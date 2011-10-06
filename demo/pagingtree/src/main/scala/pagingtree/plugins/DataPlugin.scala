package pagingtree.plugins;

import org.slf4j._

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

import pagingtree._
import pagingtree.dao.impl._

object DataPlugin extends Plugin {
    private lazy val logger = LoggerFactory.getLogger(getClass)
    override def order = 1

    override def start(): Unit = {
        //初始化数据
        transaction {
            try {
                //判断表是否存在
                Db.regions.lookup(-1L)
            } catch {
                case e: Exception =>
                    logger.info("判断表是否存在:" + e.getMessage)
                    //DDL create
                    Db.create
            }

            Db.regions.lookup(-1L) match {
                case None =>
                    logger.info("开始初始化数据...")
                    RegionDataImporter.importData()
                case _ =>
            }
        }
    }

    object RegionDataImporter {
        import scala.io._
        import java.io._
        import org.squeryl.PrimitiveTypeMode._
        def importData() = {
            val regionData = fromLines(Source.fromInputStream(RegionDataImporter.getClass.getResourceAsStream("/data/region.txt"), "UTF-8").getLines.toList)
            iterate(regionData) { r =>
                val region = new RegionImpl(r.id.toLong, r.id.toString, r.name, "", (if (r.parent.isDefined) r.parent.get.id.toLong else 0L))
                Db.regions.insert(region)
            }
        }

        class RegionData(val id: String, val name: String, val level: Int = 0,
            private var _children: List[RegionData] = Nil,
            private var _parent: Option[RegionData] = None) {
            def parent_=(p: Option[RegionData]) = {
                _parent = p
                this
            }
            def parent = _parent
            def addChild(r: RegionData): RegionData = {
                _children = _children ::: List(r)
                r.parent = Some(this)
                this
            }
            def children_=(c: List[RegionData]) = {
                _children = c
                this
            }
            def children = _children match {
                case null => Nil
                case c => c
            }
            override def toString = id + ", " + name + ", " + level + ", " + (_parent match {
                case None => "null"
                case Some(r) => r.name
            })
        }

        private def fromLine(line: String): RegionData = {
            val (id, name) = {
                val arr = line.split("\\s")
                (arr(0), arr(arr.length - 1))
            }
            val level = line.count { s => s == ' ' } //space count as level
            new RegionData(id, name, level)
        }

        private def fromLines(lines: List[String], root: RegionData = new RegionData("-1", "root", 0)): RegionData = {
            def findParent(last: RegionData, current: RegionData): RegionData = {
                assert(last != null)
                if (current.level > last.level)
                    last
                else findParent(last.parent.get, current)
            }

            def tree(lines: List[String], last: RegionData) {
                lines match {
                    case first :: tail =>
                        val current = fromLine(first)
                        //println( curr + " <=> " + last + " parent:" + (if (last.parent != null) last.parent.name else "") )
                        findParent(last, current) addChild current
                        tree(tail, current)
                    case Nil =>
                }
            }
            tree(lines, root)
            root
        }

        private def iterate(region: RegionData)(func: RegionData => Unit) = {
            def tree(region: RegionData) {
                func(region)
                for (r <- region.children) tree(r)
            }
            tree(region)
        }
    }
}


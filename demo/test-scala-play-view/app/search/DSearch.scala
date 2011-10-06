package search

import java.net.URL
import scala.collection.mutable.Map
import scala.xml._
import search.Types._

object Types {
  type KV = Map[String, String]
  val KV = Map
}

/**
 * 查询结果项.
 */
case class Hit(summary: KV = KV.empty, content: KV = KV.empty)

/**
 * 查询结果.
 */
case class Result(success: Boolean, //查询是否成功
  total: Int, //匹配结果总数
  hits: List[Hit] = Nil) { //返回的结果列表(一页)

  //当前页结果数
  def numhits = hits.size

  def costTime = _costTime

  def costTime(t: Int) = { _costTime = t; this }

  private var _costTime = 0
}

/**
 * 结果解析器.
 */
trait DResultParser {
  def parse(searchResultXml: Elem): Result = {
    val xml = searchResultXml
    val success = (xml \\ "response").head.text match {
      case "SUCCESS" => true
      case _ => false
    }

    var total = 0
    var hits: List[Hit] = Nil
    for (it <- (xml \\ "responsedata").head.child) it match {
      case <autn:totalhits>{ numhits }</autn:totalhits> =>
        total = numhits.text.toInt

      case <autn:hit>{ hitChildren @ _* }</autn:hit> => {
        val hit = Hit()
        hitChildren.foreach {
          case <autn:content>{ contentChildren @ _* }</autn:content> =>
            for (
              <DOCUMENT>{ docmentChildren @ _* }</DOCUMENT> <- contentChildren;
              item <- docmentChildren
            ) {
              //Document item
              hit.content(item.label.toLowerCase) = item.text
            }
          case hitSumaryItem if hitSumaryItem.label != "#PCDATA" => {
            hit.summary(hitSumaryItem.label) = hitSumaryItem.text
          }

          case _ =>
        }

        hits = hit :: hits
      }
      case _ =>
    }

    Result(success, total, hits)
  }
}

/**
 * 搜索接口.
 */
trait Search {
  def search(key: String = "", params: Map[String, String]): Result
}

/**
 * auto 搜索实现.
 */
object DSearch extends Search with DResultParser {
  val DefaultParams = Map("TotalResults" -> "true", "print" -> "all", "maxresults" -> 20.toString)
  def search(key: String, params: (String, String)*): Result = {
    search(key, Map[String, String](params: _*))
  }

  def search(key: String, params: Map[String, String] = Map.empty): Result = {
    val start = System.currentTimeMillis
    parse(doSearch(key, params)).costTime((System.currentTimeMillis - start).intValue)
  }

  private def doSearch(key: String, params: Map[String, String]): Elem = {
    // _cacheXML.getOrElse {
    def url = {
      val prefix = "http://203.175.156.102:9000/action=Query&"
      val p: Map[String, String] = DefaultParams + ("Text" -> key) ++ params
      prefix + p.map(it => it._1 + "=" + it._2).mkString("&")
    }
    println(url)
    val test = true
    if (test) {
      params.get("page") match {
        case Some(page) =>
          import java.io._
          XML.load(new FileInputStream(new File(play.Play.applicationPath, "test/result_%s.xml".format(page))))
        case None => throw new RuntimeException("error")
      }
    } else {
      XML.load(new URL(url))
    }
    // }
  }

  private var _cacheXML: Option[Elem] = None
}


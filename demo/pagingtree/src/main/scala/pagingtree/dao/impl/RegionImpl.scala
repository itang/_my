package pagingtree.dao.impl

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

import pagingtree._

class RegionImpl(
    var id: Long,
    var code: String,
    var name: String,
    var description: String = "",
    var parentId: Long = 0L) extends KeyedEntity[Long] with Region {

    override def toString() = {
        new StringBuilder("RegionImpl(").append("id:").append(id).append(",").append("name:").append(name)
            .append(")").toString
    }
}


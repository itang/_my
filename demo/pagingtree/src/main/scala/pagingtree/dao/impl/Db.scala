package pagingtree.dao.impl

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

import pagingtree._

object Db extends Schema {
    val regions = table[RegionImpl]("Region")

    on(regions) { r =>
        declare(
            r.id is (unique),
            r.name is (indexed))
    }
}
package pagingtree.dao.impl

import org.squeryl._
import org.squeryl.PrimitiveTypeMode._

import pagingtree.Region
import pagingtree.Page

import pagingtree.dao.RegionDao

class RegionDaoImpl extends RegionDao {
  object Implicits {
    implicit def region2RegionImpl(region: Region) = new RegionImpl(
      region.id, region.code, region.name, region.description, region.parentId)
  }

  import Implicits._
  def get(id: Long): Region = {
    Db.regions.lookup(id).getOrElse(null)
  }

  def create(region: Region) = {
    Db.regions.insert(region)
  }

  def update(region: Region) = {
    Db.regions.update(region)
  }

  def query(parentId: Int, start: Int, limit: Int): Page[Region] = {
    val total = from(Db.regions)(r => where(r.parentId === parentId)
      compute (count(r.id)))
    val data = from(Db.regions)(r => where(r.parentId === parentId)
      select (r)
      orderBy (r.id asc)).page(start, limit).toList
    Page(total, data)
  }

  def all(): List[Region] = {
    from(Db.regions)(r => select(r) orderBy (r.id asc)).toList
  }

}

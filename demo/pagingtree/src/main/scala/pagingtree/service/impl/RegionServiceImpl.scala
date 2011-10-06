package pagingtree.service.impl

import com.google.inject.Inject

import pagingtree.Region
import pagingtree.Page
import pagingtree.dao.RegionDao
import pagingtree.service.RegionService

class RegionServiceImpl @Inject() (val regionDao: RegionDao) extends RegionService {

  def addRegion(region: Region): Unit = {
    regionDao.create(region)
  }

  def getRegion(id: Int): Region = {
    regionDao.get(id)
  }

  def getAllRegion(): List[Region] = {
    regionDao.all()
  }

  def getChildRegions(parentId: Int, start: Int, limit: Int): Page[Region] = {
    regionDao.query(parentId, start, limit)
  }
}

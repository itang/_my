package pagingtree.service

import pagingtree._

trait RegionService {
    def addRegion(region: Region): Unit

    def getRegion(id: Int): Region

    def getAllRegion(): List[Region]

    def getChildRegions(parentId: Int, start: Int, limit: Int): Page[Region]
}
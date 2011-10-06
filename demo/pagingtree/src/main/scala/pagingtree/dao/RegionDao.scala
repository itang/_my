package pagingtree.dao

import pagingtree._

trait RegionDao {
    def get(id: Long): Region

    def create(region: Region): Unit

    def update(region: Region): Unit

    def query(parentId: Int, start: Int, limit: Int): Page[Region]
    
    def all(): List[Region]
}
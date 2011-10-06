package pagingtree.dao.impl

import com.google.inject.AbstractModule
import com.google.inject.Binder
import com.google.inject.Singleton

import pagingtree.dao.RegionDao

class DaoModule extends AbstractModule {
  def configure() {
    bind(classOf[RegionDao]).to(classOf[RegionDaoImpl]).in(classOf[Singleton])
  }
}

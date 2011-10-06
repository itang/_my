package pagingtree.service.impl

import com.google.inject.AbstractModule
import com.google.inject.Singleton

import pagingtree.service.RegionService

class ServiceModule extends AbstractModule {
  def configure() {
    bind(classOf[RegionService]).to(classOf[RegionServiceImpl]).in(classOf[Singleton])
  }
}

package pagingtree

import java.net.URL

import org.scalatra._
import scalate.ScalateSupport

import com.google.inject.Inject
import com.google.inject.Guice
import com.google.inject.Module

import resources._
import plugins._
import service._

import dao.impl.DaoModule
import service.impl.ServiceModule

class Application extends Base
  with RegionResource {

  @Inject
  var regionService: RegionService = _

  get("/") {
    render("index")
  }

  override def plugins = {
    //Squeryl 
    (DataPlugin :: SquerylPlugin :: Nil).sortWith { _.order < _.order }
  }

  private val injector = Guice.createInjector(new DaoModule, new ServiceModule)
  def inject(obj: AnyRef) = injector.injectMembers(obj)

  inject(this)

}

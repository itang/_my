package pagingtree

import org.scalatra._
import org.scalatra.test.scalatest._
import org.scalatest.matchers._

class ApplicationSuite extends ScalatraFunSuite with ShouldMatchers {
  addFilter(classOf[Application], "/*")

  test("GET / returns status 200") {
    get("/") {
      status should equal(200)
    }
  }
}

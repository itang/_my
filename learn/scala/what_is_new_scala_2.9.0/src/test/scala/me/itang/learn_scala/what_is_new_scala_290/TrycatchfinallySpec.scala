package me.itang.learn_scala.what_is_new_scala_290

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class TrycatchfinallySpec extends Spec with ShouldMatchers {
  describe("Generalized try-catch-finally") {
    val ex = try {
      io.Source.fromFile("badfile")
    } catch {
      case e: java.io.IOException =>
        "IOException"
      case _ =>
        "Exception"
    } finally {
      "Some"
    }
    ex should be("IOException")
  }

}
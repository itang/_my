package me.itang.learn_scala.what_is_new_scala_290

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class AppSpec extends Spec with ShouldMatchers {
  describe("App trait") {
    object Echo extends App {
      println("Echo" + (args mkString " "))
    }

    Echo.main(Array("hello"))
  }
}
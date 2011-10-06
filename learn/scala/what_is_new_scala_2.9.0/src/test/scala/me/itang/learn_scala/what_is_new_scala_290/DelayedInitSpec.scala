package me.itang.learn_scala.what_is_new_scala_290

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class DelayedInitSpec extends Spec with ShouldMatchers {
  describe("DelayedInit") {
    it("all its initialization code is packed in a closure and forwarded as an argument to a method named delayedInit") {
      trait MyDelayedInit extends DelayedInit {
        var initCode = List[() => Unit]()
        override def delayedInit(body: => Unit) {
          initCode = (() => body) :: initCode
        }

        def test() {
          initCode.reverse.foreach(_())
        }
      }

      object App extends MyDelayedInit {
        println("Hello, World")
        List(1, 2, 3).foreach(it => println(it))
      }
      App.test()
    }
  }
}
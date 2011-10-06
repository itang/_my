package me.itang.learn_scala.implicits

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

class ImplicitConversionSpec extends Spec with ShouldMatchers {
  describe("implicit conversions") {
    describe("Implicit conversion to an expected type") {
      implicit def double2int(d: Double) = d.toInt
      val i: Int = 3.5
      i should be(3)
    }

    describe("Converting the receiver") {
      implicit def intW(target: Int) = new {
        def times(proc: => Any) {
          var i = target
          while (i > 0) {
            proc
            i -= 1
          }
        }
      }

      var count = 0
      10.times(count += 1)
      count should be(10)
    }

    describe("Implicit parameters") {
      class PreferredPrompt(val preference: String)
      implicit val prompt = new PreferredPrompt("Yes, master> ")
      object Greeter {
        //在此声明implicit val prompt = new PreferredPrompt("Yes, master> ") greet编译通不过

        def greet(name: String)(implicit prompt: PreferredPrompt) = {
          println("Welcome, " + name + ". The system is ready.")
          println(prompt.preference)
          prompt.preference
        }
      }
      Greeter.greet("itang") should be("Yes, master> ")

      it("Note that the implicit keyword applies to an entire parameter list, not to individual parameters") {
        //
      }
    }
  }
}

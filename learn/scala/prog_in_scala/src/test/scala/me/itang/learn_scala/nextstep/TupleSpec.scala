package me.itang.learn_scala.nextstep

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

/**
 * Tuple.
 */
class TupleSpec extends Spec with ShouldMatchers {
  describe("Tuple") {
    describe("构造") {
      it("通过Tuple对应的类 new") {
        val tuple = new Tuple2("1", "2")
        tuple.getClass should be(classOf[Tuple2[_, _]])
        val tuple3 = new Tuple3[String, String, Int]("a", "b", 1)
        tuple3._1 should be("a")
        tuple3._3 should be(1)
      }

      it("通过()literal") {
        val tuple2 = ("a", "b")
        tuple2._1 should equal("a")
        tuple2._2 should equal("b")
        tuple2 should equal(new Tuple2("a", "b"))
      }
    }

    describe("变量匹配") {
      it("左赋值") {
        val (a, b) = ("a", "b")
        a + b should be("ab")
      }

      it("模式匹配") {
        val tuple2 = ("a", "b")
        tuple2 match { case (a, b) => a + b should be("ab") }
        tuple2 match { case (a, _) => a should be("a") }
        tuple2 match { case (_, b) => b should be("b") }

        ("a", "b", "c") match { case (_, b, _) => b should be("b") }
      }
    }

    describe("特定类型Pair") {
      val pair = Pair(1, "a") // type Pair[+A, +B] = Tuple2[A, B]
      pair.isInstanceOf[(_, _)] should be(true)
      pair.isInstanceOf[Tuple2[_, _]] should be(true)

      var map = Map[Int, String]()
      map += pair
      map(1) should be("a")
    }
  }
}

package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

object P {
  implicit def iwhat[T <: Any](t: T) = new {
    def what(proc: T => Any): T = {
      proc(t)
      t
    }
  }
}
import P._

class SeqSpec extends Spec with ShouldMatchers {
  val xs = Seq("a", "b", "c")

  describe("Seq") {
    describe("构造") {
      val seq = Seq("a", "b", "c")
      seq should have size (3)
    }

    describe("Seq[T] extends PartialFunction[Int, T])") {
      val xs = Seq("a", "b", "c")
      xs.isInstanceOf[PartialFunction[_, _]] should be(true)

      xs.isDefinedAt(1) should be(true)
      xs.isDefinedAt(xs.size) should be(false)

      List(0, 1, 2).collect {
        case i => xs(i)
      } should be(List("a", "b", "c"))
      List(0, 1, 2).collect(xs) should be(List("a", "b", "c"))
    }

    describe("Indexing and length operations") {
      it("apply") {
        val xs = Seq(1, 2, 3)
        xs.apply(0) should be(1)
        xs.apply(xs.size - 1) should be(3)

        xs(0) should be(xs apply 0)
      }

      it("length") {
        xs.length should equal(xs.size)
      }

      it("lengthCompare ") {
        xs.lengthCompare(2) should be(1)
      }

      it("indices") {
        xs.indices should be(0 to xs.size - 1)
      }

      val seq = Seq(1, 2, 3, 3, 4)
      seq.lastIndexOf(3) should be(3)
      seq.indexOfSlice(Seq(1, 2)) should be(0)
      seq.indexOfSlice(Seq(3, 4)) should be(3)
      seq.lastIndexOfSlice(Seq(3)) should be(3)

      seq indexWhere { _ > 3 } should be(seq.length - 1)

      seq segmentLength (i => i > 2, 3) should be(2)

      seq prefixLength (i => i < 3) should be(2)
    }

    describe("Additions") {
      it("+:") {
        3 +: Seq(1, 2) should be(Seq(3, 1, 2))
      }
      it(":+") {
        Seq(1, 2) :+ 3 should be(Seq(1, 2, 3))
      }

      it("padTo") {
        Seq(1, 2) padTo (5, 3) should be(Seq(1, 2, 3, 3, 3))
      }
    }

    describe("Updates") {
      Seq(1, 2, 3, 3, 4, 3).patch(3, Seq(7, 7), 3) should be(Seq(1, 2, 3, 7, 7))
      Seq(1, 2, 3, 3, 4, 3).patch(3, Seq(7, 7), 1) should be(Seq(1, 2, 3, 7, 7, 4, 3))

      xs updated (1, "d") should be(Seq("a", "d", "c"))

      val mxs = collection.mutable.Seq("a", "b", "c")
      mxs(1) = "d"
      mxs should equal(xs updated (1, "d"))
    }

    describe("Sorting") {
      it("sorted") {
        val seq = Seq("a", "c", "b")
        seq.sorted should be(xs)
      }

      it("sortWith ") {
        val seq = Seq("look", "hello", "you")
        seq sortWith { _ < _ } should be(Seq("hello", "look", "you"))
        seq sortWith { _.length < _.length } should be(Seq("you", "look", "hello"))
      }

      it("sortBy") {
        val seq = Seq("look", "hello", "you")
        seq sortBy { -_.length } should be((seq sortWith { _.length < _.length }).reverse)
      }
    }

    describe("Reversals") {
      it("reverse") {
        xs.reverse should be(Seq("c", "b", "a"))
      }
      it("reverseIterator") {
        val it = xs.reverseIterator
        it.next should be("c")
      }
      it("reverseMap ") {
        xs reverseMap { _ + "_" } should be(xs.reverse.map(_ + "_"))
      }
    }

    describe("Compari{sons") {
      it("startsWith") {
        xs startsWith Seq("a", "b") should be(true)
      }
      it("endsWith ") {
        xs endsWith Seq("b", "c") should be(true)
      }
      it("contains") {
        xs contains "a" should be(true)
      }

      it("containsSlice ") {
        xs containsSlice Seq("a", "b") should be(true)
      }

      it("corresponds ") {
        (xs corresponds Seq("a", "b", "c")) { _ == _ } should be(true)
      }
    }

    describe("Multiset Operations") {
      it("intersect 交集") {
        (xs intersect Seq("a", "d", "c")).what(println) should be(Seq("a", "c"))
      }
      it("diff 差") {
        (xs diff Seq("a", "d", "c")).what(println) should be(Seq("b"))
      }
      it("union ") {
        (xs union Seq("a", "d", "c")).what(println) should be(Seq("a", "b", "c", "a", "d", "c"))
      }

      it("distinct") {
        Seq(1, 1, 2, 3, 3, 4).distinct should be(Seq(1, 2, 3, 4))
      }
    }

    describe("子类") {
      import collection.LinearSeq
      classOf[Seq[_]] isAssignableFrom classOf[LinearSeq[_]] should be(true)
      classOf[Seq[_]] isAssignableFrom classOf[IndexedSeq[_]] should be(true)
    }

  }

}



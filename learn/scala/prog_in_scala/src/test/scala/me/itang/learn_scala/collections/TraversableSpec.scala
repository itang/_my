package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class TraversableSpec extends Spec with ShouldMatchers {
  describe("Traversable") {
    describe("构造实例") {
      it("使用半生对象") {
        val t = Traversable("1", "2", "3")
        t.isInstanceOf[Traversable[_]] should be(true)
        var i = 0
        t.foreach { i += _.toInt }
        i should be(1 + 2 + 3)
      }
    }

    describe("abstract method foreach") {
      var i = 0
      Traversable(1, 2, 3).foreach { it =>
        i += 1
        it should be(i)
      }
    }

    describe("many concrete methods") {
      describe("++") {
        it("Addition, ++, which appends two traversables together, or appends all elements of an iterator to a traversable") {
          Traversable(1, 2) ++ Traversable(3, 4) should equal(Traversable(1, 2, 3, 4))
        }
      }
      describe("map") {
        it("Map operations map, flatMap, and collect, which produce a new collection by applying some function to collection elements") {
          Traversable(1, 2).map(_ + 1) should equal(Traversable(2, 3))
          Traversable(1, 2).collect {
            case e: Int => e + 1
          } should equal(Traversable(2, 3))

          Traversable(Traversable(1, 2), Traversable(3, 4)).flatMap(it => it).map(_ + 1) should be(Traversable(2, 3, 4, 5))
        }
      }

      describe("Conversions ") {
        it("toArray, toList, toIterable, toSeq, toIndexedSeq, toStream, toSet, toMap, which turn a Traversable collection into something more specific") {
          Traversable(1, 2).toArray should be(Array(1, 2))
          Traversable(1, 2).toList should be(List(1, 2))
          Traversable(1, 2).toIterable should be(Iterable(1, 2))
          Traversable(1, 2).toSeq should be(Seq(1, 2))
          Traversable(1, 2).toIndexedSeq should be(IndexedSeq(1, 2))

          Traversable(1, 2).toStream should be(Stream(1, 2))
          Traversable(1, 2).toSet should be(Set(1, 2))
        }

        it("toMap") {
          Traversable((1, 2)).toMap should be(Map(1 -> 2))
          Traversable(1 -> 2).toMap should be(Map(1 -> 2))
        }
      }

      describe("others") {
        val t = Traversable(1, 2, 3, 4, 5)
        t should have size (5)
        t.size should be(5)

        t.hasDefiniteSize should be(true)

        t.find(_ > 3) should be(Some(4))
        t.find(_ > 5) should be(None)

        t.head should be(1)
        t.headOption should be(Some(1))
        t.tail should be(Traversable(2, 3, 4, 5))

        t.take(2) should be(Traversable(1, 2))
        t.drop(2) should be(Traversable(3, 4, 5))
        t.splitAt(2) should be(t.take(2), t.drop(2))
        t.splitAt(2)._1 should be(Traversable(1, 2))

        var p = (i: Int) => i > 3
        t filter p should be(Traversable(4, 5))
        t filterNot p should be(Traversable(1, 2, 3))
        t partition p should be((t filter p, t filterNot p))

        p = (i: Int) => i % 2 == 1
        t.takeWhile(p) should be(Traversable(1))
        t.span(p) should be(t.takeWhile(p), t.dropWhile(p))

        t.forall(_ > 0) should be(true)
        t.exists(_ == 5) should be(true)

        (0 /: t)(_ + _) should be((1 + 5) * 5 / 2)
        t.reduceLeft(_ + _) should be(15)
        t.foldLeft(0)(_ + _) should be(15)

        t.sum should be(15)
        (t.min, t.max) should be(1 -> 5)

        t.mkString(",") should be("1,2,3,4,5")

        t.product should be(1 * 2 * 3 * 4 * 5)
      }
    }
  }
}


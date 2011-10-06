package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class IterableTest extends Spec with ShouldMatchers {
  describe("Iterable") {
    describe("构造") {
      val it = Iterable(1, 2, 3)
      it should have size (3)
    }

    describe("abstract method iterator") {
      val it = Iterable(1, 2, 3).iterator
      var i = 0
      while (it.hasNext) {
        i += it.next
      }
      i should be(1 + 2 + 3)
    }

    val xs = List(1, 2, 3, 4, 5)
    describe("grouped method") {

      var it = xs.grouped(3)
      it.next should be(List(1, 2, 3))
      it.next should be(List(4, 5))
      evaluating { it.next } should produce[java.util.NoSuchElementException]

      it = xs sliding 3
      it.next should be(List(1, 2, 3))
      it.next should be(List(2, 3, 4))
      it.next should be(List(3, 4, 5))
    }

    describe("take") {
      val xs = List(1, 2, 3, 4, 5)
      xs.takeRight(3) should be(List(3, 4, 5))
      xs.dropRight(3) should be(List(1, 2))
    }

    describe("zippers") {
      List(1, 2, 3) zip List(3, 4, 5) should be(List(1 -> 3, 2 -> 4, 3 -> 5))

      List(1, 2, 3) zipAll (List("a", "b"), 100, "h") should be(List(1 -> "a", 2 -> "b", 3 -> "h"))

      List("a", "b").zipWithIndex should be(List("a" -> 0, "b" -> 1))
      List("a", "b").zipWithIndex.map(it => it._2 -> it._1) should be(List(0 -> "a", 1 -> "b"))
    }
  }

}


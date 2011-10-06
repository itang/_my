package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class SetSpec extends Spec with ShouldMatchers {
  describe("Set") {
    describe("构造") {
      val xs = Set(1, 2, 3)
      xs.size should be(3)
      val xs1 = Set(1, 2, 3, 3, 4)
      xs1 should have size (4)
    }
    val xs = Set("a", "b", "c")
    describe("Equals") {
      xs should equal(Set("a", "b", "c"))
      xs should equal(Set("a", "c", "b"))
      xs should not equal (Set("a", "c"))
    }
    describe("Tests") {
      it("contains") {
        xs.contains("a") should be(true)
        xs contains "d" should be(false)
      }
      it("apply") {
        xs.apply("a") should be(xs.contains("a"))
        xs("d") should be(xs.contains("d"))
      }
      it("subsetOf") {
        xs.subsetOf(Set("a", "b")) should be(false)
        xs.subsetOf(Set("a", "b", "c")) should be(true)
        xs.subsetOf(xs + "d") should be(true)
      }
    }

    describe("Additions") {
      it("+") {
        xs + "d" should be(Set("a", "b", "c", "d"))
        xs should be(Set("a", "b", "c")) // 默认imutable
        //d +: xs // compile error, no such method +:
        xs + ("d", "e", "f") should be(xs + "d" + "e" + "f")
        ("d", "e", "f").isInstanceOf[Tuple3[_, _, _]] should be(true)
      }
      it("++") {
        xs ++ Set("d", "e") should be(xs + "d" + "e")
      }
    }

    describe("Removals") {
      it("-") {
        xs - "a" should be(Set("b", "c"))
        xs - "b" should be(Set("a", "c"))
      }
    }

    describe("Binary operations") {
      val xy = Set("c", "d", "e")
      it("&") {
        xs & xy should be(Set("c"))
        xs & Set("d", "e") should be(Set.empty)
        xs.intersect(xy) should be(xs & xy)
      }

      it("|") {
        xs | xy should be(Set("a", "b", "c", "d", "e"))
        xs union xy should be(xs | xy)
        xs | Set("d") should be(xs + "d")
      }

      it("&~") {
        xs &~ xy should be(Set("a", "b"))
        xs diff xy should be(xs &~ xy)
        xs &~ xs should be(Set.empty)
      }
    }
  }

  describe("Mutable.Set") {
    import scala.collection.mutable.Set

    describe("Additions") {
      val xs = Set("a", "b", "c")
      val xy = Set("c", "d")
      it("+=") {
        xs += "d"
        xs should be(xs + "d")
        xs("d") should be(true)

        xs += ("c", "d")
        xs should be(xs + ("c", "d"))
      }
      it("++=") {
        xs ++= xy
        xs should be(Set("a", "b", "c", "d"))
      }
      it("add") {
        val xs = Set("a", "b", "c")
        xs add "a" should be(false)
        xs should be(Set("a", "b", "c"))
        xs add "d" should be(true)
        xs should be(Set("a", "b", "c", "d"))
      }
    }
    describe("Removals") {
      val xs = Set("a", "b", "c")
      val xy = Set("c", "d")
      xs -= "a"
      xs should be(Set("b", "c"))
      xs --= xy
      xs should be(Set("b"))
      xs += "d"
      xs retain { _ > "c" }
      xs should be(Set("d"))
      xs.clear()
      xs should be(Set())
    }

    describe("Updates") {
      val xs = Set("a", "b", "c")
      xs update ("d", true)
      xs should be(Set("a", "b", "c", "d"))

      xs("e") = true
      xs should be(Set("a", "b", "c", "d", "e"))
      xs("e") = false
      xs("d") = false
      xs should be(Set("a", "b", "c"))
    }

    describe("Clone") {
      val xs = Set("a", "b", "c")
      val xy = xs.clone
      xy should be(Set("a", "b", "c"))
      xy should not eq (xy)
    }
  }

  describe("Sorted Set") {
    it("create TreeSet") {
      import scala.collection.immutable.TreeSet
      val myOrdering = Ordering.fromLessThan[String](_ > _)
      val tree = TreeSet.empty(myOrdering)
      (tree + ("aa", "b", "ab", "c")).toList should be(List("c", "b", "ab", "aa"))

      val res0 = TreeSet.empty[String]

      val res1 = res0 + ("one", "two", "three", "four")

      res1.toList should be(List("four", "one", "three", "two"))
      (res1 range ("one", "two") toList) should be("one" :: "three" :: Nil)
      (res1 from ("three") toList) should be("three":: "two" :: Nil)
      
      TreeSet("a","b") should equal(TreeSet("b", "a"))
    }
  }
}



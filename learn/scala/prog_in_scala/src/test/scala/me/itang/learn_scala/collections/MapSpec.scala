package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class MapSpec extends Spec with ShouldMatchers {
  describe("Map") {
    describe("Construct") {
      val m = Map(1 -> "a", 2 -> "b", 3 -> "c")
      val m1 = Map((1, "a"), (2, "b"), (3, "c"))
      m should equal(m1)
      val m2 = Map.empty
      m2.size should be(0)

    }
    describe("Lookups") {
      val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
      it("get") {
        (map get 1) should be(Some("a"))
        (map get 4) should be(None)
      }

      it("apply") {
        map(1) should be("a")
        evaluating {
          map(4)
        } should produce[Exception]
      }

      it("getOrElse") {
        map.getOrElse(1, "some") should be("a")
        map.getOrElse(4, "some") should be("some")
      }

      it("contains") {
        map.contains(1) should be(true)
        map.contains(4) should be(false)
      }

      it("isDefinedAt") {
        map.isDefinedAt(1) should be(true)
        map.isDefinedAt(4) should be(false)

        List(1, 2, 3).map(map) should be(List("a", "b", "c"))
        List(2, 3).map(map) should be(List("b", "c"))
      }

    }

    describe("Additions and Updates") {
      val map = Map(1 -> "a", 2 -> "b")
      it("+") {
        (map + (3 -> "c"))(3) should be("c")
        (map + ((3, "c")))(3) should be("c")

        (map + (3 -> "c", 4 -> "d"))(4) should be("d")
      }
      it("++") {
        (map ++ Map(3 -> "c", 4 -> "d"))(4) should be("d")
      }
      it("udpated") {
        map.updated(3, "c")(3) should be("c")
      }
    }

    describe("Removals") {
      val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
      it("-") {
        map - 1 should be(Map(2 -> "b", 3 -> "c"))

        map - (1, 2) should be(Map(3 -> "c"))
      }
      it("--") {
        map -- List(1, 2) should be(Map(3 -> "c"))
        map -- Array(1, 2) should equal(map -- List(1, 2))
      }
    }

    describe("Subcollections") {
      val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
      it("keys") {
        map.keys should equal(Set(1, 2, 3))
      }
      it("keySet") {
        map.keySet should be(Set(1, 2, 3))
      }
      it("keyIterator") {
        val it = map.keysIterator

        val set = scala.collection.mutable.Set.empty[Int]
        while (it.hasNext) {
          set += it.next
        }
        set should be(Set(1, 2, 3))
      }
      it("values") {
        map.values.toList.contains("a") should be(true)
        map.valuesIterator.toList.contains("a") should be(true)
      }

    }

    describe("Transformation") {
      val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
      it("filterKeys ") {
        map.filterKeys(_ > 2) should be(Map(3 -> "c"))
      }
      it("mapValues") {
        map.mapValues(_ + ",")(1) should be("a,")
      }
    }
  }

  describe("mutable.Map") {
    import scala.collection.mutable.Map
    describe("Additions and Updates") {
      it("update") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        map.update(1, "aa")
        map(1) should be("aa")
        map.update(4, "some")
        map(4) should be("some")

        map(1) = "aaa"
        map(1) should be("aaa")
      }
      it("+=") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        map += (4 -> "d")
        map(4) should be("d")

        map += (4 -> "d", 5 -> "e")
        map(5) should be("e")
      }

      it("++=") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        map ++= Map(4 -> "d", 5 -> "e")
        map(5) should be("e")
        map.size should be(5)
      }

      it("put") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        (map put (1, "a")) should be(Some("a"))
        (map put (4, "d")) should be(None)
      }

      it("getOrElseUpdate ") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        map.getOrElseUpdate(1, "some") should be("a")
        map.getOrElseUpdate(4, "d") should be("d")
        map(4) should be("d")
      }
    }
    describe("Removals") {
      it("-=, --=") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        (map -= 1)
        map.contains(1) should be(false)
        (map -= (2, 3)) should be(Map.empty)
        map += (1 -> "a", 2 -> "b")
        map --= Traversable(1)
        map should be(Map(2 -> "b"))
      }

      it("remove retain clear") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        (map remove 1) should be(Some("a"))
        map.size should be(2)

        map.retain((key, value) => key == 2)
        map get 3 should be(None)
        map.get(2) should be(Some("b"))

        map.clear()
        map.size should be(0)
      }
    }

    describe("Transformation") {
      val ms = Map(1 -> "a", 2 -> "b", 3 -> "c")
      val f = (k: Int, v: String) => v + ","
      ms transform f
      ms(1) should be("a,")
    }

    describe("Cloning") {
      val ms = Map(1 -> "a", 2 -> "b", 3 -> "c")
      val map = ms.clone
      map should equal(ms)
      map.eq(ms) should be(false)
    }

  }

  describe("SynchronizedMap") {
    import scala.collection.mutable.{
      Map,
      SynchronizedMap,
      HashMap
    }
    object MapMaker {
      def makeMap: Map[String, String] = {
        new HashMap[String, String] with SynchronizedMap[String, String] {
          override def default(key: String) =
            "Why do you want to know?" 
        }
      }
    }

    val ms = MapMaker.makeMap
    ms += ("name" -> "itang")
    ms("name") should be("itang")
    ms("other").contains("Why") should be(true)
  }

}
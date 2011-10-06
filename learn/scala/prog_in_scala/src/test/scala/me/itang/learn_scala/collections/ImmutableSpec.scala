package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class ImmutableSpec extends Spec with ShouldMatchers {
  describe("Immutable Collection") {
    describe("List") {
      it("构造") {
        val xs = List(1, 2, 3)
        val xs1 = 1 :: 2 :: 3 :: Nil
        xs should equal(xs1)

        val xs2 = new ::("a", Nil)
        xs2.isInstanceOf[List[_]] should be(true)

        xs2 should equal(List("a"))
      }
    }
    describe("Streams") {
      val str = 1 #:: 2 #:: 3 #:: Stream.empty
      str.size should be(3)

      def fibFrom(a: Int, b: Int): Stream[Int] = a #:: fibFrom(b, a + b)

      val r = fibFrom(1, 1).take(7)
      r.toList should be(List(1, 1, 2, 3, 5, 8, 13))

    }
    describe("Vectors") {
      val vec = scala.collection.immutable.Vector.empty
      val vec2 = vec :+ 1 :+ 2
      val vec3 = 100 +: vec2
      vec3 should be(Vector(100, 1, 2))

      val xs = collection.immutable.IndexedSeq(1, 2, 3)
      xs.isInstanceOf[Vector[_]] should be(true)
    }
    describe("Immutable stacks") {
      val stack = scala.collection.immutable.Stack.empty
      val stack1 = stack push (1)
      stack1.size should be(1)
      stack1.top should be(1)
      val stack2 = stack1 push (2)
      stack2.top should be(2)
      val stack3 = stack2.pop
      stack3.size should be(1)
      stack2.size should be(2)
    }
    describe("Immutable Queues") {
      import scala.collection.immutable.Queue
      describe("first-in-first-out") {
        val empty = Queue[Int]()
        it("enqueue") {
          val has1 = empty.enqueue(1)
          val has123 = has1.enqueue(List(2, 3))
          has123 should be(Queue(1, 2, 3))
        }

        it("dequeue") {
          val has123 = Queue(1, 2, 3)
          val (element, has23) = has123.dequeue
          element should be(1)
        }
      }
    }
    describe("Ranges") {
      describe("A Range is an ordered sequence of integers that are equally spaced apart") {
        describe("构造") {
          it("通过构造函数") {
            val r = Range(1, 3)
            r.head should be(1)
            r.last should be(2)

            r should be(1 to 2 by 1)
          }
          it("通过to by") {
            val r = 1 to 10
            r.length should be(10)
            r(0) should be(1)
            r.last should be(10)
            r(1) should be(2)

            val r1 = 1 to 10 by 2
            r1.head should be(1)
            r1.last should be(9)
            r1(1) should be(3)
          }
          it("通过until") {
            val r = 1 until 10 by 1
            r.head should be(1)
            r.last should be(9)
          }
        }

        describe("迭代") {
          it("for") {
            var sum = 0
            for (i <- 1 to 10) sum += i
            sum should be((1 + 10) * 10 / 2)
          }
        }
      }
    }
    describe("Hash Tries") {
      it("Map 的默认实现 HashMap(size > 4的情况下)") {
        val map = Map(1 -> "a")
        println(map.getClass)
        map.isInstanceOf[scala.collection.immutable.Map.Map1[_, _]] should be(true)
        val map4 = (1 to 4).map(it => (it, it)).toMap
        map4.isInstanceOf[scala.collection.immutable.Map.Map4[_, _]] should be(true)

        val map5 = (1 to 5).map(it => (it, it + 1)).toMap
        map5.isInstanceOf[scala.collection.immutable.HashMap[_, _]] should be(true)
      }

      it("Set 的默认实现 HashSet(size > 4的情况下)") {
        val set = Set(1, 2, 3, 3)
        set.size should be(3)
        set.isInstanceOf[scala.collection.immutable.Set.Set3[_]] should be(true)
        (1 to 5).toSet.isInstanceOf[scala.collection.immutable.HashSet[_]] should be(true)
      }
    }
    describe("Red-Black Trees") {
      describe("TreeSet") {
        val set = scala.collection.immutable.TreeSet.empty[Int]
        val set1 = set + 1 + 1 + 3 + 3 + 3 + 2
        set1.size should be(3)
        set1 should be(scala.collection.immutable.TreeSet(1, 3, 2))

        it("standard implementation of SortedSet") {
          val sortedSet = scala.collection.immutable.SortedSet(2, 1, 2, 3, 3)
          sortedSet.isInstanceOf[scala.collection.immutable.TreeSet[_]] should be(true)
          sortedSet should be(scala.collection.immutable.TreeSet(2, 1, 3))
        }
      }

      describe("TreeMap") {
        val map = scala.collection.immutable.TreeMap.empty[Int, String]
        val map1 = map + (3 -> "c", 1 -> "a", 2 -> "b", 2 -> "bb") // 相同key, 后面的覆盖之
        map1.size should be(3)
        map1 should be(scala.collection.immutable.TreeMap(3 -> "c", 1 -> "a", 2 -> "bb"))

        it("standard implementation of SortedMap") {
          val sortedMap = scala.collection.immutable.SortedMap(1 -> "a")
          sortedMap.isInstanceOf[scala.collection.immutable.TreeMap[_, _]] should be(true)
        }
      }

    }
    describe("Immutable BitSets") {
      val bits = scala.collection.immutable.BitSet.empty
      val moreBits = bits + 3 + 4 + 4
      moreBits(3) should be(true)
      moreBits(0) should be(false)
    }
    describe("List Maps") {
      """The only possible difference is 
    	if the map is for some reason constructed in such a way 
    	that the first elements in the list are selected much more 
    	often than the other elements"""
      val map = scala.collection.immutable.ListMap(1 -> "one", 2 -> "two")
      map(2) should be("two")
    }
  }

}

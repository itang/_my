package me.itang.learn_scala.what_is_new_scala_290

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class ParallelSpec extends Spec with ShouldMatchers {
  describe("Parallel Collection") {
    describe("在 scala.collection.parallel 包下") {
      val map = Map("parallel arrays" -> "scala.collection.parallel.mutable.ParArray",
        "parallel ranges" -> "scala.collection.parallel.immutable.ParRange",
        "parallel hash maps" -> "scala.collection.parallel.mutable.ParHashMap",
        "parallel hash sets" -> "scala.collection.parallel.mutable.ParHashSet",
        "parallel hash tries HashMap" -> "scala.collection.parallel.immutable.ParHashMap",
        "parallel hash tries HashMap" -> "scala.collection.parallel.immutable.ParHashSet",
        "parallel vectors" -> "scala.collection.parallel.immutable.ParVector")

      map.foreach(it =>
        println(it._1 + " : " + Class.forName(it._2)))
    }

    describe("scala.collection.parallel.mutable.ParArray") {
      import scala.collection.parallel.mutable.ParArray
      val parray = ParArray(1, 2, 3)

      describe("length") {
        parray.length should be(3)
        parray(1) = 100
        parray(1) should be(100)
      }
      describe("foreach") {
        parray.foreach { it =>
          println(Thread.currentThread)
          println(it)
        }
      }
      describe("map") {
        var p = parray.map(_ + 2)
        p(1) should be(102)
      }

      describe("filter") {
        var p = parray.filter(_ % 2 == 0)
        parray.length should be(3)
        p.length should be(1)
        p(0) should be(100)
      }

    }

    describe("通过par方法将普通集合转换为并行集合") {
      describe("List#par") {
        it("对应scala.collection.parallel.immutable.ParVector") {
          val list = List(1, 2, 3)
          list.par.isInstanceOf[scala.collection.parallel.immutable.ParVector[_]]
          list.sum should be(6)
        }
      }

      describe("Array#par") {
        it("对应scala.collection.parallel.mutable.ParArray") {
          val array = Array(1, 2, 3)
          array.par.isInstanceOf[scala.collection.parallel.mutable.ParArray[_]]
          array.sum should be(6)
        }
      }

      describe("Range#par") {
        val range = Range(1, 4)
        it("对应scala.collection.parallel.immutable.ParRange") {
          range.par.isInstanceOf[scala.collection.parallel.immutable.ParRange]
        }
        range.par.size should be(3)
        range.sum should be(6)
      }

      describe("Map#par") {
        val map = Map(1 -> "a", 2 -> "b", 3 -> "c")
        it("对应scala.collection.parallel.immutable.ParHashMap") {
          map.par.isInstanceOf[scala.collection.parallel.immutable.ParHashMap[_, _]]
        }
        map.par should have size (3)
        map.par.seq.keys.sum should be(6)

        it("HashMap#par") {
          import scala.collection.immutable.HashMap
          val map1 = HashMap(1 -> "a")
          map1.par.isInstanceOf[scala.collection.parallel.immutable.ParHashMap[_, _]]
        }
      }

      describe("Set#par") {
        val set = Set(1, 2, 3)
        it("对应scala.collection.parallel.immutable.ParHashSet") {
          set.par.isInstanceOf[scala.collection.parallel.immutable.ParHashSet[_]]
        }
        set.par.sum should be(6)
      }
    }

    describe("通过seq方法将并行集合转换为普通集合") {
      import scala.collection.parallel.immutable.ParHashSet
      val pset = ParHashSet(1, 2, 3)
      val seq = pset.seq
      seq.isInstanceOf[scala.collection.immutable.HashSet[_]]
      seq should equal(Set(3, 2, 1))
    }

    describe("GenIterable") {
      import scala.collection.GenIterable
      def foreach[T](xs: GenIterable[T])(proc: T => Any) {
        xs
      }
      foreach(Array(1, 2, 3)) {
        println _
      }
      import scala.collection.parallel.mutable.ParArray
      foreach(ParArray(1, 2, 3))(println)

      import scala.collection.GenSeq
      def p[T](xs: GenSeq[T]) = xs foreach println
      p(List(1, 2, 3))
      p(List(1, 2, 3).par)
    }
  }
}
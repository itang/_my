package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class ListSpec extends Spec with ShouldMatchers {
  describe("List") {
    describe(":: 是List的子类") {
      classOf[List[_]].isAssignableFrom(classOf[::[_]]) should be(true)

      (manifest[java.lang.Integer] <:< manifest[java.lang.Number]) should be(true)
      /*(manifest[::[_]] <:< manifest[scala.collection.immutable.List[_]]) should be(true)*/ //???

      it(":: 类使用") {
        val xs = ::(1, Nil)
        xs.length should be(1)
        xs.head should be(1)
        xs.tail should be(Nil)
        val xs1 = ::(100, 1 :: 2 :: Nil)
        xs1.last should be(2)
        xs1.head should be(100)
      }
    }

    describe("Nil 是List subobject") {
      Nil.isInstanceOf[List[_]] should be(true)
      val xs = Nil
      1 :: xs should be(List(1))
    }
  }

}
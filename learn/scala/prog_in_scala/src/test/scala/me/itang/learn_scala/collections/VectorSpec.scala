package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class VectorSpec extends Spec with ShouldMatchers {
  describe("Vector") {
    describe("""ectors allow accessing any element of the list in " effectively " constant time""") {
      it("构造") {
        val vec = scala.collection.immutable.Vector.empty
        val vec2 = vec :+ 1 :+ 2
        val vec3 = 100 +: vec2
        vec3(0) should be(100)
        vec3(1) should be(1)
        vec3(2) should be(2)
      }

      it("updated") {
        val vec = Vector(1, 2, 3)
        val vec2 = vec updated (2, 4)
        vec(2) should be(3)
        vec2(2) should be(4)
      }
      
      it("IndexedSeq的默认实现"){
         val indexdSeq = collection.immutable.IndexedSeq(1, 2, 3)
         indexdSeq.isInstanceOf[Vector[_]] should be(true)
      } 
    }
  }

}
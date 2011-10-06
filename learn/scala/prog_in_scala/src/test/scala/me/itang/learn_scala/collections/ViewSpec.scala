package me.itang.learn_scala.collections

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

class ViewSpec extends Spec with ShouldMatchers {
  describe("View") {
    //A view is a special kind of collection that represents some base collection, but implements all transformers lazily.
    describe("视图是一种特定的集合， 用来表现一些基本的集合，但是所有的转换操作都是lazy的") {
      it("通过view方法获取集合的视图") {
        val v = Vector(1 to 10: _*)
        v map (_ + 1) map (_ * 2) should be(Vector(4 to 22 by 2: _*))
        (v.view map (_ + 1) map (_ * 2)).force should be(Vector(4 to 22 by 2: _*))
      }
    }
  }
}
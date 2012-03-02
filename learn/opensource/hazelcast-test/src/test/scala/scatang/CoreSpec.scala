package scatang

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

//导入scatang core implicits
//import scatang._

class CoreSpec extends Spec with ShouldMatchers {
  describe("Core 库") {
    describe("Int 增强") {
      describe("times 方法") {
        it("byname parameter方式") {
          var count = 0
          10.times {
            count += 1
          }
          count should be(10)
        }
        it("带当前次数方式") {
          var index = 1
          10.times { it =>
            it should be(index)
            index += 1
          }
        }
      }
    }

    describe("tap"){
      1.tap(println).tap(it => 100) should be (1)
      "hello".tap(_ + ",world") should be("hello")
    }

    describe("deliver"){
      "hello".deliver(_ + ",world") should be("hello,world")
      1.`with`(_ + 1).`with`(_ + 1) should be(3)
    }
  }
}

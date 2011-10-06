package me.itang.learn_scala.nextstep

/**
 * 数组.
 *
 * @author itang
 */
import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
class ArraySpec extends Spec with ShouldMatchers {
  describe("Array 数组") {
    describe("构造") {
      it("通过new, 参数指定数组维数信息") {
        val array = new Array[String](2)
        array should have length (2)

        array(0) = "a"; array(1) = "b"
        array(1) should be("b")

        val array2 = new Array(1)
        array2.isInstanceOf[Array[_]] should be(true)
      }

      it("通过Array.apply工厂方法") {
        var array = Array(1, 2)
        (0 until array.length) foreach println
        array(0) should be(1)
        array(1) should be(2)
        array should have length (2)

        /*array(0) = "a"*/ // type mismatch
        /*array = Array(1, "a")*/ //type mismatch array的类型已经是Array[Int], 强类型
        var array2 = Array(1, "a") // Array[Any]
        array2(0) = "b"

        var array3 = Array.apply(List("1", "2", "3"): _*)
        array3 should have length (3)
        array3(2) should be("3")
      }
    }

    describe("内容相等性") {
      it("不能通过==比较内容相等性 ") {
        Array(1, 2) == Array(1, 2) should be(false)
        Array(1, 2) eq Array(1, 2) should be(false)
      }

      it("通过Arrays.equals比较内容相等性") {
        import java.util.Arrays
        Arrays.equals(Array(1, 2), Array(1, 2)) should be(true)
        Arrays.equals(Array(1, 2), Array(1)) should be(false)
      }

      it("通过隐式转换（ArrayOps）后的sameElements方法比较内容相等性") {
        Array(1, 2).sameElements(Array(1, 2)) should be(true)
      }
    }

    describe("数组元素迭代访问") {
      it("通过while") {
        def a[T](elements: T*) = Array(elements)
        val array = a("1", "2", "3")
        var index = 0
        while (index < array.length) {
          println(array(index))
          index += 1
        }
      }

      it("通过隐式转换为Seq后的foreach等") {
        val array = Array(0, 1, 2)
        array.foreach(println)
        array.map(_ + 1) should be === (Array(1, 2, 3)) //scalatest 使用 === 比较数组内容的相等性
      }
    }
  }
}


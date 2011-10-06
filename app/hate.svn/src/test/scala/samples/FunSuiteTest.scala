package samples

import org.scalatest.FunSuite
import scala.collection.mutable.Stack
 
class FunSuiteTest extends FunSuite {

  test("1 + 1") {
    assert( (1 + 1).toString === "2" )
  }
 
  test("1 / 0") {
    intercept[ArithmeticException] {
      1 / 0
    }
  }
  
}
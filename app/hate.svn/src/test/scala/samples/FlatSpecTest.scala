package samples

import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class StackSpecTest extends FlatSpec with ShouldMatchers {

  "test" should "1 + 1 = 2" in {
    1 + 1  should equal (2)
  }
  
  it should """(1 + 1).toString == "2" """ in {
  	(1 + 1).toString should be ("2")
  }

  it should "1 / 0 throws ArithmeticException" in {
    evaluating { 1/0 } should produce [ArithmeticException]
  }
  
}
package learn.scalaxio

import java.io.StringReader
import scalax.io.Resource

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

class ReaderTest extends FunSuite with ShouldMatchers {
  test("read something as chars") {
    val in = Resource.fromReader(new StringReader("hello"))
    val number = in.chars.filter("ailo" contains _).size
    number should be(3)
  }
}

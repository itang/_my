package learn_guava

import scala.collection.JavaConversions._

import com.google.common.net.InternetDomainName
import com.google.common.collect.Lists

import org.scalatest._
import org.scalatest.matchers._

class InternetDomainNameTest extends FunSuite with ShouldMatchers {
  test("constructor") {
    evaluating {
      InternetDomainName.fromLenient("http://beehao.appspot.com")
    } should produce[IllegalArgumentException]
    
    val beehao = InternetDomainName.fromLenient("beehao.appspot.com")
    beehao.name should be ("beehao.appspot.com")
    beehao.parts should have length (3)
    beehao.parts.toArray should be (Array("beehao", "appspot", "com"))
    beehao.parts should equal (Lists.newArrayList("beehao", "appspot", "com"))

    beehao.hasParent should be(true)
    beehao.parent.name should be("appspot.com")
		
  }

}

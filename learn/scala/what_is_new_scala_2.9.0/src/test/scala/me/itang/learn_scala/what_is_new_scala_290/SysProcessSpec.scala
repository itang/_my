package me.itang.learn_scala.what_is_new_scala_290

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

/**
 * @see http://code.google.com/p/simple-build-tool/wiki/Process
 */
class SysProcessSpec extends Spec with ShouldMatchers {
  describe("scala.sys") {
    //TODO
  }

  describe("scala.sys.process") {
    import scala.sys.process._
    it("!!") {
      val re = "find project -name *.jar".!!
      re.contains("scala") should be(true)
    }

    it("指定work dir") {
      val re = ((new java.lang.ProcessBuilder("ls", "-l")) directory new java.io.File(System.getProperty("user.home"))).!!
      re.contains("workspace") should be(true)
    }

    it("#>") {
      import java.io._
      import java.net._
      new URL("http://databinder.net/dispatch/About") #> new File("target/About.html") !
    }
  }
}
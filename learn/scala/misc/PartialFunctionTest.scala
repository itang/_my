package test

import org.junit._
import Assert._

class PartialFunctionTest {
  @Test
  def test_pf {
    val b: PartialFunction[Any, String] = {
      case str: String => str
      case 1 => "1"
    }

    assertTrue(b.isInstanceOf[Function1[_, _]])
    assertTrue(b("hello") == "hello")
    assertEquals("1", b(1))

    assertTrue(b.isInstanceOf[PartialFunction[_, _]])
    assertTrue(b.isDefinedAt("hello"))
    assertTrue(b.isDefinedAt(1))
    assertTrue(!b.isDefinedAt(2))
    try {
      b(2)
      fail()
    } catch {
      case e: Exception => Console.err.println(e.toString)
    }
  }

  @Test
  def test_combine_pfs {
    assertTrue(new AFactory()("A").isDefined)
    assertFalse(new AFactory()("AA").isDefined)

    val aaf = new AFactory addMeta {
      case "AA" => new AA
    }
    assertTrue(aaf.apply("AA").isDefined)
  }

  class A {
    override def toString = "A"
  }
  class AA extends A {
    override def toString = "AA"
  }

  class AFactory {
    protected val meta: PartialFunction[String, A] = {
      case "A" => new A
    }

    def addMeta(m: PartialFunction[String, A]) = new AFactory {
      override val meta = AFactory.this.meta orElse m
    }

    def apply(name: String): Option[A] = meta.lift(name)
  }

}


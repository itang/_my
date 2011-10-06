package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers
class BufferSpec extends Spec with ShouldMatchers {
  import collection.mutable.Buffer
  describe("Buffers") {
    describe("构造") {
      val buf = Buffer(1, 2, 3)
      buf should have size (3)
    }

    describe("继承自Seq") {
      classOf[Seq[_]] isAssignableFrom classOf[Buffer[_]] should be(true)
    }

    describe("Additions Removals") {
      val buf = Buffer("a", "b", "c")
      buf += "d"
      buf.last should be("d")
      buf += ("e", "f")
      buf should be(Buffer("a", "b", "c", "d", "e", "f"))

      "z" +=: buf
      buf.head should be("z")
      Array("1", "2") ++=: buf
      buf.take(2) should be(Buffer("1", "2"))

      buf insert (2, "3")
      buf(2) should be("3")
      buf insertAll (3, List("4", "5"))

      buf indexOfSlice (Buffer("4", "5")) should be(3)

      (1 to 5) foreach (it => buf -= it.toString)
      buf(0) should be("z")

      buf remove 0
      buf(0) should be("a")

      buf trimStart 4
      buf should be(Buffer("e", "f"))
      buf trimEnd 1
      buf should be(Buffer("e"))

      buf clear

      buf should have size (0)

      buf += "a"
      buf.clone should be(Buffer("a"))
    }
  }
}
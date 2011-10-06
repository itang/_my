package me.itang.learn_scala.collections

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class MutableSpec extends Spec with ShouldMatchers {
  describe("Array Buffers") {
    import scala.collection.mutable.ArrayBuffer
    it("An ArrayBuffer buffer holds an array and a size") {
      val buf = ArrayBuffer.empty[Int]
      buf.length should be(0)
      buf += 1
      buf += (2, 3)
      buf.length should be(3)

      buf(2) should be(3)
    }

    it("array buffers are useful for efficiently building up a large collection whenever the new items are always added to the end.") {
      val buf = ArrayBuffer(1, 2)
      for (i <- 0 to 100) {
        buf += i
      }
      buf should have length (2 + 100 + 1)
    }

    it("toArray") {
      val array = ArrayBuffer(1, 2, 3).toArray
      array.sameElements(Array(1, 2, 3)) should be(true)
    }
  }
  describe("List Buffers") {
    import scala.collection.mutable.ListBuffer
    it("linked list internally instead of an array") {
      val buf = ListBuffer.empty[Int]
      buf += 1
      buf += (2, 3)
      buf.length should be(3)
    }
    it("toList onvert the buffer to a list once it is built up") {
      val buf = ListBuffer(1, 2, 3)
      buf.toList should be(List(1, 2, 3))
    }
  }
  describe("StringBuilders") {
    //import scala.collection.mutable.StringBuilder
    val sb = new StringBuilder
    sb.+=('a')
    sb ++= "hello"
    sb.append("world")
    sb.toString should be("ahelloworld")

    StringBuilder.newBuilder.append("hello,world").filter(_ > 'r').toString should be("w")
  }
  describe("Linked Lists") {
    import scala.collection.mutable.LinkedList
    val xs = LinkedList(1, 2)
    xs insert LinkedList(3, 4)
    xs.length should be(4)
  }
  describe("Double Linked Lists") {
    import scala.collection.mutable.DoubleLinkedList
    val xs = DoubleLinkedList(1, 2, 3)

    xs.drop(1) should be(DoubleLinkedList(2, 3))
  }
  describe("Mutable Lists") {
    import scala.collection.mutable.MutableList
    val xs = MutableList(1, 2)
    xs += 3
    xs.length should be(3)
    it("standard implemention of mutable.LinearSeq") {
      val seq = scala.collection.mutable.LinearSeq(1, 2, 3)
      seq.isInstanceOf[MutableList[_]]
    }
  }
  describe("Queues") {
    import scala.collection.mutable.Queue
    val queue = new Queue[String]
    queue += "a" // immutable use enqueue
    queue ++= List("b", "c")
    queue.dequeue should be("a")
    queue should be(Queue("b", "c"))
  }
  describe("Array Sequences") {
    import scala.collection.mutable.ArraySeq
    val a = ArraySeq("a", "b", "c")
    a(0) should be("a")
    a.toArray should be(Array("a", "b", "c"))
  }
  describe("Stacks") {
    import scala.collection.mutable.Stack
    val stack = new Stack[Int]
    stack push 1
    stack should be(Stack(1))
    stack push 2
    stack.top should be(2)
    stack.pop should be(2)
    stack should be(Stack(1))
  }
  describe("Array Stacks") {
    import scala.collection.mutable.ArrayStack
    val stack = ArrayStack.empty[Int]
    stack push 1
    stack push 2
    stack push 3
    stack.top should be(3)
    stack.pop
    stack.pop
    stack should be(ArrayStack(1))
  }
  describe("Hash Tables") {
    val map = scala.collection.mutable.HashMap.empty[Int, String]
    map += (1 -> "make a web site")
    map += (3 -> "profit!")
    map(1) should be("make a web site")
    (map contains 2) should be(false)
  }
  describe("Weak Hash Maps") {
    //TODO 进一步
  }
  describe("Concurrent Maps") {
    import scala.collection.JavaConversions._
    val map: scala.collection.mutable.ConcurrentMap[Int, String] = new java.util.concurrent.ConcurrentHashMap[Int, String]
    map += (1 -> "a")
    map putIfAbsent (1, "b")
    map(1) should be("a")
  }
  describe("Mutable Bitsets") {
    val bits = scala.collection.mutable.BitSet.empty
    bits += 1
    bits += 3
    bits should be(scala.collection.mutable.BitSet(1, 3))
  }

}


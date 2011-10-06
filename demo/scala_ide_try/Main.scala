package t

import scala.io.Source

class RichInt(i: Int) {
  def times(block: => Unit) {
    intToRange(i).foreach { it =>
      block
    }
  }

  def times(block: Int => Unit) {
    intToRange(i).foreach { it =>
      block(it)
    }
  }

  private def intToRange(i: Int) = if (i >= 0) (0 until i) else (i until 0)

}

object Main {
  implicit def int2rich(i: Int) = new RichInt(i)

  def main(args: Array[String]): Unit = {
    1.times(println("hello"))

    10.times { index =>
      println(index)
    }

    0.times {
      println("0: ")
    }

    -1.times {
      println("-1")
    }

    -10.times { it =>
      println("-10: " + it)
    }
  }

}
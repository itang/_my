
import com.hazelcast.core.Hazelcast
import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

import scala.actors.Actor
import scala.actors.Actor._
import scatang._
/**
 * Distributed Queue.
 */
object QueueTest extends App {

  val q: BlockingQueue[MyTask] = Hazelcast.getQueue("tasks")
  val LEN = 1000
  time {
    LEN times { i =>
      q.add(MyTask(i))
      // Thread.sleep(1000)
    }

    LEN.times { _=>
      val task: MyTask = q.take()
      (task())
    }
  }
  
  Hazelcast.shutdownAll()

  def time(p: => Unit) {
    val s = System.currentTimeMillis()
    p
    println((System.currentTimeMillis() - s) / 1000.0 + "s")
  }
}

case class MyTask(n: Int) {
  def apply() = (n, (0 to n).sum)
}
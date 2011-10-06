package learn_guava

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Callable

import org.scalatest.FunSuite
import org.scalatest.matchers._
import com.google.common._

class ExecutorServiceTest extends FunSuite with ShouldMatchers {
  test("use ExecutorService") {
    val executorService = Executors.newFixedThreadPool(2)
    val future = executorService.submit(new Callable[String] {
      def call: String = "Hello"
    })
    println("start...")
    println(future.get)
    future.get should be ("Hello")
    
    executorService.shutdown()
  }
}

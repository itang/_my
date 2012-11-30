package test

import java.util.concurrent.TimeUnit._

import scala.concurrent.Future
import scala.concurrent.duration.{ Duration }
import akka.actor.ActorSystem
import spray.caching.{ LruCache, Cache }
import spray.util._

object Main extends App {
  implicit val system = ActorSystem()

  def expensiveOp(): Double = {
    Thread.sleep(500)
    new util.Random().nextDouble()
  }

  val cache: Cache[Double] = LruCache()

  def cachedOp[T](key: T): Future[Double] = cache(key) {
    expensiveOp()
  }

  println(cachedOp("foo").await)
  println(cachedOp("bar").await(Duration(100, MILLISECONDS)))

  system.shutdown()
}

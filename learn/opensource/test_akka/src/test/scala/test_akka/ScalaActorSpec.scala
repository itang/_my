package test_akka

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

object Tick

class ScalaActorSpec extends Spec with ShouldMatchers {
  describe("Scala Actor") {
    describe("构造") {
      it("可以通过继承Actor类") {
        import scala.actors.Actor
        class MyActor extends Actor {
          def act() { //覆写act方法, 实现Actor逻辑
            receive {
              case Tick =>
                reply(Tick)
            }
          }
        }

        val a = new MyActor //构建Actor实例
        a.start() //启动
        (a !? Tick) should be(Tick)
      }

      it("可以通过actor Builder来构建") {
        import scala.actors.Actor._ // 导入object Actor 的 actor 方法( builder)
        val a = actor {
          println("from actor builder")
          receive {
            case Tick => reply(Tick)
          }
        }

        a !? Tick should be(Tick) //!? 等待回复
      }
    }
    
  }
}
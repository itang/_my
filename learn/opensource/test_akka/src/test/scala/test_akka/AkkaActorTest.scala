package test_akka

import org.scalatest.Spec
import org.scalatest.matchers.ShouldMatchers

class AkkaActorTest extends Spec with ShouldMatchers {
    describe("Akka Actor") {
        describe("构建") {
            it("通过继承Actor类") {
                import akka.actor.Actor
                class SubActor extends Actor {
                    var counter = 1
                    object Tick
                    def receive = { //as PartialFunction
                        case Tick =>
                            counter += 1
                            println(counter)
                    }
                }
                import akka.actor.Actor._
                val counter = actorOf(new SubActor).start //actorOf[SubActor] //# 这种引用方式SubActor须定义在class和trait外面
                counter ! Tick
                Actor.registry.actors should have length (1)
                counter.stop
                Actor.registry.actors should have length (0)
                counter.exit()
                Actor.registry.actors should have length (0)
            }

        }
    }

    import akka.actor.{ Actor, ActorRef }
    import akka.actor.Actor._
    describe("销毁") {
        it("单个Actor stop退出") {
            assertExit { _.stop }
        }

        it("单个Actor exit退出") {
            assertExit { _.exit }
        }

        it("关闭全部Actor实例") {
            assertExit((1 to 10).map(it => actorOf(actorInstance))) { a =>
                Actor.registry.shutdownAll()
            }
        }
    }

    describe("生命周期回调") {

        it("preStart方法在start后先调用") {
            var prestart = false
            class PreCallbackTest extends Actor {
                override def preStart = {
                    prestart = true
                }

                def receive = {
                    case _ =>
                        prestart = false
                }
            }
            withActor(new PreCallbackTest) { ref =>
                prestart should be(false)
                ref.start()

                prestart should be(true)
                ref !! "some message"
                prestart should be(false)
            }
        }

        it("postStop方法在stop后调用") {
            var poststop = false
            class PostCallTest extends Actor {
                def receive = {
                    case _ =>
                        poststop = false
                }
                override def postStop = {
                    poststop = true
                }
            }
            withActor(new PostCallTest) { ref =>
                poststop should be(false)
                ref.start()
                ref !! "some"
                poststop should be(false)
            }
            poststop should be(true)
        }
    }

    describe("消息交互模式") {
        class PatternTest extends Actor {
            def receive = {
                case "reply" => Thread.sleep(2000); self.reply("hah")
                case "timeout" => Thread.sleep(1000 * 6); self.reply("hah")
                case e => Thread.sleep(2000); println(e)
            }
        }
        it("! fire-forget模式, （异步发送模式)") {
            withActor(new PatternTest, true) { actor =>
                val start = System.currentTimeMillis
                actor ! "hello"
                var end = System.currentTimeMillis
                (end - start) should be < (100L)
                actor ! "reply"
                end = System.currentTimeMillis
                (end - start) should be < (100L)
            }
        }

        it("!! uses Future under the hood(with time-out)") {
            withActor(new PatternTest, true) { actor =>
                val start = System.currentTimeMillis
                var result = actor !! "hello"
                (System.currentTimeMillis - start) should be >= (2000L)
                result should be(None)
                result = actor !! "reply"
                result should equal(Some("hah"))

                //default timeout 5seconds
                actor !! "timeout" should be(None)
                actor !! ("timeout", akka.util.Duration(7, "seconds").toMillis) should be(Some("hah"))
                //##evaluating { actor !! "timeout" } should produce [akka.dispatch.FutureTimeoutException]
            }
        }
    }

    def assertExit(actors: Seq[ActorRef])(proc: ActorRef => Unit) {
        actors.map(_.start)
        Actor.registry.actors should have length (actors.size)
        actors foreach proc
        Actor.registry.actors should have length (0)
    }

    def assertExit(proc: ActorRef => Unit) {
        assertExit(actorOf(actorInstance).start :: Nil)(proc)
    }

    def actorInstance = new akka.actor.Actor {
        def receive = {
            case Tick => println(Tick)
        }
    }

    def withActor[T](actor: => Actor, start: Boolean = false)(proc: ActorRef => T): T = {
        val ref = actorOf(actor)
        if (start) {
            ref.start()
        }
        val result = proc(ref)
        if (!ref.isShutdown) {
            ref.stop()
        }
        result
    }
}
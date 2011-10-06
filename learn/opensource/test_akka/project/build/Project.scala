import sbt._
import de.element34.sbteclipsify._

/**
 * Project 定义.
 *
 * @author itang
 */
class Project(info: ProjectInfo) extends DefaultProject(info)
  with IdeaProject with Eclipsify
  //with Repositories
  //with Configurations
  with TestDependencies
  with MyAkkaProject {
  override def javaCompileOptions = super.javaCompileOptions ++ javaCompileOptions("-encoding", "UTF-8")
  override def compileOptions = super.compileOptions ++ compileOptions("-encoding", "UTF-8")
  //more
}

trait TestDependencies { self: DefaultProject =>
  val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"
}

/**
 * This adds akka-actor as a dependency by default.
 */
trait MyAkkaProject extends AkkaProject {
  val akkaRepo = MavenRepository("Akka Repo", "http://akka.io/repository")

  //    val akkaStm = akkaModule("stm")
  //    val akkaTypedActor = akkaModule("typed-actor")
  //    val akkaRemote = akkaModule("remote")
  //    val akkaHttp = akkaModule("http")
  //    val akkaAmqp = akkaModule("amqp")
  //    val akkaCamel = akkaModule("camel")
  //    val akkaJta = akkaModule("jta")
  //    val akkaKernel = akkaModule("kernel")
  //    val akkaCassandra = akkaModule("persistence-cassandra")
  //    val akkaMongo = akkaModule("persistence-mongo")
  //    val akkaRedis = akkaModule("persistence-redis")
  //    val akkaSpring = akkaModule("spring")
}

trait Configurations { self: DefaultProject =>
  //
}

trait Repositories {
  //
}

import sbt._
import de.element34.sbteclipsify._

/**
 * Project 定义.
 *
 * @author itang
 */
class Project(info: ProjectInfo) extends DefaultProject(info)
  with IdeaProject with Eclipsify {
  override def javaCompileOptions = super.javaCompileOptions ++ javaCompileOptions("-encoding", "UTF-8")
  override def compileOptions = super.compileOptions ++ compileOptions("-encoding", "UTF-8")

  val mongodbDriver = "org.mongodb" % "mongo-java-driver" % "2.5.2" withSources ()

  val scalatest = "org.scalatest" %% "scalatest" % "1.4.1" % "test"
}

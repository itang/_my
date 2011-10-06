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

	//val guice = "com.google.inject" % "guice" % "3.0-rc2"

	val scalatest = "org.scalatest" %% "scalatest" % "1.4.1" % "test"
}

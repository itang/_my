import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {
  val core = "com.github.scala-incubator.io" %% "core" % "0.1.0"
  val file = "com.github.scala-incubator.io" %% "file" % "0.1.0"

  val scalatest = "org.scalatest" % "scalatest" % "1.3" % "test"
}

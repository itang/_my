import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info){
  val guava = "com.google.guava" % "guava" % "r08"
  val scalatest = "org.scalatest" % "scalatest" % "1.3"
}

import sbt._
import de.element34.sbteclipsify._
class MyProject(info: ProjectInfo) extends DefaultWebProject(info)  with Eclipsify  {
  val scalatraVersion = "2.0.0.M2"

  val servletApi = "org.mortbay.jetty" % "servlet-api" % "2.5-20081211" % "provided"
  val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.25" % "test"
  //支持jsp
  val jettyJsp = "org.mortbay.jetty" % "jsp-2.1-glassfish" % "2.1.v20091210"
  val jstl = "javax.servlet" % "jstl" % "1.2"
  
  val  scalatra = "org.scalatra" %% "scalatra" % scalatraVersion
  val scalate = "org.scalatra" %% "scalatra-scalate" % scalatraVersion

  // Alternatively, you could use scalatra-specs
  val scalatest = "org.scalatra" %% "scalatra-scalatest" % scalatraVersion % "test"

  // Pick your favorite slf4j binding
  val slf4jBinding = "ch.qos.logback" % "logback-classic" % "0.9.25" % "runtime"

  // http://groups.google.com/group/simple-build-tool/msg/1f17b43807d06cda
  override def testClasspath = super.testClasspath +++ buildCompilerJar

  val sonatypeNexusSnapshots = "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
  // For Scalate
  val fuseSourceSnapshots = "FuseSource Snapshot Repository" at "http://repo.fusesource.com/nexus/content/repositories/snapshots"
}

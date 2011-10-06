import sbt._
import de.element34.sbteclipsify._

/**
 * Project 定义.
 * 
 * @author itang
 */
class Project(info: ProjectInfo) extends DefaultProject(info)
  with IdeaProject with Eclipsify {
  /** mvn repositories **/
  val guice_repo = "guice maven" at "http://guice-maven.googlecode.com/svn/trunk"
  val sonatype_public = "SnakeYAML repository" at "http://oss.sonatype.org/content/groups/public"

  /** dependencies **/
  /* default scope dependencies */
  //val spring_context = "org.springframework" % "spring-context" % "3.0.5.RELEASE"
  val spring_context_support = "org.springframework" % "spring-context-support" % "3.0.5.RELEASE"
  val javax_mail = "javax.mail" % "mail" % "1.4.3"
  val jyaml = "org.jyaml" % "jyaml" % "1.3"
  val snakeyaml = "org.yaml" % "snakeyaml" % "1.7"

  /* test scope dependencies */
  val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
}

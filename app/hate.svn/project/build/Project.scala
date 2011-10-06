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
    val scala_repo_snapshots = "scala_repo_snapshots" at "http://www.scala-tools.org/repo-snapshots"
    val guice_repo = "guice maven" at "http://guice-maven.googlecode.com/svn/trunk"
    val qtjambi_repo = "qtjambi maven " at "http://qtjambi.sourceforge.net/maven2"

    val QtjambiVersion = "4.5.2_01" // "4.6.3"

    /** dependencies **/
    /* default scope dependencies */
    val guice = "com.google.code.guice" % "guice" % "2.0.1"
    //val guice_servlet = "com.google.code.guice" % "guice-servlet" % "2.0.1"
    val qtjambi = "net.sf.qtjambi" % "qtjambi" % QtjambiVersion
    val qtjambi_platform_win32 = "net.sf.qtjambi" % "qtjambi-platform-win32" % QtjambiVersion

    /* test scope dependencies */
    val scalatest = "org.scalatest" % "scalatest" % "1.2-for-scala-2.8.0.final-SNAPSHOT" % "test"
}

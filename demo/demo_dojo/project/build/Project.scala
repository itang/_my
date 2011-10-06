import sbt._
import de.element34.sbteclipsify._

/**
 * Project 定义.
 * 
 * @author itang
 */
class Project(info: ProjectInfo) extends DefaultWebProject(info) 
	with IdeaProject with Eclipsify {	
	/** mvn repositories **/
	
	val servlet_api = "javax.servlet" % "servlet-api" % "2.5"  % "provided"
	//val jsp_api = "javax.servlet.jsp" % "jsp-api" % "2.1" % "provided"
	//val jstl = "javax.servlet" % "jstl" % "1.2" % "provided"

  	/* test scope dependencies */
	val scalatest = "org.scalatest" % "scalatest" % "1.2" % "test"
  	
  	val jasper = "org.apache.tomcat" % "jasper" % "6.0.18" % "test"
  	val jetty7 = "org.eclipse.jetty" % "jetty-webapp" % "7.0.2.RC0" % "test"
  	val jetty7Server = "org.eclipse.jetty" % "jetty-server" % "7.0.2.RC0" % "test"
}


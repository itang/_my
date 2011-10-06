import sbt._

/**
 * 定义sbt插件
 *
 * @author itang
 */
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
    //Sbt Idea Plugin	
    val sbtIdeaRepo = "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"
    val sbtIdea = "com.github.mpeltonen" % "sbt-idea-plugin" % "0.4.0"

    //akka
    val akkaRepo = "Akka Repo" at "http://akka.io/repository"
    val akkaPlugin = "se.scalablesolutions.akka" % "akka-sbt-plugin" % "1.0"

    //Sbt Eclipse Plugin
    lazy val eclipsify = "de.element34" % "sbt-eclipsify" % "0.7.0"
}

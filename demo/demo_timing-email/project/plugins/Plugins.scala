import sbt._

/**
 * 定义sbt插件
 *
 * @author itang
 */
class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
    //Sbt Idea Plugin	
    val idea_plugin_repo = "GH-pages repo" at "http://mpeltonen.github.com/maven/"
    lazy val idea_plugin = "com.github.mpeltonen" % "sbt-idea-plugin" % "0.1-SNAPSHOT"

    //Sbt Eclipse Plugin
    lazy val eclipsify = "de.element34" % "sbt-eclipsify" % "0.6.0"
}

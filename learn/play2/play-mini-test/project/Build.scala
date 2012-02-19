import sbt._
import Keys._

object PlayMiniApplicationBuild extends Build {

  lazy val buildVersion = "0.1.0-SNAPSHOT"

  lazy val typesafe = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"
  lazy val typesafeSnapshot = "Typesafe Snapshots Repository" at "http://repo.typesafe.com/typesafe/snapshots/"
  lazy val repo = if (buildVersion.endsWith("SNAPSHOT")) typesafeSnapshot else typesafe

  lazy val root = Project(
    id = "play-mini-application",
    base = file("."),
    settings = Project.defaultSettings
  ).settings(
      libraryDependencies += "com.typesafe" %% "play-mini" % "2.0-RC3-SNAPSHOT" withSources (),
      resolvers += repo,
      mainClass in (Compile, run) := Some("play.core.server.NettyServer")
    )
}
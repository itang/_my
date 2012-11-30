/* basic project info */
name := "test"

organization := "test"

version := "0.1"

description := "test"

startYear := Some(2012)

/* scala versions and options */
scalaVersion := "2.10.0-RC3"

// crossScalaVersions := Seq("2.9.1")

offline := false

scalacOptions ++= Seq("-deprecation", "-unchecked")

// scalacOptions ++= Seq("-Ydependent-method-types") // if using shapeless

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

mainClass in (Compile, run) := Some("test.Main")

/* dependencies */
libraryDependencies ++= Seq (
	"io.spray" % "spray-io" % "1.1-M5",
	"io.spray" % "spray-caching" % "1.1-M5",
	"com.typesafe.akka" % "akka-actor_2.10.0-RC3" % "2.1.0-RC3"
)

resolvers ++= Seq(
	"spray repo" at "http://repo.spray.io"
)


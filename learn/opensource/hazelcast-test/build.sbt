name := "hazelcast-test"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.9.1"

organization := "me.itang"

javaOptions += "-Xmx912m"

javacOptions ++= Seq("-encoding", "UTF-8")

javacOptions ++= Seq("-source", "1.5", "-target", "1.5")

//1.9.4

libraryDependencies ++= Seq("com.hazelcast" % "hazelcast" % "2.0-RC2",
                            "org.scalatest" % "scalatest_2.9.0" % "1.6.1" % "test")

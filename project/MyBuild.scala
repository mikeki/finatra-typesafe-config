import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object MyBuild extends Build {

  val finatraVersion = "2.0.0"

  lazy val root = Project(
    "Example",
    file(".")).settings(

    ).settings(
      name := "finatra-typesafe-config",
      organization := "is.kow",
      version := "1.0-SNAPSHOT",
      scalaVersion := "2.11.7",
      scalacOptions := Seq(
        "-encoding", "UTF-8", "-deprecation", "-feature", "-unchecked",
        "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-unused-import",
        "-language:existentials", "-language:higherKinds", "-language:implicitConversions"),
      ivyScala := ivyScala.value map {
        _.copy(overrideScalaVersion = true)
      },
      fork in (Test, test) := true,
      javaOptions in (Test, test) += "-Dconfig.trace=loads",
      assemblyMergeStrategy in assembly := {
        case "BUILD" => MergeStrategy.discard
        case other => MergeStrategy.defaultMergeStrategy(other)
      },
      resolvers ++= Seq(
        "Twitter maven" at "http://maven.twttr.com"
      ),
      libraryDependencies ++= Seq(
        "com.twitter.finatra" %% "finatra-http" % finatraVersion,
        "com.twitter.finatra" %% "finatra-slf4j" % finatraVersion,
        "com.twitter.finatra" %% "finatra-jackson" % finatraVersion,
        "ch.qos.logback" % "logback-classic" % "1.1.3",

        "com.mashape.unirest" % "unirest-java" % "1.4.6", //Trying a simple Rest framework
        "org.apache.velocity" % "velocity" % "1.7", // Template engine instead of twirl
        "com.typesafe.play" %% "play-json" % "2.4.2", //For awesome JSON parsing!
        "com.github.racc" % "typesafeconfig-guice" % "0.0.1",
        "com.typesafe" % "config" % "1.3.0", //Typesafe config library requires java8

        //Test dependencies for finatra
        "com.twitter.finatra" %% "finatra-http" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-server" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-app" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-core" % finatraVersion % "test",
        "com.twitter.inject" %% "inject-modules" % finatraVersion % "test",
        "com.twitter.finatra" %% "finatra-http" % finatraVersion % "test" classifier "tests",
        "com.twitter.finatra" %% "finatra-jackson" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-server" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-app" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-core" % finatraVersion % "test" classifier "tests",
        "com.twitter.inject" %% "inject-modules" % finatraVersion % "test" classifier "tests",

        "org.mockito" % "mockito-core" % "1.10.19" % "test",
        "org.scala-lang.modules" %% "scala-xml" % "1.0.3" % "test", //I want the xml support in test mode
        "org.scalatest" %% "scalatest" % "2.2.4" % "test", // testing
        "junit" % "junit" % "4.12" % "test"
      ).map(_.exclude("commons-logging", "commons-logging"))
    )
}

// vim: set ts=4 sw=4 et:

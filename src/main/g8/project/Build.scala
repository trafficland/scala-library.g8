import sbt._
import Keys._
import trafficland.sbt.plugins.TrafficLandStandardPluginSet
import trafficland.opensource.sbt.plugins._

object Build extends sbt.Build {
  import Dependencies._

  lazy val myProject = Project("$name$", file("."))
    .settings(TrafficLandStandardPluginSet.plugs :_*)
    .settings(
      isApp := false,
      version := "0.1.0-SNAPSHOT".toReleaseFormat,
      resolvers ++= Dependencies.resolutionRepos,
      libraryDependencies ++= compileDeps ++ testDeps,
      testListeners += SbtTapReporting(),
      parallelExecution in Test := false      
    )
}

object Dependencies {
  val resolutionRepos = Seq(
    "TrafficLand Artifactory Server (with default Maven pattern)" at "http://build01.tl.com:8081/artifactory/repo"
  )

  object V {
    val mockito     = "1.9.0"
    val slf4j       = "1.6.4"
    val logback     = "1.0.0"
    val scalatest   = "2.0.M5b"
    val junit       = "4.9"
  }

  val compileDeps = Seq(
    "org.slf4j"                 %  "slf4j-api"          % V.slf4j,
    "ch.qos.logback"            %  "logback-classic"    % V.logback
  )

  val testDeps = Seq(
    "junit"                     %  "junit"                 % V.junit,
    "org.mockito"               %  "mockito-core"          % V.mockito,
    "org.scalatest"             %% "scalatest" 	           % V.scalatest
  )
}

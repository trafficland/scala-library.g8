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
      libraryDependencies ++= Seq(
        CompileDeps.slf4j,
        CompileDeps.logback,
        TestDeps.scalatest,
        TestDeps.mockito,
        TestDeps.junit
      ),
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
    val ning        = "1.7.4"
    val scalatest   = "2.0.M5b"
    val junit       = "4.9"
  }

  object CompileDeps {
    val slf4j       = "org.slf4j"                 %  "slf4j-api"          % V.slf4j
    val logback     = "ch.qos.logback"            %  "logback-classic"    % V.logback
  }

  object TestDeps {
    val junit       = "junit"                     %  "junit"                 % V.junit
    val mockito     = "org.mockito"               %  "mockito-core"          % V.mockito
    val scalatest   = "org.scalatest"             %% "scalatest" 	           % V.scalatest
  }
}

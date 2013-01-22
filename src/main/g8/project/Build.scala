import sbt._
import Keys._


object Build extends sbt.Build {
  import Dependencies._

  lazy val myProject = Project("$name$", file("."))
    .settings(
      organization  := "com.trafficland",
      version       := "0.1.0-SNAPSHOT",
      scalaVersion  := "2.10.0",
      scalacOptions := Seq("-deprecation", "-encoding", "utf8"),
      resolvers     ++= Dependencies.resolutionRepos,
      libraryDependencies ++= Seq(
        CompileDeps.slf4j,
        CompileDeps.logback,
        TestDeps.scalatest,
        TestDeps.mockito,
        TestDeps.junit
      ),
      testListeners += SbtTapReporting(),
      parallelExecution in Test := false,
      credentials += Credentials("Artifactory Realm", "build01.tl.com", "$artifactory_user$", "$artifactory_password$"),
      publishTo <<= (version) {
        version: String =>
          val repo = "http://build01.tl.com:8081/artifactory/"
          if (version.trim endsWith "SNAPSHOT") Some("TrafficLand Snapshots" at (repo + "com.trafficland.snapshots"))
          else if (version.trim endsWith "RC") Some("TrafficLand Release Candidates" at (repo + "com.trafficland.releasecandidates"))
          else Some("TrafficLand Releases" at (repo + "com.trafficland.final"))
      }
    )
}

object Dependencies {
  val resolutionRepos = Seq(
    "TrafficLand Artifactory Server" at "http://build01.tl.com:8081/artifactory/repo"
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

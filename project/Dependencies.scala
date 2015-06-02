import play.PlayImport._
import sbt._

object Dependencies {

  val akkaVersion = "2.3.11"

  private val commonAkkaDeps = Seq(
    "com.typesafe.akka" %% "akka-contrib" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion
  )

  private val webjars = Seq(
    "org.webjars" % "bootstrap" % "2.3.1",
    "org.webjars" % "flot" % "0.8.0"
  )

  val frontendProjectDeps = Seq(
    javaWs
  ) ++ webjars ++ commonAkkaDeps

  val backendProjectDeps = Seq(
    "com.typesafe.akka" %% "akka-persistence-experimental" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test
  ) ++ commonAkkaDeps
}
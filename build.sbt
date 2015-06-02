import BuildSettings._

name := "reactive-stocks-java8"

version := "1.0"

scalaVersion := "2.11.6"

lazy val common = (project in file("common"))
  .settings(commonSettings:_*)

lazy val frontend = (project in file("frontend"))
  .enablePlugins(PlayJava)
  .dependsOn(common)
  .aggregate(common)
  .settings(commonSettings:_*)
  .settings(
    libraryDependencies ++= Dependencies.frontendProjectDeps,
    routesGenerator := InjectedRoutesGenerator,
    LessKeys.compress := true
  )

lazy val backend = (project in file("backend"))
  .dependsOn(common)
  .aggregate(common)
  .settings(commonSettings:_*)
  .settings(fork in run := true)
  .settings(libraryDependencies ++= Dependencies.backendProjectDeps)


addCommandAlias("f", ";project frontend; run 9000")
addCommandAlias("b1", ";project backend; runMain services.MainClusterManager 2551")
addCommandAlias("b2", ";project backend; runMain services.MainClusterManager 2552")
addCommandAlias("sj", ";project backend; runMain journal.SharedJournalApp 2553")

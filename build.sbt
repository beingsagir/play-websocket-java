name := "reactive-stocks-java8"

version := "1.0"

scalaVersion := "2.11.6"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

libraryDependencies ++= Seq(
  javaWs,
  "org.webjars" % "bootstrap" % "2.3.2",
  "org.webjars" % "flot" % "0.8.0",
  "com.typesafe.conductr" %% "play24-conductr-bundle-lib" % "0.13.0",
  "org.easytesting" % "fest-assert" % "1.4" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % Test
)

javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

LessKeys.compress := true

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

routesGenerator := InjectedRoutesGenerator


// Deploy settings
import ByteConversions._
BundleKeys.nrOfCpus := 1.0
BundleKeys.memory := 64.MiB
BundleKeys.diskSpace := 50.MiB
BundleKeys.roles := Set("stocks")
BundleKeys.endpoints := Map("stocks" -> Endpoint("http", services = Set(URI("http://:9000"))))
BundleKeys.startCommand += "-Dhttp.address=$STOCKS_BIND_IP -Dhttp.port=$STOCKS_BIND_PORT"
import com.typesafe.sbteclipse.core.EclipsePlugin.EclipseKeys
import sbt.Keys._
import sbt._

object BuildSettings {

  private val _projectPrompt = { state: State =>
    val extracted = Project.extract(state)
    import extracted._
    (name in currentRef get structure.data).map { name =>
      "[" + name + "] $ "
    }.getOrElse("> ")
  }

  val commonSettings = Seq(
    scalaVersion := "2.11.6",
    shellPrompt := BuildSettings._projectPrompt,
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint"),
    EclipseKeys.skipParents in ThisBuild := false
  )

  initialize := {
    val _ = initialize.value
    if (sys.props("java.specification.version") != "1.8")
      sys.error("Java 8 is required for this project.")
  }
}
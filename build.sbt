sbtPlugin := true

name := "sbt-checkstyle-plugin"

organization := "com.etsy"

version := "0.3.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "com.puppycrawl.tools" % "checkstyle" % "5.5",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.github.stefanbirkner" % "system-rules" % "1.6.0" % "test"
)

publishTo <<= version { (v: String) =>
  val archivaURL = "http://ivy.etsycorp.com/repository"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at (archivaURL + "/snapshots"))
  } else {
    Some("releases"  at (archivaURL + "/internal"))
  }
}

sbtPlugin := true

name := "sbt-checkstyle-plugin"

organization := "com.etsy"

version := "0.1"

libraryDependencies ++= Seq(
  "com.puppycrawl.tools" % "checkstyle" % "5.5"
)

publishTo <<= version { (v: String) =>
  val archivaURL = "http://ivy.etsycorp.com/repository"
  if (v.trim.endsWith("SNAPSHOT")) {
    Some("snapshots" at (archivaURL + "/snapshots"))
  } else {
    Some("releases"  at (archivaURL + "/internal"))
  }
}

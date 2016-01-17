sbtPlugin := true

name := "sbt-checkstyle-plugin"

organization := "com.etsy"

version := "2.0.1-SNAPSHOT"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq(
  "com.puppycrawl.tools" % "checkstyle" % "6.13",
  "net.sf.saxon" % "Saxon-HE" % "9.6.0-5",
  "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test",
  "junit" % "junit" % "4.11" % "test",
  "com.github.stefanbirkner" % "system-rules" % "1.6.0" % "test"
)

xerial.sbt.Sonatype.sonatypeSettings

pomExtra := <url>https://github.com/etsy/sbt-checkstyle-plugin</url>
  <licenses>
    <license>
      <name>MIT License</name>
      <url>http://opensource.org/licenses/MIT</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:etsy/sbt-checkstyle-plugin.git</url>
    <connection>scm:git:git@github.com:etsy/sbt-checkstyle-plugin.git</connection>
  </scm>
  <developers>
    <developer>
      <id>ajsquared</id>
      <name>Andrew Johnson</name>
      <url>github.com/ajsquared</url>
    </developer>
  </developers>

scalastyleConfig := file("scalastyle.xml")

scalastyleFailOnError := true
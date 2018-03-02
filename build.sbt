name := "sbt-checkstyle-plugin"
organization := "com.etsy"

version := "3.1.2-SNAPSHOT"

sbtPlugin := true
crossSbtVersions := Seq("0.13.16", "1.1.0")

libraryDependencies ++= Seq(
  "com.puppycrawl.tools"      %  "checkstyle"   % "6.15",
  "net.sf.saxon"              %  "Saxon-HE"     % "9.6.0-5",
  "org.scalatest"             %% "scalatest"    % "3.0.4"   % "test",
  "junit"                     %  "junit"        % "4.11"    % "test",
  "com.github.stefanbirkner"  %  "system-rules" % "1.6.0"   % "test"
)

xerial.sbt.Sonatype.sonatypeSettings
publishTo := sonatypePublishTo.value

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

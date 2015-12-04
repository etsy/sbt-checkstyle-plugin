version := "0.1"

name := "checkstyle-classpath-config"

organization := "com.etsy"

com.etsy.sbt.Checkstyle.checkstyleSettings
//com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleConfig :=
//  scala.io.Source.fromInputStream()
{
  (dependencyClasspath in Compile).value. match {
    case x => System.out.println(x.toString)
  }
}

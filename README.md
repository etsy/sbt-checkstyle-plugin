# sbt-checkstyle-plugin

This project provides an SBT 0.13+ plugin for running Checkstyle over
Java source files.  For more information about Checkstyle, see
[http://checkstyle.sourceforge.net/](http://checkstyle.sourceforge.net/)

This plugin uses version 5.5 of Checkstyle.

## Setup

Add the following lines to `project/plugins.sbt`

```scala
addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "0.2-SNAPSHOT")

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"	
```

The add the following line to `build.sbt`:

```scala
com.etsy.sbt.Checkstyle.checkstyleSettings
```

## Usage

You can run Checkstyle over your Java source files with the
`checkstyle` task.  You can run Checkstyle over your Java tests with
the `test:checkstyle` task.

The Checkstyle configuration file is `./checkstyle-config.xml` by
default.  This can be changed by setting the value of
`checkstyleConfig`.

The Checkstyle report is output to `target/checkstyle-report.xml` by
default.  This can be changed by setting the value of
`checkstyleTarget`.

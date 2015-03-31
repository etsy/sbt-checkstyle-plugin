# sbt-checkstyle-plugin [![Build Status](https://travis-ci.org/etsy/sbt-checkstyle-plugin.svg?branch=master)](https://travis-ci.org/etsy/sbt-checkstyle-plugin)

This project provides an SBT 0.13+ plugin for running Checkstyle over
Java source files.  For more information about Checkstyle, see
[http://checkstyle.sourceforge.net/](http://checkstyle.sourceforge.net/)

This plugin uses version 6.1 of Checkstyle.

This is a fork of the sbt-code-quality project found
[here](https://github.com/corux/sbt-code-quality).

## Setup

Add the following lines to `project/plugins.sbt`

```scala
addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "0.4.2")
```

Then add the following line to `build.sbt`:

```scala
com.etsy.sbt.Checkstyle.checkstyleSettings
```

## Usage

You can run Checkstyle over your Java source files with the
`checkstyle` task.  You can run Checkstyle over your Java tests with
the `test:checkstyle` task.

The Checkstyle configuration file is `./checkstyle-config.xml` by
default.  This can be changed by setting the value of
`checkstyleConfig`.  By default `test:checkstyle` uses the same
configuration file, but this can be changed by setting the value of
`checkstyleConfig in Test`.

The Checkstyle report is output to `target/checkstyle-report.xml` by
default.  This can be changed by setting the value of
`checkstyleTarget`.  `test:checkstyle` outputs to
`target/checkstyle-test-report.xml`, but this can be changed by
setting the value of `checkstyleTarget in Test`.

You can set `checkstyleConfig` like so in `build.sbt`:
```scala
com.etsy.sbt.Checkstyle.CheckstyleTasks.checkstyleConfig := file("checkstyle-config.xml")
```
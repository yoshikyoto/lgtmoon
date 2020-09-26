scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "42.2.6",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "com.github.tototoshi" %% "slick-joda-mapper" % "2.4.2"
)

name := """lgtm-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  jdbc,
  ws,
  guice,
  specs2 % Test,
  "com.rabbitmq" % "amqp-client" % "3.4.4",
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
  "org.postgresql" % "postgresql" % "42.2.5",
  "org.im4java" % "im4java" % "1.4.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

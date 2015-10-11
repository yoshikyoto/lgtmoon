name := """lgtm-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "com.rabbitmq" % "amqp-client" % "3.4.4",
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "com.typesafe.slick" %% "slick-codegen" % "3.1.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

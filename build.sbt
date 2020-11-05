name := """lgtm-scala"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies ++= Seq(
  ws,
  guice,
  specs2 % Test,
  jdbc,
  jdbc % Test,
  "com.typesafe.slick" %% "slick" % "3.3.2",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
  "org.postgresql" % "postgresql" % "42.2.6",

  // テストで利用している H2 のドライバ
  // https://mvnrepository.com/artifact/com.h2database/h2
  "com.h2database" % "h2" % "1.4.200",

  "org.im4java" % "im4java" % "1.4.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2",
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.github.seratch" %% "awscala-s3" % "0.8.4",
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// slick-migration-api のインストールに必要なリゾルバ
resolvers += Resolver.jcenterRepo

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

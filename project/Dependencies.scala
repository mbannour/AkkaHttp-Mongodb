import sbt._

object Dependencies {

  lazy val circeVersion           = "0.10.0"
  lazy val scalaTestVersion       = "3.0.5"
  lazy val akkaHttpVersion        = "10.1.7"
  lazy val akkaVersion            = "2.5.20"
  lazy val akkaHttpCirce          = "1.23.0"
  lazy val mongoVersion           = "2.5.0"
  lazy val httpExprimentalVersion = "2.4.2"
  lazy val sl4jVesion             = "1.7.25"
  lazy val logbackVersion         = "1.2.3"

  lazy val akkaHttp        = "com.typesafe.akka"          %% "akka-http"              % akkaHttpVersion
  lazy val akkaStream      = "com.typesafe.akka"          %% "akka-stream"            % akkaVersion
  lazy val httpExprimental = "com.typesafe.akka"          %% "akka-http-experimental" % httpExprimentalVersion
  lazy val akkahttpCirce   = "de.heikoseeberger"          %% "akka-http-circe"        % akkaHttpCirce
  lazy val circeGeneric    = "io.circe"                   %% "circe-generic"          % circeVersion
  lazy val mongo           = "org.mongodb.scala"          %% "mongo-scala-driver"     % mongoVersion
  lazy val sl4j            = "org.slf4j"                  % "slf4j-api"               % sl4jVesion
  lazy val logback         = "ch.qos.logback"             % "logback-classic"         % logbackVersion
  lazy val scalaLogging    = "com.typesafe.scala-logging" %% "scala-logging"          % "3.9.2"

  lazy val scalaTest    = "org.scalatest"     %% "scalatest"         % scalaTestVersion
  lazy val akkaHttpTest = "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion

}

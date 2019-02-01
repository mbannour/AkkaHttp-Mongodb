package com.dali

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.dali.user.api.UserRoutes
import com.typesafe.scalalogging.StrictLogging

import scala.io.StdIn

object Main extends App with ServerConfig with StrictLogging {

  implicit val system       = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  val modules = new ModulesWiring with UserRoutes {
    override def _system: ActorSystem = system
  }

  val route = modules.userRoutes

  val bindingFuture = Http().bindAndHandle(route, host, port)

  logger.info(s"Server start at : $host:$port")

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

  StdIn.readLine()
  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ => system.terminate())

}

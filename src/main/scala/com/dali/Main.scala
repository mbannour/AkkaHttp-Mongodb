package com.dali

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.dali.mongo.Mongo
import com.dali.user.api.UserRoutes
import com.dali.user.application.{UserRepository, UserService}
import com.dali.user.domain.User
import org.mongodb.scala.MongoCollection

import scala.io.StdIn

object Main extends App with UserRoutes {

  implicit val system       = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher

  lazy val dao = system.dispatchers.lookup("akka-dao-mongo")

  val mongo = new Mongo()

  val userDao = new UserRepository(mongo)(dao)

  override val userService = new UserService(userDao)(dao)

  val route = userRoutes

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}

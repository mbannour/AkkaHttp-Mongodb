package com.dali.user.api

import com.dali.user.application.UserService
import akka.http.scaladsl.server.Directives.{as, complete, entity, pathPrefix, post}
import akka.http.scaladsl.server.PathMatchers.IntNumber
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import akka.http.scaladsl.server._
import com.dali.user.domain.UserSerialization._
import Directives._
import akka.http.scaladsl.model.StatusCodes
import com.dali.user.domain.User

import scala.util.{Failure, Success}

trait UserRoute {

  val userService: UserService

  def userRoute = pathPrefix("api" / "user") {
    post {
      entity(as[User]) { user =>
        onComplete(userService.insertOneUser(user)) {
          case Success(value) =>
            complete(StatusCodes.NotFound, "user is inserted")
          case Failure(_) => {
            complete(StatusCodes.NotFound)
          }
        }
      }
    } ~ get {
      onComplete(userService.finOneUser()) {
        case Success(user) =>
          complete(user)
        case Failure(_) => {
          complete(StatusCodes.NotFound)
        }
      }
    }
  }
}

package com.dali.user.api

import com.dali.user.application.UserService
import akka.http.scaladsl.server.Directives.{as, complete, entity, pathPrefix, post}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import akka.http.scaladsl.server._
import com.dali.user.domain.UserSerialization._
import Directives._
import akka.http.scaladsl.model.StatusCodes
import com.dali.user.domain.{UpdateUser, User, UserLogin}
import org.mongodb.scala.bson.ObjectId

import scala.util.{Failure, Success}

trait UserRoutes {

  val userService: UserService

  def userRoutes = pathPrefix("api" / "user") {
    path("create") {
      post {
        entity(as[User]) { user =>
          onComplete(userService.insertOneUser(user)) {
            case Success(value) =>
              complete(StatusCodes.OK, "user is inserted")
            case Failure(_) => {
              complete(StatusCodes.NotFound)
            }
          }
        }
      }
    } ~ path("update") {
      patch {
        entity(as[UpdateUser]) { user =>
          onComplete(userService.updateUser(user)) {
            case Success(updatedUser) =>
              complete("user is updated !")
            case Failure(_) =>
              complete(StatusCodes.NotFound)
          }
        }
      }
    } ~ path("delete") {
      delete {
        entity(as[UserLogin]) { user =>
          onComplete(userService.deleteUser(user.login)) {
            case Success(_) =>
              complete(StatusCodes.OK, "user is deleted!")
            case Failure(_) =>
              complete(StatusCodes.NotFound)
          }
        }
      }
    } ~ get {
      parameters('id.as[String]) { id =>
        onComplete(userService.finOneUserById(new ObjectId(id))) {
          case Success(Some(user)) =>
            complete(user)
          case Success(None) =>
            complete(StatusCodes.OK, "Nothing found in the user Database")
          case Failure(_) => {
            complete(StatusCodes.NotFound)
          }
        }
      }
    } ~ get {
      onComplete(userService.findAllUser()) {
        case Success(user) =>
          complete(user)
        case Failure(_) => {
          complete(StatusCodes.NotFound)
        }

      }
    }
  }

}

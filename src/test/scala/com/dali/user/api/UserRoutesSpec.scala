package com.dali.user.api
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.dali.user.application.UserService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import org.scalatest.{FlatSpec, Matchers}

class UserRoutesSpec extends FlatSpec with ScalatestRouteTest with Matchers {

  val userInMemory = new UserInMemory()
  val userRoutes = new UserRoutes {
    override val userService: UserService = new UserService(userInMemory)

  }.userRoutes

  "GET /api/users" should "returns all users" in {
    Get("/api/users") ~> userRoutes ~> check {
      status shouldEqual StatusCodes.OK
    }
  }

  "GET /api/users?id=xxx" should "returns the specific user if it exists" in {
    Get("/api/users?id=507f191e810c19729de860ea") ~> userRoutes ~> check {
      status shouldEqual StatusCodes.OK
    }
  }

  "POST /api/users/create" should "create a new User" in {
    Post(
      "/api/users/create",
      Map(
        "id"       -> "507f191e810c19729de860ea",
        "login"    -> "user",
        "email"    -> "user@gamil.com",
        "password" -> "secret"
      )
    ) ~> userRoutes ~> check {
      status shouldEqual StatusCodes.OK
    }
  }

  "Patch /api/users/update" should "udpdate the old User" in {
    Patch(
      "/api/users/update",
      Map(
        "id"       -> "507f191e810c19729de860ea",
        "email"    -> "user",
        "password" -> "secret"
      )
    ) ~> userRoutes ~> check {
      status shouldEqual StatusCodes.OK
    }
  }

  "DELETE /api/users/delete" should "delete the user if the user is found" in {
    Delete(
      "/api/users/delete",
      Map(
        "login" -> "user"
      )
    ) ~> userRoutes ~> check {
      status shouldEqual StatusCodes.OK
    }
  }

}

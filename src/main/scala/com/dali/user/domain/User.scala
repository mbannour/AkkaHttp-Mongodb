package com.dali.user.domain

import io.circe.{Decoder, Encoder, HCursor}
import org.bson.types.ObjectId
import org.mongodb.scala.bson.ObjectId

import io.circe.syntax._
import io.circe._

case class User(id: ObjectId, login: String, email: String, password: String)

object UserSerialization {

  implicit val encoder: Encoder[User] = (user: User) => {
    Json.obj(
      "id"       -> user.id.toHexString.asJson,
      "login"    -> user.login.asJson,
      "email"    -> user.email.asJson,
      "password" -> user.password.asJson
    )
  }

  implicit val decoder: Decoder[User] = (c: HCursor) => {
    for {
      login    <- c.downField("login").as[String]
      email    <- c.downField("email").as[String]
      password <- c.downField("password").as[String]
    } yield User(ObjectId.get, login, email, password)
  }

}

package com.dali.user.domain

import io.circe.HCursor
import org.mongodb.scala.bson.ObjectId

import io.circe.syntax._
import io.circe._
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
case class User(_id: ObjectId, login: String, email: String, password: String)

case class UpdateUser(_id: ObjectId, login: Option[String], email: Option[String], password: Option[String])

case class UserLogin(login: String)

object UserSerialization {

  implicit val encoder: Encoder[User] = (user: User) => {
    Json.obj(
      "id"       -> user._id.toString.asJson,
      "login"    -> user.login.asJson,
      "email"    -> user.email.asJson,
      "password" -> user.password.asJson
    )
  }

  implicit val decoder: Decoder[User] = (c: HCursor) => {
    for {
      id       <- c.downField("id").as[String]
      login    <- c.downField("login").as[String]
      email    <- c.downField("email").as[String]
      password <- c.downField("password").as[String]
    } yield User(new ObjectId(id), login, email, password)
  }

  implicit val updateUserEncoder: Encoder[UpdateUser] = (user: UpdateUser) => {
    Json.obj(
      "id"       -> user._id.toString.asJson,
      "login"    -> user.login.asJson,
      "email"    -> user.email.asJson,
      "password" -> user.password.asJson
    )
  }

  implicit val updateUserDecoder: Decoder[UpdateUser] = (c: HCursor) => {
    for {
      id       <- c.downField("id").as[String]
      login    <- c.downField("login").as[Option[String]]
      email    <- c.downField("email").as[Option[String]]
      password <- c.downField("password").as[Option[String]]
    } yield UpdateUser(new ObjectId(id), login, email, password)
  }

  implicit val roadDecoder: Decoder[UserLogin] = deriveDecoder[UserLogin]
  implicit val roadEncoder: Encoder[UserLogin] = deriveEncoder[UserLogin]

}

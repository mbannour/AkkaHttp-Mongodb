package com.dali.user.application
import com.dali.mongo.MongoDb
import com.dali.user.domain.{UpdateUser, User}
import org.bson.types.ObjectId
import org.mongodb.scala.{Completed, Observable}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.model.Updates._

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(mongo: MongoDb)(implicit val ec: ExecutionContext) extends UserDao {

  override def insertUser(user: User): Future[Completed] = mongo.collection.insertOne(user).toFuture()

  override def findOneUserById(id: ObjectId): Future[Option[User]] =
    mongo.collection.find(equal("_id", id)).headOption()

  override def updateUser(updateUser: UpdateUser): Future[Option[UpdateResult]] = {

    val updatedUser: Observable[UpdateResult] = mongo.collection
      .find(equal("_id", updateUser._id))
      .first()
      .map { user =>
        updateUser match {
          case UpdateUser(id, Some(login), Some(email), Some(password)) => User(id, login, email, password)
          case UpdateUser(id, None, Some(email), Some(password))        => User(id, user.login, email, password)
          case UpdateUser(id, Some(login), None, Some(password))        => User(id, login, user.email, password)
          case UpdateUser(id, Some(login), Some(email), None)           => User(id, login, email, user.password)
          case UpdateUser(id, None, None, Some(password))               => User(id, user.login, user.email, password)
          case UpdateUser(id, None, Some(email), None)                  => User(id, user.password, email, user.password)
          case UpdateUser(id, Some(logging), None, None)                => User(id, logging, user.email, user.password)
          case _                                                        => user
        }
      }
      .flatMap(
        user =>
          mongo.collection
            .updateOne(
              equal("_id", updateUser._id),
              combine(set("login", user.login), set("email", user.email), set("password", user.password))
          )
      )
    updatedUser.toFuture().map(_.headOption)

  }

  override def deleteUserByLogin(login: String): Future[DeleteResult] =
    mongo.collection.deleteOne(equal("login", login)).toFuture()

  override def findAllUsers(): Future[Seq[User]] = mongo.collection.find().toFuture()
}

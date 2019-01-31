package com.dali.user.application
import com.dali.mongo.Mongo
import com.dali.user.domain.{UpdateUser, User}
import org.mongodb.scala.{Completed, MongoCollection, Observable}
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.result.DeleteResult

import scala.concurrent.{ExecutionContext, Future}

class UserRepository(mongo: Mongo)(implicit val ec: ExecutionContext) extends UserDao {

  lazy val collection: MongoCollection[User] = mongo.database.getCollection("users")

  override def insertUser(user: User): Future[Completed] =
    collection.insertOne(user).toFuture()

  override def findOneUser(): Future[Option[User]] =
    collection.find().headOption()

  override def updateUser(updateUser: UpdateUser): Future[User] = {

    val updatedUser = collection
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
          case other                                                    => user
        }
      }
    val users: Observable[User] = for {
      user <- updatedUser
      replaced <- collection
        .findOneAndReplace(equal("_id", updateUser._id), user)
    } yield replaced

    updatedUser.head()
  }

  override def deleteUser(login: String): Future[DeleteResult] =
    collection.deleteOne(equal("login", login)).toFuture()
}

package com.dali.user.application

import com.dali.mongo.Mongo
import com.dali.user.domain.User
import org.mongodb.scala.{Completed, MongoCollection}

import scala.concurrent.{ExecutionContext, Future}

trait UserDao {

  def insertUser(user: User): Future[Completed]

  def findOneUser(): Future[User]
}

class UserRepository()(implicit val ec: ExecutionContext) extends UserDao with Mongo {

  val collection: MongoCollection[User] = database.getCollection("users")

  override def insertUser(user: User): Future[Completed] =
    collection.insertOne(user).toFuture()
  def findOneUser(): Future[User] = collection.find().first().toFuture()
}

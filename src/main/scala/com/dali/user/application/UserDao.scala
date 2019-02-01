package com.dali.user.application

import com.dali.user.domain.{UpdateUser, User}
import org.bson.types.ObjectId
import org.mongodb.scala.result.{DeleteResult, UpdateResult}
import org.mongodb.scala.Completed

import scala.concurrent.Future

trait UserDao {

  def findAllUsers(): Future[Seq[User]]

  def insertUser(user: User): Future[Completed]

  def findOneUserById(id: ObjectId): Future[Option[User]]

  def updateUser(updateUser: UpdateUser): Future[Option[UpdateResult]]

  def deleteUserByLogin(login: String): Future[DeleteResult]
}

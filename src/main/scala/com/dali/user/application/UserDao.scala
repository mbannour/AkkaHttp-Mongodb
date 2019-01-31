package com.dali.user.application

import com.dali.user.domain.{UpdateUser, User}
import org.mongodb.scala.result.DeleteResult
import org.mongodb.scala.Completed

import scala.concurrent.Future

trait UserDao {

  def insertUser(user: User): Future[Completed]

  def findOneUser(): Future[Option[User]]

  def updateUser(updateUser: UpdateUser): Future[User]

  def deleteUser(login: String): Future[DeleteResult]
}

package com.dali.user.application

import com.dali.user.domain.{UpdateUser, User}
import org.mongodb.scala.bson.ObjectId

import scala.concurrent.ExecutionContext

class UserService(userDao: UserDao)(implicit val ec: ExecutionContext) {

  def findAllUser() = userDao.findAllUsers()

  def insertOneUser(user: User) = userDao.insertUser(user)

  def finOneUserById(id: ObjectId) = userDao.findOneUserById(id)

  def updateUser(updateUser: UpdateUser) = userDao.updateUser(updateUser)

  def deleteUser(login: String) = userDao.deleteUserByLogin(login)

}

package com.dali.user.application

import com.dali.user.domain.{UpdateUser, User}

import scala.concurrent.ExecutionContext

class UserService(userDao: UserDao)(implicit val ec: ExecutionContext) {

  def insertOneUser(user: User) = userDao.insertUser(user)

  def finOneUser() = userDao.findOneUser()

  def updateUser(updateUser: UpdateUser) = userDao.updateUser(updateUser)

  def deleteUser(login: String) = userDao.deleteUser(login)

}

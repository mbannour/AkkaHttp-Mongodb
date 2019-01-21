package com.dali.user.application

import com.dali.user.domain.User

import scala.concurrent.ExecutionContext

class UserService(userDao: UserDao)(implicit val ec: ExecutionContext) {

  def insertOneUser(user: User) = userDao.insertUser(user)

  def finOneUser() = userDao.findOneUser()

}

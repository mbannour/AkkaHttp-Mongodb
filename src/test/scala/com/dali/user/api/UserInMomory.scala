package com.dali.user.api
import com.dali.user.application.UserDao
import com.dali.user.domain.{UpdateUser, User}
import com.mongodb.client.result.{DeleteResult, UpdateResult}
import org.bson.types.ObjectId
import org.mongodb.scala.Completed
import org.mongodb.scala.result.DeleteResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserInMemory extends UserDao {

  var users = List[User]()

  override def findAllUsers(): Future[Seq[User]]                   = Future(users)
  override def insertUser(user: User): Future[Completed]           = Future(users = users ++ List(user)).map(_ => Completed())
  override def findOneUserById(id: ObjectId): Future[Option[User]] = Future(users.find(_._id.toString == id.toString))
  override def updateUser(updateUser: UpdateUser): Future[Option[UpdateResult]] =
    Future(Some(UpdateResult.unacknowledged()))
  override def deleteUserByLogin(login: String): Future[DeleteResult] = Future(DeleteResult.unacknowledged())
}

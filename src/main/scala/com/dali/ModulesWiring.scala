package com.dali
import akka.actor.ActorSystem
import akka.dispatch.MessageDispatcher
import com.dali.mongo.{MongoDb, MongodbConfig}
import com.dali.user.application.{UserRepository, UserService}

trait ModulesWiring {

  def _system: ActorSystem

  lazy val dao: MessageDispatcher = _system.dispatchers.lookup("akka-dao-mongo")

  lazy val mongo = new MongoDb(MongodbConfig.databaseName, "users")

  lazy val userDao = new UserRepository(mongo)(dao)

  lazy val userService = new UserService(userDao)(dao)

}

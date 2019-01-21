package com.dali.mongo

import com.typesafe.config.{Config, ConfigFactory}

object MongodbConfig {

  lazy val host = rootConfig.getString("mongo.host")

  private val rootConfig: Config = ConfigFactory.load()

}

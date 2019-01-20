package com.dali.mongo

import com.typesafe.config.{Config, ConfigFactory}

object MongodbConfig {

  lazy val host = rootConfig.getString("server.host")

  private val rootConfig: Config = ConfigFactory.load()

}

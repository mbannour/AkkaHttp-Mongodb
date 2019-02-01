package com.dali

import com.typesafe.config.{Config, ConfigFactory}

trait ServerConfig {

  lazy val host                  = rootConfig.getString("server.host")
  lazy val port                  = rootConfig.getInt("server.port")
  private val rootConfig: Config = ConfigFactory.load()
}

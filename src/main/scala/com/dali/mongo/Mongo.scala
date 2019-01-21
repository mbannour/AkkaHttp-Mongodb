package com.dali.mongo

import com.dali.user.domain.User
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

trait Mongo {

  val mongoClient: MongoClient = MongoClient(MongodbConfig.host)

  val codecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)

  val database: MongoDatabase = mongoClient.getDatabase("userApp").withCodecRegistry(codecRegistry)

  val collection: MongoCollection[User]

}

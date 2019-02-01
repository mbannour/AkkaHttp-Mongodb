package com.dali.mongo

import com.dali.user.domain.User
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}

class MongoDb(databaseName: String, collectionName: String) {

  val mongoClient: MongoClient = MongoClient(MongodbConfig.url)

  lazy val codecRegistry = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)

  lazy val database: MongoDatabase = mongoClient.getDatabase(databaseName).withCodecRegistry(codecRegistry)

  lazy val collection: MongoCollection[User] = database.getCollection(collectionName)

}

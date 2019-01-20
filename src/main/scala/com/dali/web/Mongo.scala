package com.dali.web

import java.util.concurrent.TimeUnit

import com.dali.mongo.MongodbConfig
import org.mongodb.scala._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object Mongo {

  val mongoClient: MongoClient = MongoClient(MongodbConfig.host)

  val database: MongoDatabase = mongoClient.getDatabase("mongotuto")

  val collection: MongoCollection[Document] = database.getCollection("persons")

  val doc: Document = Document(
    "_id"   -> 3,
    "name"  -> "MongoDB",
    "type"  -> "database",
    "count" -> 1,
    "info"  -> Document("x" -> 203, "y" -> 102)
  )

  val observable = collection.insertOne(doc).toFuture()

  Await.result(observable, Duration(10, TimeUnit.SECONDS))

  mongoClient.close()

}

package com.dali.user.application
import com.dali.mongo.MongoDb
import com.dali.user.domain.{UpdateUser, User}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.bson.ObjectId
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Await

class UserRepositorySpec extends WordSpec with ScalaFutures with BeforeAndAfterAll with Matchers {

  override protected def beforeAll(): Unit =
    Await.result(db.drop().toFuture(), 5.seconds)

  private val client                                = MongoClient(s"mongodb://localhost:27017")
  private val codecRegistry                         = fromRegistries(fromProviders(classOf[User]), DEFAULT_CODEC_REGISTRY)
  private val db                                    = client.getDatabase("scala-mongo").withCodecRegistry(codecRegistry)
  private val userCollection: MongoCollection[User] = db.getCollection("users")

  lazy val mongo = new MongoDb("scala-mongo", "users ") {
    override val mongoClient: MongoClient               = client
    override lazy val database: MongoDatabase           = db
    override lazy val collection: MongoCollection[User] = userCollection
  }

  lazy val userRepository = new UserRepository(mongo)

  "Mongo database Spec" must {

    "insert user into MongoDB" in {

      val userToInsert = User(new ObjectId("507f1f77bcf86cd799439011"), "login", "test@test", "secret")

      userRepository.insertUser(userToInsert).futureValue

      val expectedUser = userCollection.find().first().head().futureValue

      expectedUser should ===(userToInsert)

    }

    "Update user into MongoDB" in {

      val userToUpdate = UpdateUser(new ObjectId("507f1f77bcf86cd799439011"), Some("login1"), None, Some("secret1"))

      userRepository.updateUser(userToUpdate).futureValue

      val expectedUser = userCollection.find().head().futureValue

      expectedUser should ===(User(new ObjectId("507f1f77bcf86cd799439011"), "login1", "test@test", "secret1"))

    }

    "Find a specific user by id in MongoDB" in {

      val user = userRepository.findOneUserById(new ObjectId("507f1f77bcf86cd799439011")).futureValue

      user should ===(Some(User(new ObjectId("507f1f77bcf86cd799439011"), "login1", "test@test", "secret1")))

    }

    "delete user user from MongoDBd " in {

      userRepository.deleteUserByLogin("login1").futureValue

      val expectedUser = userCollection.find().first().headOption().futureValue

      expectedUser should ===(None)
    }

  }

}

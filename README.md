# AkkaHttp-Mongodb

This is a simple project, in which I used akka HHTP for creating REST API with a Mongodb as database for storing and retrieving data.
In this Project I used the following technologies: 

- **MongoDB Scala Driver** which provides asynchronous event-based observable sequences for MongoDB.
- **circe** 
- **Akka HTTP**


Before starting this project you should have Mongodb installed locally or you can start a mongodb docker container and you should have already installed scala.

In my case I user docker instance for Mongodb, to start that it is very simple just run this command:

```docker run --name mongodb -p 27017:27017 -d mongo```

After that to start the whole project just run :

```sbt run ```



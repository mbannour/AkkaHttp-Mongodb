# Akka HTTP-Mongodb Application

This is a simple project, in which I used akka HTTP for creating a REST API with Mongodb as database for storing and retrieving data.
In this Project I used the following technologies: 

- **MongoDB Scala Driver** 
- **circe** 
- **Akka HTTP**

# Requirements

- JDK 8 (http://www.oracle.com/technetwork/java/javase/downloads/index.html);
- sbt (http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html);
- Docker (For Lunix: https://docs.docker.com/install/linux/docker-ce/ubuntu/)

# Development guide

Before starting this project you should have Mongodb installed locally or you can start a mongodb docker container in your local machine.

In my case I user docker instance for Mongodb, to start that it is very simple just run this command:

```docker run --name mongodb -p 27017:27017 -d mongo```

After that to start the whole project just run :

```sbt run ```

# Docker packager

To package application as docker image, use `sbt docker:publishLocal`. It will generate and push application image into your local docker store. For more information about publishing to external store, please, read [plugin documentation](https://www.scala-sbt.org/sbt-native-packager/formats/docker.html).

After publishing the image locally, the easiest way to start the application is to run docker-compose:

```docker-compose up``` 

Now you have the application running in docker , for testing some functionalities just run theses commands :

```curl --header "Content-Type: application/json" --request POST  --data ' {"id": "58dd0a68218de27733475fa4","login": "user1", "email": "email@email.com","password": "secret"}' http://localhost:9000/api/users/create```

``` curl -i -H "Accept: application/json" "http://localhost:9000/api/users" ```









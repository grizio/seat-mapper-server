package sms

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import scalikejdbc.{ConnectionPool, ConnectionPoolSettings}
import sms.configuration.Configuration
import sms.controller.Controller
import sms.persistence.Persistence
import sms.router.{Cors, Router}
import sms.service.Service

import scala.concurrent.ExecutionContext

class Sms() {
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher

  val configuration: Configuration = Configuration.load()

  Class.forName("org.postgresql.Driver")
  ConnectionPool.singleton(
    s"jdbc:postgresql://${configuration.database.host}:${configuration.database.port}/${configuration.database.name}",
    configuration.database.username,
    configuration.database.password,
    ConnectionPoolSettings(
      initialSize = configuration.database.initialSize,
      maxSize = configuration.database.maxSize
    )
  )

  val persistence = new Persistence()
  val service = new Service(persistence)
  val controller = new Controller(service)
  val cors = new Cors(configuration)
  val router = new Router(cors, controller)
}

package sms

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import sms.configuration.Environment
import sms.evolutions.DatabaseEvolution

import scala.concurrent.ExecutionContext
import scala.io.StdIn

object Boot extends App {
  val logger = Logger(Boot.getClass)

  val sms = new Sms()
  implicit val actorSystem: ActorSystem = sms.actorSystem
  implicit val actorMaterializer: ActorMaterializer = sms.actorMaterializer
  implicit val executionContext: ExecutionContext = sms.executionContext

  new DatabaseEvolution().setup()

  val bindingFuture = Http().bindAndHandle(sms.router.route, sms.configuration.server.host, sms.configuration.server.port)

  logger.info(s"Server online at ${sms.configuration.server.protocol}://${sms.configuration.server.host}:${sms.configuration.server.port}")

  if (sms.configuration.environment == Environment.dev) {
    logger.info("Press RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => actorSystem.terminate())
  }
}
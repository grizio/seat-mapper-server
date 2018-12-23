package sms.router

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import sms.configuration.Configuration
import sms.json.ConfigurationJson

class ClientRouter(
  configuration: Configuration
) {
  private val classLoader: ClassLoader = classOf[ClientRouter].getClassLoader

  val route: Route = pathEndOrSingleSlash {
    complete {
      val content = scala.io.Source.fromInputStream(classLoader.getResource("index.html").openStream()).mkString
      val clientConfiguration = ConfigurationJson.clientConfigurationFormat.write(configuration.client)
      val contentWithConfiguration = content.replace(
        """<script id="configuration-placeholder"></script>""",
        s"""<script>window.configuration = ${clientConfiguration}</script>"""
      )
      HttpResponse(
        status = StatusCodes.OK,
        entity = HttpEntity(ContentTypes.`text/html(UTF-8)`, contentWithConfiguration)
      )
    }
  }
}
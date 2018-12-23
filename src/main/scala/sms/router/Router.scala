package sms.router

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import sms.controller.Controller
import sms.json.CommonJson._
import sms.json.SeatMapJson._
import sms.model.{SeatMap, SeatMapCreation, SeatMapUpdate}
import sms.router.PathMatchers._
import sms.router.Unmarshallers._

class Router(
  clientRouter: ClientRouter,
  cors: Cors,
  controller: Controller
) {
  val route: Route = cors.applyCors {
    {
      (get & path("maps")) {
        complete(controller.list())
      }
    } ~ {
      (get & path("maps" / SeatMapId)) { id =>
        complete(controller.find(id))
      }
    } ~ {
      (post & path("maps") & entity(as[SeatMapCreation])) { seatMapCreation =>
        complete(controller.create(seatMapCreation))
      }
    } ~ {
      (put & path("maps" / SeatMapId) & parameter("key".as[SeatMap.UpdateKey]) & entity(as[SeatMapUpdate])) { (id, updateKey, seatMapUpdate) =>
        complete(controller.update(id, updateKey, seatMapUpdate))
      }
    } ~ {
      (delete & path("maps" / SeatMapId) & parameter("key".as[SeatMap.UpdateKey])) { (id, updateKey) =>
        complete(controller.delete(id, updateKey))
      }
    } ~ {
      path("swagger.json") {
        getFromResource("swagger.json")
      }
    } ~ {
      clientRouter.route
    }
  }
}

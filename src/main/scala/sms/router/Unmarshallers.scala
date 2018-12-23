package sms.router

import java.util.UUID

import akka.http.scaladsl.unmarshalling.Unmarshaller
import akka.stream.Materializer
import sms.model.SeatMap

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object Unmarshallers {
  implicit val seatMapUpdateKeyUnmarshaller: Unmarshaller[String, SeatMap.UpdateKey] = {
    valueClassUnmarshaller(SeatMap.UpdateKey.apply)
  }

  implicit val seatMapIdUnmarshaller: Unmarshaller[String, SeatMap.Id] = {
    valueClassUnmarshaller(value => SeatMap.Id(UUID.fromString(value)))
  }

  private def valueClassUnmarshaller[Input, Output](op: Input => Output): Unmarshaller[Input, Output] = {
    new Unmarshaller[Input, Output] {
      override def apply(value: Input)(implicit ec: ExecutionContext, materializer: Materializer): Future[Output] = {
        Future.fromTry {
          Try {
            op(value)
          }
        }
      }
    }
  }
}

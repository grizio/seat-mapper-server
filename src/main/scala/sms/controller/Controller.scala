package sms.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpResponse, StatusCodes}
import sms.json.CommonJson._
import sms.json.SeatMapJson._
import sms.model._
import sms.service.{Service, ServiceResult}
import spray.json.JsonWriter

import scala.concurrent.{ExecutionContext, Future}

class Controller(
  service: Service
)(
  implicit executionContext: ExecutionContext
) {
  def list(): Future[HttpResponse] = {
    service.list().map(serviceResultToHttpResult)
  }

  def find(id: SeatMap.Id): Future[HttpResponse] = {
    service.find(id).map(serviceResultToHttpResult)
  }

  def create(seatMapCreation: SeatMapCreation): Future[HttpResponse] = {
    service.create(seatMapCreation).map(serviceResultToHttpResult)
  }

  def update(id: SeatMap.Id, updateKey: SeatMap.UpdateKey, seatMapUpdate: SeatMapUpdate): Future[HttpResponse] = {
    service.update(id, updateKey, seatMapUpdate).map(serviceResultToHttpResult)
  }

  def delete(id: SeatMap.Id, updateKey: SeatMap.UpdateKey): Future[HttpResponse] = {
    service.delete(id, updateKey).map(serviceResultToHttpResult)
  }

  private def serviceResultToHttpResult(serviceResult: ServiceResult): HttpResponse = serviceResult match {
    case ServiceResult.FoundMany(seatMaps: Seq[SeatMap]) =>
      HttpResponse(
        status = StatusCodes.OK,
        entity = jsonEntity(seatMaps.map(PublicSeatMap.from))
      )

    case ServiceResult.FoundOne(seatMap: SeatMap) =>
      HttpResponse(
        status = StatusCodes.OK,
        entity = jsonEntity(PublicSeatMap.from(seatMap))
      )

    case ServiceResult.NotFound =>
      HttpResponse(
        status = StatusCodes.NotFound
      )

    case ServiceResult.InvalidUpdateKey =>
      HttpResponse(
        status = StatusCodes.Forbidden
      )

    case ServiceResult.Created(seatMap) =>
      HttpResponse(
        status = StatusCodes.Created,
        entity = jsonEntity(PublicSeatMap.from(seatMap))
      )

    case ServiceResult.Updated(seatMap) =>
      HttpResponse(
        status = StatusCodes.OK,
        entity = jsonEntity(PublicSeatMap.from(seatMap))
      )

    case ServiceResult.Deleted =>
      HttpResponse(
        status = StatusCodes.NoContent
      )
  }

  private def jsonEntity[A: JsonWriter](value: A): HttpEntity.Strict = {
    HttpEntity(ContentTypes.`application/json`, implicitly[JsonWriter[A]].write(value).compactPrint)
  }
}

package sms.service

import sms.model.{SeatMap, SeatMapCreation, SeatMapUpdate}
import sms.persistence.Persistence

import scala.concurrent.{ExecutionContext, Future}

class Service(
  persistence: Persistence
)(
  implicit executionContext: ExecutionContext
) {
  def list(): Future[ServiceResult] = {
    persistence.list().map(ServiceResult.FoundMany)
  }

  def find(id: SeatMap.Id): Future[ServiceResult] = {
    persistence.find(id) map {
      case Some(seatMap) => ServiceResult.FoundOne(seatMap)
      case None => ServiceResult.NotFound
    }
  }

  def create(seatMapCreation: SeatMapCreation): Future[ServiceResult] = {
    val seatMap = SeatMap(
      id = SeatMap.Id.random(),
      name = seatMapCreation.name,
      address = seatMapCreation.address,
      coordinates = seatMapCreation.coordinates,
      description = seatMapCreation.description,
      map = seatMapCreation.map,
      updateKey = seatMapCreation.updateKey.hash()
    )
    persistence.create(seatMap)
      .map(_ => ServiceResult.Created(seatMap))
  }

  def update(id: SeatMap.Id, updateKey: SeatMap.UpdateKey, seatMapUpdate: SeatMapUpdate): Future[ServiceResult] = {
    persistence.find(id) flatMap {
      case Some(seatMap) =>
        if (seatMap.updateKey.check(updateKey)) {
          val updatedSeatMap = seatMap.copy(
            name = seatMapUpdate.name,
            address = seatMapUpdate.address,
            coordinates = seatMapUpdate.coordinates,
            description = seatMapUpdate.description,
            map = seatMapUpdate.map
          )
          persistence.update(updatedSeatMap)
            .map(_ => ServiceResult.Updated(updatedSeatMap))
        } else {
          Future.successful(ServiceResult.InvalidUpdateKey)
        }
      case None =>
        Future.successful(ServiceResult.NotFound)
    }
  }

  def delete(id: SeatMap.Id, updateKey: SeatMap.UpdateKey): Future[ServiceResult] = {
    persistence.find(id) flatMap {
      case Some(seatMap) =>
        if (seatMap.updateKey.check(updateKey)) {
          persistence.delete(id)
              .map(_ => ServiceResult.Deleted)
        } else {
          Future.successful(ServiceResult.InvalidUpdateKey)
        }
      case None =>
        Future.successful(ServiceResult.NotFound)
    }
  }
}

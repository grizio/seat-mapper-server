package sms.persistence

import scalikejdbc._
import sms.model.SeatMap

import scala.concurrent.{ExecutionContext, Future}

class Persistence(
  implicit executionContext: ExecutionContext
) {
  def list(): Future[Seq[SeatMap]] = Database.query { implicit session =>
    sql"""
      SELECT * FROM seat_maps
    """
      .map(SeatMapWrappers.toSeatMap)
      .list()
      .apply()
  }

  def find(id: SeatMap.Id): Future[Option[SeatMap]] = Database.query { implicit session =>
    sql"""
      SELECT * FROM seat_maps
      WHERE id = ${id.value}
    """
      .map(SeatMapWrappers.toSeatMap)
      .headOption()
      .apply()
  }

  def create(seatMap: SeatMap): Future[Unit] = Database.command { implicit session =>
    sql"""
      INSERT INTO seat_maps(id, name, address, longitude, latitude, description, map, updateKey)
      VALUES (
        ${seatMap.id.value},
        ${seatMap.name.value},
        ${seatMap.address.value},
        ${seatMap.coordinates.longitude.value},
        ${seatMap.coordinates.latitude.value},
        ${seatMap.description.value},
        ${seatMap.map.value.toString}::jsonb,
        ${seatMap.updateKey.value}
      )
    """
      .execute()
      .apply()
  }

  def update(seatMap: SeatMap): Future[Unit] = Database.command { implicit session =>
    sql"""
      UPDATE seat_maps
      SET name = ${seatMap.name.value},
          address = ${seatMap.address.value},
          longitude = ${seatMap.coordinates.longitude.value},
          latitude = ${seatMap.coordinates.latitude.value},
          description = ${seatMap.description.value},
          map = ${seatMap.map.value.toString}::jsonb,
          updateKey = ${seatMap.updateKey.value}
      WHERE id = ${seatMap.id.value}
    """
      .execute()
      .apply()
  }

  def delete(id: SeatMap.Id): Future[Unit] = Database.command { implicit session =>
    sql"""
      DELETE FROM seat_maps
      WHERE id = ${id.value}
    """
      .execute()
      .apply()
  }
}

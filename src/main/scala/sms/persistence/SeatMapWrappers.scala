package sms.persistence

import scalikejdbc.{TypeBinder, WrappedResultSet}
import sms.model.{Coordinates, SeatMap}
import sms.persistence.DatabaseWrappers._

object SeatMapWrappers {
  def toSeatMap(rs: WrappedResultSet): SeatMap = SeatMap(
    id = rs.get[SeatMap.Id]("id"),
    name = rs.get[SeatMap.Name]("name"),
    address = rs.get[SeatMap.Address]("address"),
    coordinates = Coordinates(
      longitude = rs.get[Coordinates.Longitude]("longitude"),
      latitude = rs.get[Coordinates.Latitude]("latitude")
    ),
    description = rs.get[SeatMap.Description]("description"),
    map = rs.get[SeatMap.Map]("map"),
    updateKey = rs.get[SeatMap.HashedUpdateKey]("updateKey")
  )

  implicit val longitudeWrapper: TypeBinder[Coordinates.Longitude] = valueClassWrapper(Coordinates.Longitude.apply)
  implicit val latitudeWrapper: TypeBinder[Coordinates.Latitude] = valueClassWrapper(Coordinates.Latitude.apply)

  implicit val idWrapper: TypeBinder[SeatMap.Id] = valueClassWrapper(SeatMap.Id.apply)
  implicit val nameWrapper: TypeBinder[SeatMap.Name] = valueClassWrapper(SeatMap.Name.apply)
  implicit val addressWrapper: TypeBinder[SeatMap.Address] = valueClassWrapper(SeatMap.Address.apply)
  implicit val descriptionWrapper: TypeBinder[SeatMap.Description] = valueClassWrapper(SeatMap.Description.apply)
  implicit val mapWrapper: TypeBinder[SeatMap.Map] = valueClassWrapper(SeatMap.Map.apply)
  implicit val hashedUpdateKeyWrapper: TypeBinder[SeatMap.HashedUpdateKey] = valueClassWrapper(SeatMap.HashedUpdateKey.apply)
}

package sms.model

import java.util.UUID

import sms.utils.StringUtils
import spray.json.JsValue

case class SeatMap(
  id: SeatMap.Id,
  name: SeatMap.Name,
  address: SeatMap.Address,
  coordinates: Coordinates,
  description: SeatMap.Description,
  map: SeatMap.Map,
  updateKey: SeatMap.HashedUpdateKey
)

object SeatMap {
  case class Id(value: UUID) extends AnyVal
  object Id {
    def random(): Id = Id(UUID.randomUUID())
  }
  case class Name(value: String) extends AnyVal
  case class Address(value: String) extends AnyVal
  case class Description(value: String) extends AnyVal
  case class Map(value: JsValue) extends AnyVal
  case class UpdateKey(value: String) extends AnyVal {
    def hash(): HashedUpdateKey = HashedUpdateKey(StringUtils.hash(value))
  }
  case class HashedUpdateKey(value: String) extends AnyVal {
    def check(updateKey: UpdateKey): Boolean = StringUtils.checkHash(value = updateKey.value, hash = value)
  }
}
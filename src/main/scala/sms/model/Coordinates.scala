package sms.model

case class Coordinates(
  longitude: Coordinates.Longitude,
  latitude: Coordinates.Latitude
)

object Coordinates {
  case class Longitude(value: BigDecimal) extends AnyVal

  case class Latitude(value: BigDecimal) extends AnyVal
}

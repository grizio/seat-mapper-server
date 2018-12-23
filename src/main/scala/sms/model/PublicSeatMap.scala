package sms.model

case class PublicSeatMap(
  id: SeatMap.Id,
  name: SeatMap.Name,
  address: SeatMap.Address,
  coordinates: Coordinates,
  description: SeatMap.Description,
  map: SeatMap.Map
)

object PublicSeatMap {
  def from(seatMap: SeatMap): PublicSeatMap = {
    PublicSeatMap(
      id = seatMap.id,
      name = seatMap.name,
      address = seatMap.address,
      coordinates = seatMap.coordinates,
      description = seatMap.description,
      map = seatMap.map
    )
  }
}

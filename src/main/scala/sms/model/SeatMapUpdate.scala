package sms.model

case class SeatMapUpdate(
  name: SeatMap.Name,
  address: SeatMap.Address,
  coordinates: Coordinates,
  description: SeatMap.Description,
  map: SeatMap.Map
)

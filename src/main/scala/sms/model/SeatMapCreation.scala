package sms.model

case class SeatMapCreation(
  name: SeatMap.Name,
  address: SeatMap.Address,
  coordinates: Coordinates,
  description: SeatMap.Description,
  map: SeatMap.Map,
  updateKey: SeatMap.UpdateKey
)

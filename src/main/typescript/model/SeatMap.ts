interface SeatMap {
  id: string
  name: string
  address: string
  coordinates: Coords
  description: string
  map: object
}

interface Coords {
  latitude: number
  longitude: number
}
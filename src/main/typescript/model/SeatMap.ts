export interface SeatMap {
  id: string
  name: string
  address: string
  coordinates: Coords
  description: string
  map: object
}

export interface Coords {
  latitude: number
  longitude: number
}

export function coordsEqual(coords1: Coords, coords2: Coords): boolean {
  return coords1.longitude === coords2.longitude && coords1.latitude === coords2.latitude
}
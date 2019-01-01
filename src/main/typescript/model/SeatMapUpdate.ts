import {Coords} from "./SeatMap"

export interface SeatMapUpdate {
  name: string
  address: string
  coordinates: Coords
  description: string
  map: object
}
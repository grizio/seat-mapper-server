import {Coords} from "./SeatMap"

export interface SeatMapCreation {
  name: string
  address: string
  coordinates: Coords
  description: string
  map?: object
  updateKey: string
}
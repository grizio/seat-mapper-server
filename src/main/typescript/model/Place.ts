import {Coords} from "./SeatMap"

export interface Place {
  id: string
  label: string
  class: string
  coordinates: Coords
}

export interface OSMPlace {
  place_id: string
  licence: string
  osm_type: string
  osm_id: string
  boundingbox: [string, string, string, string]
  lat: string
  lon: string
  display_name: string
  class: string
  type: string
  importance: number
  icon: string
}
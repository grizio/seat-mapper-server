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
  icon: string,
  address: {
    community_centre?: string
    house_number?: string
    footway?: string
    road?: string
    neighbourhood: string
    suburb: string
    village?: string
    city?: string
    county: string
    state: string
    country: string
    postcode: string
    country_code: string
  }
}

export function normalizedAddress(osmPlace: OSMPlace): string {
  const address = osmPlace.address

  const addressPart = address.community_centre
    ? [ address.community_centre ]
    : [
      address.house_number || '',
      address.footway || address.road || ''
    ]
  const cityPart = [
    address.postcode,
    address.city || address.village || ''
  ]
  return [
    ...addressPart,
    ...cityPart
  ].join(' ')
}
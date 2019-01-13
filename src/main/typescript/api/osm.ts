import {normalizedAddress, OSMPlace, Place} from "../model/Place"

export function searchPlaces(query: string): Promise<Array<Place>> {
  return fetch(`https://nominatim.openstreetmap.org/search/?q=${query}&format=json&limit=50&addressdetails=1`)
    .then(response => response.json())
    .then((places: Array<OSMPlace>) =>
      places.map<Place>(place => ({
        id: place.place_id,
        label: normalizedAddress(place),
        class: place.class,
        coordinates: {
          longitude: parseFloat(place.lon),
          latitude: parseFloat(place.lat)
        }
      }))
    )
}
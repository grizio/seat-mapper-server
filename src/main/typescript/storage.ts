import {StoredSeatMap} from "./model/StoredSeatMap"

const storageKey = "seatMaps"

export function getStoredSeatMaps(): Array<StoredSeatMap> {
  const storedJson = localStorage.getItem(storageKey)
  if (storedJson !== null) {
    return JSON.parse(storedJson)
  } else {
    return []
  }
}

export function addSeatMap(seatMap: StoredSeatMap): void {
  const storedSeatMaps = getStoredSeatMaps()
  const newSeatMaps = [...storedSeatMaps, seatMap]
  localStorage.setItem(storageKey, JSON.stringify(newSeatMaps))
}
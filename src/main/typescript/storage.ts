import {StoredSeatMap} from "./model/StoredSeatMap"

const storageKey = "seatMaps"

export function getAllStoredSeatMaps(): Array<StoredSeatMap> {
  const storedJson = localStorage.getItem(storageKey)
  if (storedJson !== null) {
    return JSON.parse(storedJson)
  } else {
    return []
  }
}

export function getStoredSeatMap(id: string): StoredSeatMap | undefined {
  return getAllStoredSeatMaps().find(_ => _.id === id)
}

export function containsSeatMap(id: string): boolean {
  return getAllStoredSeatMaps().some(_ => _.id === id)
}

export function persistSeatMap(seatMap: StoredSeatMap): void {
  const storedSeatMaps = getAllStoredSeatMaps()
  const newSeatMaps = [
    ...storedSeatMaps.filter(_ => _.id !== seatMap.id),
    seatMap
  ]
  localStorage.setItem(storageKey, JSON.stringify(newSeatMaps))
}
import configuration from "../configuration"
import {SeatMap} from "../model/SeatMap"
import {SeatMapCreation} from "../model/SeatMapCreation"
import {SeatMapUpdate} from "../model/SeatMapUpdate"

export function list(): Promise<Array<SeatMap>> {
  return fetch(`${configuration.server}/maps`)
    .then(response => response.json())
}

export function create(seatMapCreation: SeatMapCreation): Promise<SeatMap> {
  return fetch(`${configuration.server}/maps`, {
    method: "post",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(seatMapCreation)
  }).then(_ => _.json())
}

export function update(id: string, updateKey: string, seatMapUpdate: SeatMapUpdate): Promise<SeatMap> {
  return fetch(`${configuration.server}/maps/${id}?key=${updateKey}`, {
    method: "put",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(seatMapUpdate)
  }).then(_ => _.json())
}
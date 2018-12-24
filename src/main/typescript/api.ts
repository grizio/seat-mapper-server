import configuration from "./configuration"

export function list(): Promise<Array<SeatMap>> {
  return fetch(`${configuration.server}/maps`)
    .then(response => response.json())
}

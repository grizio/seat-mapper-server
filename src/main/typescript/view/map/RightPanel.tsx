import {h} from "preact"
import {SeatMap} from "../../model/SeatMap"
import * as storage from "../../storage"

interface Props {
  selectedSeatMap?: SeatMap
  deselectSeatMap: () => void
  deleteSelectedSeatMap: () => void
}

export function RightPanel(props: Props) {
  if (props.selectedSeatMap !== undefined) {
    return seatMapInformation(props.selectedSeatMap, props)
  } else {
    return empty()
  }
}

function seatMapInformation(seatMap: SeatMap, {deselectSeatMap, deleteSelectedSeatMap}: Props) {
  return <section class="right-panel">
    <button onClick={deselectSeatMap}>Close</button>
    <h3>{seatMap.name}</h3>

    {
      storage.containsSeatMap(seatMap.id)
        ? [
          <a href={`${seatMap.id}/update`}>Update</a>,
          <button onClick={deleteSelectedSeatMap}>Delete</button>
        ]
        : []
    }

    <p>{seatMap.address}</p>

    <p>{seatMap.description}</p>
    {
      // @ts-ignore
      <seat-mapper-preview structure={JSON.stringify(seatMap.map)}/>
    }
  </section>
}

function empty() {
  return (
    <section class="right-panel">
      <p>Select a point for more information</p>

      <a href="/new">New seat map</a>
    </section>
  )
}
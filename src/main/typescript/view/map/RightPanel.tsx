import {h} from "preact"
import {SeatMap} from "../../model/SeatMap"

interface Props {
  selectedSeatMap?: SeatMap
  deselectSeatMap: () => void
}

export function RightPanel(props: Props) {
  if (props.selectedSeatMap !== undefined) {
    return seatMapInformation(props.selectedSeatMap, props.deselectSeatMap)
  } else {
    return empty()
  }
}

function seatMapInformation(seatMap: SeatMap, deselectSeatMap: () => void) {
  return <section class="right-panel">
    <button onClick={deselectSeatMap}>Close</button>
    <h3>{seatMap.name}</h3>

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
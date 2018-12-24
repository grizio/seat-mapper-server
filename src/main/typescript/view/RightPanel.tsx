import {h} from "preact"

interface Props {
  selectedSeatMap?: SeatMap
}

export function RightPanel(props: Props) {
  if (props.selectedSeatMap !== undefined) {
    return seatMapInformation(props.selectedSeatMap)
  } else {
    return empty()
  }
}

function seatMapInformation(seatMap: SeatMap) {
  return <section class="right-panel">
    <h3>{seatMap.name}</h3>

    <p>{seatMap.address}</p>

    <p>{seatMap.description}</p>
    {
      // @ts-ignore
      <seat-mapper initial-structure={JSON.stringify(seatMap.map)}/>
    }
  </section>
}

function empty() {
  return (
    <section>
      <p>Select a point for more information</p>
    </section>
  )
}
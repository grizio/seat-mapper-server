import {Component, h} from "preact"
import {Coords, SeatMap} from "../../model/SeatMap"
import Leaflet from "../components/Leaflet"

interface Props {
  id: string
  coordinates: Coords
  zoom: number
  seatMaps: Array<SeatMap>
  onSeatMapSelected: (seatMap: SeatMap) => void
}

export default class LeafletView extends Component<Props, {}> {
  render(props: Props) {
    return (
      <Leaflet
        id={props.id}
        coordinates={props.coordinates}
        zoom={props.zoom}
        markers={
          props.seatMaps.map(seatMap => ({
            id: seatMap.id,
            label: seatMap.name,
            coordinates: seatMap.coordinates,
            onClick: this.onClickMarker
          }))
        }/>
    )
  }

  onClickMarker = (id: string) => {
    const seatMap = this.props.seatMaps.find(_ => _.id === id)
    if (seatMap !== undefined) {
      this.props.onSeatMapSelected(seatMap)
    }
  }
}
import {Component, h} from "preact"
import L from "leaflet"
import configuration from "../configuration"

interface Props {
  id: string
  coordinates: Coords
  zoom: number
  seatMaps: Array<SeatMap>
  onSeatMapSelected: (seatMap: SeatMap) => void
}

interface State {
  map: L.Map
  markers: Array<L.Marker>
}

export default class LeafletView extends Component<Props, State> {
  constructor(props: Props) {
    super(props)
  }

  componentDidMount(): void {
    this.setState(
      {
        map: L.map(this.props.id),
        markers: []
      },
      () => {
        this.state.map.setView([this.props.coordinates.latitude, this.props.coordinates.longitude], this.props.zoom)

        L.tileLayer(configuration.leaflet.tileLayer, {
          attribution: configuration.leaflet.attribution,
          maxZoom: 18,
          id: configuration.leaflet.id,
          accessToken: configuration.leaflet.accessToken
        } as L.TileLayerOptions)
          .addTo(this.state.map)
        this.replaceMarkers(this.props.seatMaps)
      }
    )
  }

  componentWillReceiveProps(nextProps: Props): void {
    if (nextProps.seatMaps !== this.props.seatMaps) {
      this.replaceMarkers(nextProps.seatMaps)
    }
  }

  replaceMarkers = (seatMaps: Array<SeatMap>) => {
    this.state.markers.forEach(marker => marker.removeFrom(this.state.map))
    const markers = seatMaps.map(seatMap =>
      L.marker([seatMap.coordinates.latitude, seatMap.coordinates.longitude])
        .bindPopup(`<b>${seatMap.name}</b>`)
        .on("click", () => this.props.onSeatMapSelected(seatMap))
  )
    markers.forEach(marker => marker.addTo(this.state.map))
    this.setState({markers})
  }

  render(props: Props) {
    return <section id={props.id} class="leaflet-view"/>
  }
}
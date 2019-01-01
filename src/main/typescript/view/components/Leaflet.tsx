import {Component, h} from "preact"
import {Coords} from "../../model/SeatMap"
import L from "leaflet"
import configuration from "../../configuration"

interface Props {
  id: string
  coordinates: Coords
  zoom: number
  markers: Array<Marker>
}

interface Marker {
  id: string
  label: string
  coordinates: Coords
  selected?: boolean
  onClick: (id: string) => void
}

interface State {
  map: L.Map
  markers: Map<string, L.Marker>
}

export default class Leaflet extends Component<Props, State> {
  constructor(props: Props) {
    super(props)
  }

  componentWillUnmount(): void {
    this.state.map.remove()
  }

  componentDidMount(): void {
    this.setState(
      {
        map: L.map(this.props.id),
        markers: new Map()
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

        this.replaceMarkers(this.props.markers)
      }
    )
  }

  componentWillReceiveProps(nextProps: Props): void {
    this.replaceMarkers(nextProps.markers)
  }

  replaceMarkers = (markers: Array<Marker>) => {
    const newMarkers = new Map<string, L.Marker>()
    Array.from(this.state.markers.entries()).forEach(([id, lmarker]) => {
      const marker = markers.find(_ => _.id === id)
      if (marker !== undefined) {
        this.updateMarker(lmarker, marker)
        newMarkers.set(id, lmarker)
      } else {
        lmarker.removeFrom(this.state.map)
      }
    })
    markers.forEach(marker => {
      if (!newMarkers.has(marker.id)) {
        const lmarker = this.createMarker(marker)
        newMarkers.set(marker.id, lmarker)
      }
    })
    this.setState({markers: newMarkers})
  }

  createMarker = (marker: Marker) => {
    return L.marker(
      [marker.coordinates.latitude, marker.coordinates.longitude],
      {opacity: marker.selected === false ? 0.5 : 1}
    )
      .bindPopup(`<b>${marker.label}</b>`)
      .on("click", () => marker.onClick(marker.id))
      .addTo(this.state.map)
  }

  updateMarker = (lmarker: L.Marker, marker: Marker) => {
    lmarker
      .setLatLng([marker.coordinates.latitude, marker.coordinates.longitude])
      .setOpacity(marker.selected === false ? 0.5 : 1)
      .unbindPopup()
      .bindPopup(`<b>${marker.label}</b>`)
      .off()
      .on("click", () => marker.onClick(marker.id))
  }

  render(props: Props) {
    return <section id={props.id} class="leaflet-view"/>
  }
}
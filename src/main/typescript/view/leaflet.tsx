import {Component, h} from "preact"
import L from "leaflet"
import configuration from "../configuration"

interface Props {
  id: string
  coordinates: Coords
  zoom: number
}

interface State {
  map: L.Map
}

export default class LeafletView extends Component<Props, State> {
  componentDidMount(): void {
    const map = L.map(this.props.id)
    map.setView([this.props.coordinates.latitude, this.props.coordinates.longitude], this.props.zoom)

    L.tileLayer(configuration.leaflet.tileLayer, {
      attribution: configuration.leaflet.attribution,
      maxZoom: 18,
      id: configuration.leaflet.id,
      accessToken: configuration.leaflet.accessToken
    } as L.TileLayerOptions)
      .addTo(map)

    this.setState({map})
  }

  render(props: Props) {
    return <div id={props.id} class="leafletView"/>
  }
}
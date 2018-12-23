import {Component, h} from "preact"
import LeafletView from "./leaflet"

export default class App extends Component<{}, {}> {
  render() {
    return <LeafletView
      id="test"
      coordinates={{latitude: 0, longitude: 0}}
      zoom={2}
    />
  }
}
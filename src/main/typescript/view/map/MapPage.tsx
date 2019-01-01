import {Component, h} from "preact"
import LeafletView from "./LeafletView"
import {list} from "../../api/server"
import {RightPanel} from "./RightPanel"
import {SeatMap} from "../../model/SeatMap"

interface State {
  seatMaps: Array<SeatMap>
  selectedSeatMap?: SeatMap
}

export default class MapPage extends Component<{}, State> {
  constructor(props: {}) {
    super(props)
    this.setState({
      seatMaps: []
    })
  }

  componentDidMount(): void {
    list().then(seatMaps => this.setState({seatMaps}))
  }

  render(_: {}, state: State) {
    return <main>
      <LeafletView
        id="test"
        coordinates={{latitude: 0, longitude: 0}}
        zoom={2}
        seatMaps={state.seatMaps}
        onSeatMapSelected={this.selectSeatMap}
      />
      <RightPanel
        selectedSeatMap={state.selectedSeatMap}
        deselectSeatMap={this.deselectSeatMap}
      />
    </main>
  }

  selectSeatMap = (seatMap: SeatMap) => {
    this.setState({selectedSeatMap: seatMap})
  }

  deselectSeatMap = () => {
    this.setState({selectedSeatMap: undefined})
  }
}
import {Component, h} from "preact"
import LeafletView from "./LeafletView"
import {deleteSeatMap, list} from "../../api/server"
import {RightPanel} from "./RightPanel"
import {SeatMap} from "../../model/SeatMap"
import * as storage from "../../storage"

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
    this.reload()
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
        deleteSelectedSeatMap={this.deleteSelectedSeatMap}
      />
    </main>
  }

  reload = () => {
    list().then(seatMaps => this.setState({seatMaps}))
  }

  selectSeatMap = (seatMap: SeatMap) => {
    this.setState({selectedSeatMap: seatMap})
  }

  deselectSeatMap = () => {
    this.setState({selectedSeatMap: undefined})
  }

  deleteSelectedSeatMap = () => {
    if (this.state.selectedSeatMap !== undefined) {
      const storedSeatMap = storage.getStoredSeatMap(this.state.selectedSeatMap.id)
      if (storedSeatMap !== undefined && confirm("Delete selected seat map?")) {
        deleteSeatMap(storedSeatMap.id, storedSeatMap.updateKey)
          .then(() => {
            storage.removeSeatMap(storedSeatMap.id)
            this.reload()
          })
      }
    }
  }
}
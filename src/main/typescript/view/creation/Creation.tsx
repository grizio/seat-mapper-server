import {Component, h} from "preact"
import {SeatMapCreation} from "../../model/SeatMapCreation"
import AddressField from "../components/AddressField"
import {Coords} from "../../model/SeatMap"
import {create} from "../../api/server"
import * as router from "preact-router"
import {SeatMapperChangeEvent} from "seat-mapper"
import {generateKey} from "../../utils/string"
import * as storage from "../../storage"

interface State {
  seatMap: SeatMapCreation
}

export default class CreationPage extends Component<{}, State> {
  constructor(props: {}) {
    super(props)
    this.setState({
      seatMap: {
        name: "",
        address: "",
        coordinates: {latitude: 0, longitude: 0},
        description: "",
        updateKey: generateKey()
      }
    })
  }

  render({}: {}, state: State) {
    return (
      <main>
        <form onSubmit={this.onSubmit}>
          <button type="submit">Validate</button>

          <div class="field">
            <label for="creation-name">Name</label>
            <input type="string" name="name" id="creation-name" value={state.seatMap.name} required onInput={this.updateName}/>
          </div>

          <div class="field">
            <label for="creation-description">Description</label>
            <textarea name="description" id="creation-description" value={state.seatMap.description} required
                      onInput={this.updateDescription}/>
          </div>

          <AddressField
            id="creation-address"
            name="address"
            address={this.state.seatMap.address}
            coordinates={this.state.seatMap.coordinates}
            onUpdate={this.updateAddress}
            required
          />

          {
            // @ts-ignore
            <seat-mapper initial-structure={JSON.stringify(this.state.seatMap.map)} onChange={this.updateMap}/>
          }
        </form>
      </main>
    )
  }

  updateSeatMap = (patch: Partial<SeatMapCreation>) => {
    this.setState({
      seatMap: {
        ...this.state.seatMap,
        ...patch
      }
    })
  }

  updateName = (event: Event) => this.updateSeatMap({name: (event.target as HTMLInputElement).value})
  updateAddress = (address: string, coordinates: Coords) => this.updateSeatMap({address, coordinates})
  updateDescription = (event: Event) => this.updateSeatMap({description: (event.target as HTMLTextAreaElement).value})
  updateMap = (event: SeatMapperChangeEvent) => this.updateSeatMap({map: event.structure})

  onSubmit = (event: Event) => {
    event.preventDefault()
    create(this.state.seatMap)
      .then(seatMap => {
        storage.addSeatMap({...seatMap, updateKey: this.state.seatMap.updateKey})
        router.route("/")
      })
  }
}
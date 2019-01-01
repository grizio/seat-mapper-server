import {Component, h} from "preact"
import {SeatMapCreation} from "../../model/SeatMapCreation"
import AddressField from "../components/AddressField"
import {Coords} from "../../model/SeatMap"
import {update} from "../../api/server"
import * as router from "preact-router"
import {SeatMapperChangeEvent} from "seat-mapper"
import * as storage from "../../storage"
import {SeatMapUpdate} from "../../model/SeatMapUpdate"
import {StoredSeatMap} from "../../model/StoredSeatMap"

interface Props {
  id?: string
}

interface State {
  storedSeatMap: StoredSeatMap
  seatMap: SeatMapUpdate
}

export default class UpdatePage extends Component<Props, State> {
  constructor(props: Props) {
    super(props)
    if (props.id !== undefined) {
      const storedSeatMap = storage.getStoredSeatMap(props.id)
      if (storedSeatMap !== undefined) {
        this.setState({
          storedSeatMap,
          seatMap: {
            name: storedSeatMap.name,
            address: storedSeatMap.address,
            coordinates: storedSeatMap.coordinates,
            description: storedSeatMap.description,
            map: storedSeatMap.map
          }
        })
      }
    }
  }

  render({}: Props, state: State) {
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
    update(this.state.storedSeatMap.id, this.state.storedSeatMap.updateKey, this.state.seatMap)
      .then(() => {
        storage.persistSeatMap({...this.state.seatMap, id: this.state.storedSeatMap.id, updateKey: this.state.storedSeatMap.updateKey})
        router.route("/")
      })
  }
}
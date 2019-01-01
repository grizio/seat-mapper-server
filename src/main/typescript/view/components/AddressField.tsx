import {Component, h} from "preact"
import {Coords, coordsEqual} from "../../model/SeatMap"
import Leaflet from "./Leaflet"
import {searchPlaces} from "../../api/osm"
import {Place} from "../../model/Place"

interface Props {
  id: string
  name: string
  address?: string
  coordinates?: Coords
  onUpdate: (address: string, coordinates: Coords) => void
  required?: boolean
}

interface State {
  open: boolean
  confirmClose: boolean
  address: string
  coordinates: Coords
  availablePlaces: Array<Place>
}

export default class AddressField extends Component<Props, State> {
  private ref?: HTMLElement

  constructor(props: Props) {
    super(props)
    this.setState({
      open: false,
      address: props.address || "",
      coordinates: props.coordinates || {latitude: 0, longitude: 0},
      availablePlaces: []
    })
  }

  componentDidMount(): void {
    document.addEventListener("mousedown", this.openOrClose)
  }

  componentWillUnmount(): void {
    document.removeEventListener("mousedown", this.openOrClose)
  }

  render(props: Props, state: State) {
    return (
      <div class="field address-field" ref={ref => this.ref = ref}>
        <label for={props.id}>Address</label>
        <input type="text" name={props.name} id={props.id} value={state.address} required={props.required}
               onInput={this.onInput} onFocus={this.open} onBlur={this.close}/>

        {
          state.open
            ? (
              <div>
                <Leaflet
                  id={`${props.id}-leaflet`}
                  coordinates={this.getLeafletCoordinates()}
                  zoom={this.getLeafletZoom()}
                  markers={state.availablePlaces.map(place => ({
                    id: place.id,
                    label: place.label,
                    coordinates: place.coordinates,
                    onClick: this.selectPlace,
                    selected: coordsEqual(place.coordinates, this.state.coordinates)
                  }))}
                />
              </div>
            )
            : undefined
        }
      </div>
    )
  }

  open = () => {
    this.setState({open: true})
  }

  close = () => {
    this.setState({confirmClose: true}, () => {
      setTimeout(() => {
        if (this.state.confirmClose) {
          this.setState({open: false})
        }
      }, 1)
    })
  }

  openOrClose = (event: Event) => {
    let parent: HTMLElement | null = event.target as HTMLElement
    while (parent !== null) {
      if (parent === this.ref) {
        this.setState({open: true, confirmClose: false})
        return
      }
      parent = parent.parentElement
    }
    this.setState({open: false})
  }

  onInput = (event: Event) => {

    this.setState(
      {address: (event.target as HTMLInputElement).value},
      () => {
        this.search()
        this.triggerUpdate()
      })
  }

  search = () => {
    searchPlaces(this.state.address)
      .then(availablePlaces => this.setState({availablePlaces}))
  }

  triggerUpdate = () => {
    this.props.onUpdate(this.state.address, this.state.coordinates)
  }

  getLeafletCoordinates = () => {
    if (this.state.availablePlaces.length === 0) {
      return {latitude: 0, longitude: 0}
    } else {
      return this.state.availablePlaces[0].coordinates
    }
  }

  getLeafletZoom = () => {
    if (this.state.availablePlaces.length === 1) {
      return 20
    } else {
      return 1
    }
  }

  selectPlace = (id: string) => {
    const place = this.state.availablePlaces.find(_ => _.id === id)
    if (place !== undefined) {
      this.setState({
        address: place.label,
        coordinates: place.coordinates
      }, () => this.triggerUpdate())
    }
  }
}
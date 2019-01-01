import {Component, h} from "preact"
import Router from "preact-router"
import CreationPage from "./creation/Creation"
import MapPage from "./map/MapPage"

export default class App extends Component<{}, {}> {
  constructor(props: {}) {
    super(props)
  }

  render() {
    return (
      <Router>
        <MapPage path="/"/>
        <CreationPage path="/new"/>
      </Router>
    )
  }
}
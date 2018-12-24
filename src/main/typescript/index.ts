import {h, render} from "preact"
import App from "./view/app"
import "seat-mapper"

const root = document.getElementById("root")
if (root) {
  render(h(App, {}), root)
}
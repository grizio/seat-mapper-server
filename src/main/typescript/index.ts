import {h, render} from "preact"
import App from "./view/app"

const root = document.getElementById("root")
if (root) {
  render(h(App, {}), root)
}
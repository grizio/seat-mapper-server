import {Configuration} from "./configuration"

/**
 * If you want to redefine the configuration for your environment, please update this file.
 * Please do not commit this file by adding in a changelist you will ignore, for example.
 */
const localConfiguration: Configuration | undefined = {
  server: "http://localhost:9000",
  leaflet: {
    tileLayer: "https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token={accessToken}",
    attribution: "Map data &copy; <a href=\"https://www.openstreetmap.org/\">OpenStreetMap</a> contributors, <a href=\"https://creativecommons.org/licenses/by-sa/2.0/\">CC-BY-SA</a>, Imagery © <a href=\"https://www.mapbox.com/\">Mapbox</a>",
    id: "mapbox.streets",
    accessToken: "pk.eyJ1Ijoic3RvbWJha2VyIiwiYSI6ImNqcTE1bXlsZzBya2gzeGp5MmV5NWprN2IifQ.zYCIy5M0HBAZ0fr8bnnEng"
  }
}
export default localConfiguration
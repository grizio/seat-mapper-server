// @ts-ignore
const windowConfiguration: Configuration | undefined = window.configuration
import localConfiguration from "./local.conf"

export interface Configuration {
  server: string
  leaflet: {
    tileLayer: string
    attribution: string
    id: string
    accessToken: string
  }
}

const configuration = loadConfiguration()
export default configuration

function loadConfiguration(): Configuration {
  if (localConfiguration !== undefined) {
    console.info("Load configuration from local")
    return localConfiguration
  } else if (windowConfiguration !== undefined) {
    console.info("Load configuration from environment", windowConfiguration)
    return windowConfiguration
  } else {
    throw [
      "No configuration found",
      "If you are in a prod environment, please set environment variables.",
      "If you are in dev environment, please update file local.conf.ts."
    ].join("\n")
  }
}
package sms.configuration

import com.typesafe.config.Config

case class ClientConfiguration(
  leaflet: Leaflet
)

case class Leaflet(
  tileLayer: String,
  attribution: String,
  id: String,
  accessToken: String
)

object ClientConfiguration {
  def load(config: Config): ClientConfiguration = {
    ClientConfiguration(
      leaflet = Leaflet(
        tileLayer = config.getString("client.leaflet.tileLayer"),
        attribution = config.getString("client.leaflet.attribution"),
        id = config.getString("client.leaflet.id"),
        accessToken = config.getString("client.leaflet.accessToken")
      )
    )
  }
}
package sms.json

import sms.configuration.{ClientConfiguration, Leaflet}
import sms.json.CommonJson._
import spray.json.JsonFormat

object ConfigurationJson {
  implicit val leafletFormat: JsonFormat[Leaflet] = jsonFormat4(Leaflet.apply)
  implicit val clientConfigurationFormat: JsonFormat[ClientConfiguration] = jsonFormat1(ClientConfiguration.apply)
}

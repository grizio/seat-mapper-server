package sms.json

import sms.json.CommonJson._
import sms.model.Coordinates.{Latitude, Longitude}
import sms.model.SeatMap.UpdateKey
import sms.model._
import spray.json.{JsonFormat, RootJsonFormat, RootJsonReader}

object SeatMapJson {
  implicit val longitudeFormat: JsonFormat[Longitude] = jsonValueClassFormat(Longitude.apply, Longitude.unapply)
  implicit val latitudeFormat: JsonFormat[Latitude] = jsonValueClassFormat(Latitude.apply, Latitude.unapply)
  implicit val coordinatesFormat: JsonFormat[Coordinates] = jsonFormat2(Coordinates.apply)

  implicit val idFormat: JsonFormat[SeatMap.Id] = jsonValueClassFormat(SeatMap.Id.apply, SeatMap.Id.unapply)
  implicit val nameFormat: JsonFormat[SeatMap.Name] = jsonValueClassFormat(SeatMap.Name.apply, SeatMap.Name.unapply)
  implicit val addressFormat: JsonFormat[SeatMap.Address] = jsonValueClassFormat(SeatMap.Address.apply, SeatMap.Address.unapply)
  implicit val descriptionFormat: JsonFormat[SeatMap.Description] = jsonValueClassFormat(SeatMap.Description.apply, SeatMap.Description.unapply)
  implicit val mapFormat: JsonFormat[SeatMap.Map] = jsonValueClassFormat(SeatMap.Map.apply, SeatMap.Map.unapply)

  implicit val publicSeatMapFormat: RootJsonFormat[PublicSeatMap] = jsonFormat6(PublicSeatMap.apply)

  implicit val updateKeyReader: JsonFormat[SeatMap.UpdateKey] = jsonValueClassFormat(UpdateKey.apply, UpdateKey.unapply)
  implicit val seatMapCreationReader: RootJsonReader[SeatMapCreation] = jsonFormat6(SeatMapCreation.apply)
  implicit val seatMapUpdateReader: RootJsonReader[SeatMapUpdate] = jsonFormat5(SeatMapUpdate.apply)
}

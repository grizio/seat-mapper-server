package sms.json

import java.util.UUID

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsString, JsValue, JsonFormat, deserializationError}

object CommonJson extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val uuidFormat: JsonFormat[UUID] = {
    new JsonFormat[UUID] {
      override def write(obj: UUID): JsValue = JsString(obj.toString)
      override def read(json: JsValue): UUID = json match {
        case JsString(value) =>
          try {
            UUID.fromString(value)
          } catch {
            case x: IllegalArgumentException => deserializationError(s"UUID expected, got ${x}")
          }
        case x => deserializationError(s"UUID expected, got ${x}")
      }
    }
  }

  def jsonValueClassFormat[Internal: JsonFormat, ValueClass](apply: Internal => ValueClass, unapply: ValueClass => Option[Internal]): JsonFormat[ValueClass] = {
    new JsonFormat[ValueClass] {
      override def read(json: JsValue): ValueClass = apply(implicitly[JsonFormat[Internal]].read(json))
      override def write(obj: ValueClass): JsValue = unapply(obj) match {
        case Some(internalValue) => implicitly[JsonFormat[Internal]].write(internalValue)
        case None => throw new RuntimeException(s"Could not unapply ${obj}")
      }
    }
  }
}

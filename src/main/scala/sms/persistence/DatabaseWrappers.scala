package sms.persistence

import java.sql.ResultSet
import java.util.UUID

import scalikejdbc.TypeBinder
import spray.json.{JsValue, JsonParser}

object DatabaseWrappers {
  implicit val uuidWrapper: TypeBinder[UUID] = {
    new TypeBinder[UUID] {
      override def apply(rs: ResultSet, columnIndex: Int): UUID = {
        UUID.fromString(rs.getString(columnIndex))
      }
      override def apply(rs: ResultSet, columnLabel: String): UUID = {
        UUID.fromString(rs.getString(columnLabel))
      }
    }
  }

  implicit val jsValueWrapper: TypeBinder[JsValue] = {
    new TypeBinder[JsValue] {
      override def apply(rs: ResultSet, columnIndex: Int): JsValue = {
        JsonParser(rs.getString(columnIndex))
      }
      override def apply(rs: ResultSet, columnLabel: String): JsValue = {
        JsonParser(rs.getString(columnLabel))
      }
    }
  }

  def valueClassWrapper[Input: TypeBinder, Output](op: Input => Output): TypeBinder[Output] = {
    new TypeBinder[Output] {
      override def apply(rs: ResultSet, columnIndex: Int): Output = {
        op(implicitly[TypeBinder[Input]].apply(rs, columnIndex))
      }
      override def apply(rs: ResultSet, columnLabel: String): Output = {
        op(implicitly[TypeBinder[Input]].apply(rs, columnLabel))
      }
    }
  }
}

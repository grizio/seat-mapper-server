package sms.evolutions

import scalikejdbc.DBSession

trait Evolution {
  def process()(implicit dbSession: DBSession): Unit
}

object Evolution {
  val allEvolutions: Seq[Evolution] = Seq(
    Evolution20181223_create_configuration_table,
    Evolution20181223_create_seat_maps_table
  )
}

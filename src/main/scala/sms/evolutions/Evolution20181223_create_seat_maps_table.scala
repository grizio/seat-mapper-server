package sms.evolutions

import scalikejdbc._

object Evolution20181223_create_seat_maps_table extends Evolution {
  override def process()(implicit dbSession: DBSession): Unit = {
    sql"""
      CREATE TABLE seat_maps(
        id UUID NOT NULL,
        name TEXT NOT NULL,
        address TEXT NOT NULL,
        longitude NUMERIC NOT NULL,
        latitude NUMERIC NOT NULL,
        description TEXT,
        map JSONB NOT NULL,
        updateKey TEXT NOT NULL,
        PRIMARY KEY (id)
      );
    """
      .executeUpdate()
      .apply()
  }
}

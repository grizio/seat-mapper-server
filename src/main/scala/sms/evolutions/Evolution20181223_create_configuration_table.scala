package sms.evolutions

import scalikejdbc._

object Evolution20181223_create_configuration_table extends Evolution {
  override def process()(implicit dbSession: DBSession): Unit = {
    sql"""
      CREATE TABLE configurations(
        key VARCHAR(25) NOT NULL,
        value VARCHAR(25) NOT NULL,
        PRIMARY KEY (key)
      );
    """
      .executeUpdate()
      .apply()
  }
}

package sms.evolutions

import com.typesafe.scalalogging.Logger
import scalikejdbc._

class DatabaseEvolution {
  private val classLoader = classOf[DatabaseEvolution].getClassLoader
  private val logger = Logger(getClass)
  val configurationKey = "version"

  def setup(): Unit = {
    val currentVersion = fetchCurrentVersion()
    val finalVersion = runEvolutions(currentVersion)
    saveFinalVersion(finalVersion)
  }

  private def fetchCurrentVersion(): Int = DB.readOnly { implicit session =>
    try {
      val maxVersion =
        sql"SELECT value FROM configurations WHERE key = ${configurationKey}"
          .map(resultSet => resultSet.int("value"))
          .single()
          .apply()
      maxVersion.getOrElse(0)
    } catch {
      case _: Exception => 0
    }
  }

  private def runEvolutions(currentVersion: Int): Int = DB.localTx { implicit session =>
    logger.info(s"Running evolutions after version ${currentVersion}")
    val evolutions = Evolution.allEvolutions.zipWithIndex.drop(currentVersion)
    if (evolutions.nonEmpty) {
      evolutions.foreach { case (evolution, index) =>
        runEvolution(evolution, index)
      }
    } else {
      logger.info("No evolution to run")
    }
    Evolution.allEvolutions.length
  }

  private def runEvolution(evolution: Evolution, index: Int)(implicit session: DBSession): Unit = {
    logger.info(s"Running evolution ${index + 1}")
    evolution.process()
  }

  private def saveFinalVersion(version: Long): Unit = DB.localTx { implicit session =>
    if (containsConfigurationEvolution) {
      updateConfigurationValue(version)
    } else {
      insertConfigurationValue(version)
    }
  }

  private def containsConfigurationEvolution(implicit session: DBSession): Boolean = {
    sql"""
      SELECT * FROM configurations
      WHERE key = ${configurationKey}
    """
      .map(_.any("value"))
      .single()
      .apply()
      .nonEmpty
  }

  private def updateConfigurationValue(version: Long)(implicit session: DBSession) = {
    sql"""
        UPDATE configurations
        SET value = ${version}
        WHERE key = ${configurationKey}
      """
      .executeUpdate()
      .apply()
  }

  private def insertConfigurationValue(version: Long)(implicit session: DBSession) = {
    sql"""
        INSERT INTO configurations(key, value) VALUES (${configurationKey}, ${version})
      """
      .executeUpdate()
      .apply()
  }
}

package sms.configuration

import com.typesafe.config.{Config, ConfigFactory}

case class Configuration(
  environment: Environment.Value,
  server: Server,
  database: Database
)

object Environment extends Enumeration {
  val dev, prod = Value
}

case class Server(
  protocol: String,
  host: String,
  port: Int
)

case class Database(
  host: String,
  port: Int,
  username: String,
  password: String,
  name: String,
  initialSize: Int,
  maxSize: Int
)

object Configuration {
  def load(): Configuration = {
    load(ConfigFactory.load())
  }

  def load(config: Config): Configuration = {
    // The configuration being loaded at startup and required, we want it to crash the server is something is wrong.
    // So there is no catch of failures.
    Configuration(
      environment = Environment.withName(config.getString("environment")),
      server = Server(
        protocol = config.getString("server.protocol"),
        host = config.getString("server.host"),
        port = config.getInt("server.port")
      ),
      database = Database(
        host = config.getString("database.host"),
        port = config.getInt("database.port"),
        username = config.getString("database.username"),
        password = config.getString("database.password"),
        name = config.getString("database.name"),
        initialSize = config.getInt("database.initialSize"),
        maxSize = config.getInt("database.maxSize")
      )
    )
  }
}

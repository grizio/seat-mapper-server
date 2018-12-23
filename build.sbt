name := "seat-mapper-server"

version := "0.0.0"

scalaVersion := "2.12.8"

libraryDependencies += "com.typesafe" % "config" % "1.3.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"
libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.6"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.6"
libraryDependencies += "ch.megard" %% "akka-http-cors" % "0.3.3"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.5"
libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "3.3.2"

enablePlugins(JavaAppPackaging)
mappings in (Compile, packageDoc) := Seq()

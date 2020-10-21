name := "DataREST2KafkaTopic"

version := "0.1"

scalaVersion := "2.11.8"

// https://mvnrepository.com/artifact/org.json/json
libraryDependencies += "org.json" % "json" % "20190722"

// https://mvnrepository.com/artifact/org.apache.kafka/kafka
libraryDependencies += "org.apache.kafka" %% "kafka" % "2.4.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.3.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-sql
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.4.4"
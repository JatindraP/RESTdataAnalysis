/*
     *************************************************************************************************************
     * getFromRESTURL(URL:String) is getting the data from the rest url in json format and return the json string*
     * restToKAFKA(..) is pushing the data to the kafka broker using kafka producer API                          *
     * pullFromKAFKA(..) is pulling the data from the kafka broker using consumer API store the data in local    *
     *                                    Code written by                                                        *
     *                               Jatindra Nath Pattanaik                                                     *
     *************************************************************************************************************
 */

import java.io.FileWriter
import java.time.LocalDateTime
import java.util.Properties
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.json.JSONObject
import scala.collection.JavaConverters._
object REST {
  def getFromRESTURL(URL:String):String={
    val result = scala.io.Source.fromURL(URL).mkString
    val rawJSON = new JSONObject(result)
    val user = rawJSON.get("results").toString.replace("[", "").replace("]", "")
    val userJSON = new JSONObject(user)
    val userdata = userJSON.get("user").toString.trim
    return userdata
  }
  def restToKAFKA(topic:String,bootstrapServer:String,port:Int,data:String): Unit ={
    val properties = new Properties()
    properties.put("bootstrap.servers",bootstrapServer+":"+port.toString)
    properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String,String](properties)
    val record = new ProducerRecord[String,String](topic,data)
    producer.send(record)

  }

  def pullFromKAFKA(topic:String,bootstrapServer:String,port:Int): Unit ={
    val properties = new Properties()
    properties.put("bootstrap.servers",bootstrapServer+":"+port.toString)
    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    properties.put("auto.offset.reset", "latest")
    properties.put("group.id", "rest")
    val consumer:KafkaConsumer[String,String]=new KafkaConsumer[String,String](properties)
    consumer.subscribe(java.util.Arrays.asList(topic))
    while(true){
      val now = LocalDateTime.now()
      val fileName = now.getYear.toString+now.getMonthValue.toString+now.getDayOfMonth.toString+now.getHour.toString+
        now.getMinute.toString+now.getSecond.toString
      val fileWriter = new FileWriter("/root/SparkApp/data/"+fileName+".json",true)
      for(c<-0 to 49) {
        val record = consumer.poll(2000).asScala
        for (data <- record.iterator) {
          fileWriter.write(data.value().toString+"\n")
          println(data.value())
        }
        println("_________________"+c+"___________________")
      }
      fileWriter.close()
    }
  }

}
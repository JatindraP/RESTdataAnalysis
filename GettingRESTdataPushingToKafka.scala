/*
       *******************************************************************************************
       * GettingRESTdataPushingToKafka is the main Object that getting the data from rest URL by *
       * REST.getFromRESTURL(url) and pushing the same data to the kafka broker by               *
       * REST.restToKAFKA(topic,server,port,userdata),REST is a Singleton object in which        *
       *getFromRESTURL and restToKAFKA methods are written.                                      *
       *                                   Code written by                                       *
       *                               Jatindra Nath Pattanaik                                   *
       * *****************************************************************************************
 */
object GettingRESTdataPushingToKafka {
  def main(args: Array[String]): Unit = {
    val url = "https://randomuser.me/api/0.8"
    val server ="sandbox-hdp.hortonworks.com"
    val port = 6667
    val topic ="MyTestTopic"
    while (true) {
      val userdata=REST.getFromRESTURL(url)
      REST.restToKAFKA(topic,server,port,userdata)
      println(userdata)
      Thread.sleep(500)
    }
  }
}
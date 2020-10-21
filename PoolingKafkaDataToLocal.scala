/*
       ***********************************************************************************************
       * PoolingKafkaDataToLocal is the main Object that pulling the data from rest kafka broker and *
       * save the data in local by REST.pullFromKAFKA(topic,server,port) REST is a Singleton object  *
       * in which  pullFromKAFKA method is written.                                                  *
       *                                   Code written by                                           *
       *                               Jatindra Nath Pattanaik                                       *
       * *********************************************************************************************
 */
object PoolingKafkaDataToLocal {
  def main(args: Array[String]): Unit = {
    val server ="sandbox-hdp.hortonworks.com"
    val port = 6667
    val topic ="MyTestTopic"
    REST.pullFromKAFKA(topic,server,port)
  }

}
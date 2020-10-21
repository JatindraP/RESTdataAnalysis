/*
    ************************************************************************************************
    * Land2Stage is main object that contains main spark job,it reading the json and make a        *
    * spark dataFrame, on that dataFrame some preprocessing are done,then save the dataFrame       *
    * in a hive table for further prcessing .The transactionLoad.sql file move some required data  *
    * to another hive table                                                                        *
    *                                  Code written by                                             *
    *                              Jatindra Nath Pattanaik                                         *
    * **********************************************************************************************
 */
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.util.Random

object Land2Stage {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("yarn").setAppName("Stream Data move from landing to staging")
    val spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate()
    val rawdataDF=spark.read.json("/data/reststream/landing/")
    val card_type = udf((username:String)=>{
      val A = Array("MasterCard","RuPay","Visa","Maestro","credit card","cash on delivery")
      Random.shuffle(A.toList).head
    })
    val transaction_amt = udf((username:String)=>{
    val r = new Random
    100+ r.nextInt((50000-100) + 1)})
    import spark.implicits._
    val rawdataDF1=rawdataDF.withColumn("new_name",concat($"name.first",lit(" "),$"name.last"))
      .drop("name").withColumnRenamed("new_name","name")
    val requireDF = rawdataDF1.select("username","name","gender","registered","location","dob","email",
    "phone").withColumn("card_type",card_type($"username"))
      .withColumn("transaction_amt",transaction_amt($"username") ).na.drop()
    requireDF.write.mode("overwrite").saveAsTable("staging.transactions")
    spark.stop()
  }
}
# RESTdataAnalysis
A dummy project that stream data from REST API and store the data into hive. Kafka,Spark,Hive are used in this project 
Scala and shell scripting is also used 
Most of the code written in Scala ,shell scripting is used to trigger the jobs. 
See the document to undrestand the project architecture
For kafka topic create,list kafka topic and other kafka related command check kafka-notes.txt
Follow the bellow order
1- GettingRESTdataPushingToKafka.scala  :- to get data from rest url and push it to kafka broker
2- REST.scala :- methods used in GettingRESTdataPushingToKafka.scala and PoolingKafkaDataToLocal.scala
3- PoolingKafkaDataToLocal.scala :- pull the data from the kafka broker and store the data in local file system in json file format
4- moveJson-local2hdfs.sh :- moving the json file from local file system to hdfs
5- staging-transaction-table.sql :- creating the table in hive , this table will be used by Land2Stage.scala(spark job)
6- Land2Stage.scala :- sparkSql read the json files from hdfs and load in the hive table(create the jar by sbt)
7- target-transactions-table.sql :- create a hive table to store data as per the requirement by fetching from staging.transactions table
8- transactionLoad.sql :- loading the data into target.transactions from staging.transactions
9- TransactionDataAnalysis.sh :- tish script is to trigger the spark job and transactionLoad.sql
Thank you for following the steps
Please give me feedback and you can connect with me on LinkedIn
linkedin :-  www.linkedin.com/in/jatindra-nath-pattanaik-07954b179
_________________________________THANK YOU______________________________________

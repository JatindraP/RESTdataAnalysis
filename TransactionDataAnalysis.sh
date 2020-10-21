#!/bin/sh
#***************************************************
#* This script is written to trigger the spark job *
#*            Script written by                    *
#*          Jatindra Nath Pattanaik                *
#***************************************************
BASE_PATH="/root/SparkApp"
LOG_FILE=${BASE_PATH}/log/Transaction-`date +%Y-%m-%d-%H-%M`.log
HDFS_DATA_PATH="/data/reststream/landing/"
HDFS_ARCHIVE_PATH="/data/reststream/archive/"
SQL_FILE=${BASE_PATH}/transactionLoad.sql
#----------------------
#| Logging information|
#----------------------
log_message()
{
	timeNow=`date +%H-%M-%S`
	echo "${timeNow}[INFO]-$1">>${LOG_FILE} 
}
#--------------------------------------------------------------------------------------------
#| Checking files in HDFS_DATA_PATH and loading the data into staging table by spark in hive|
#--------------------------------------------------------------------------------------------
check_hdfs_path_loading_to_staging()
{
	file_count=`hadoop fs -ls ${HDFS_DATA_PATH} | wc -l`
	if [ ${file_count} -gt 1 ]
	then
		log_message "${file_count} files present in hdfs location ${HDFS_DATA_PATH}"
		log_message "Loading the data into staging table..."
		spark-submit --master yarn \
				--deploy-mode client \
				--class Land2Stage DataREST2KafkaTopic.jar >> ${LOG_FILE}
	else
		log_message "ERROR- No files present in hdfs location ${HDFS_DATA_PATH}" 
		exit 0
	fi
}
#------------------------------------------------------------------------
#| Loading the data from Staging to Target table by Hive Query Language |
#------------------------------------------------------------------------
staging_to_target()
{
	sql=`cat ${SQL_FILE}`
	log_message "Executing the query ${sql}"
	hive -f ${SQL_FILE}>>${LOG_FILE}
	if [ $? -eq 0 ]
	then
		log_message "Data loading from staging table to target table sussessful"
		log_message "Moving the files to archive"
		hadoop fs -mv ${HDFS_DATA_PATH}/* ${HDFS_ARCHIVE_PATH}
		if [ $? -eq 0 ]
		then
			log_message "Moving files to archive sussessful"
		else
			log_message "ERROR- error while archiving"
			exit 0
		fi
	else
		log_message "ERROR- error while executing ${SQL_FILE}"
		exit 0
	fi
}
#-----------------
#|  MAIN PROGRAM |
#-----------------
check_hdfs_path_loading_to_staging
staging_to_target
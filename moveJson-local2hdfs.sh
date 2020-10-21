#!/bin/bash
#*****************************************************************************
#* This shell script is to move the json data from local file system to hdfs *
#*                            Script written by                              *
#*                         Jatindra Nath Pattanaik                           *
#*****************************************************************************
LOCAL_DATA_PATH="/root/SparkApp/data/"
HDFS_LANDING_PATH="/data/reststream/landing/"
# main program starts here
file_count=`ls -lrt $LOCAL_DATA_PATH | wc -l`
echo $file_count
if [ $file_count -gt 1 ]
then
	echo $file_count "files present at the path "$LOCAL_DATA_PATH
	files=`ls $LOCAL_DATA_PATH`
	echo $files
	for file in $files
	do
		echo "Moving "$LOCAL_DATA_PATH$file" to hdfs path "$HDFS_LANDING_PATH" ..."
		hadoop fs -put $LOCAL_DATA_PATH$file $HDFS_LANDING_PATH
		echo "file moved!"
		rm $file
	done
else
	echo "No files present at the path "$LOCAL_DATA_PATH 
fi
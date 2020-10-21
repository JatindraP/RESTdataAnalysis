/*
    ******************************************************************************
    *Execute the bellow query before executing the spark job to create the table *
    *so that spark dataFrame can saved in the hive table                         *
    *                          query written by                                  *
    *                      Jatindra Nath Pattanaik                               *
    ******************************************************************************
*/
CREATE TABLE staging.transactions
(
username STRING,
name STRING,
gender STRING,
registered BIGINT,
location STRUCT < city:STRING,state:STRING,street:STRING,zip:STRING >,
dob BIGINT,
email STRING,
phone STRING,
card_type STRING,
transaction_amt BIGINT
)
STORED AS PARQUET;
/*
    ******************************************************************************
    *Execute the bellow query before executing the transactionLoad.sql to create *
    *the target table where we nead to load the data as per the requirement      *
    *                          query written by                                  *
    *                      Jatindra Nath Pattanaik                               *
    ******************************************************************************
*/
CREATE TABLE target.transactions
(
username STRING,
name STRING,
gender STRING,
registration_date TIMESTAMP,
city STRING,
state STRING,
street STRING,
zip STRING,
dateOfBirth DATE,
email STRING,
phone STRING,
card_type STRING,
transaction_amt BIGINT
)
STORED AS PARQUET;
/*
    *****************************************************************
    *This query is to move the required data to target hive table   *
    *                          query written by                     *
    *                      Jatindra Nath Pattanaik                  *
    *****************************************************************
*/
INSERT INTO target.transactions
SELECT
username,
name,
gender,
cast(from_unixtime(registered DIV 1000) as timestamp) as registration_date,
location.city as city,
location.state as state,
location.street as street,
location.zip as zip,
to_date(from_unixtime(dob DIV 1000)) as dateOfBirth,
email,
phone,
card_type,
transaction_amt
FROM staging.transactions;
TRUNCATE TABLE staging.transactions;
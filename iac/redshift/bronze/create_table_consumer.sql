-- COSTUMER
CREATE EXTERNAL TABLE bronze.bronze_consumer (
    customer_id varchar,
    language varchar,
    created_at varchar,
    active varchar,
    pseudonymous_id varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/bronze/consumer/_symlink_format_manifest';
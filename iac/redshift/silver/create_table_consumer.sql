-- COSTUMER
CREATE EXTERNAL TABLE silver.silver_consumer (
    pseudonymous_id varchar,
    customer_id varchar,
    language varchar,
    created_at timestamp,
    active boolean
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/silver/consumer/_symlink_format_manifest';
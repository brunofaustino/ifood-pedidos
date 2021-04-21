-- RESTAURANT
CREATE EXTERNAL TABLE bronze.bronze_restaurant (
    id varchar,
    created_at timestamp,
    enabled boolean ,
    price_range varchar,
    average_ticket integer,
    takeout_time integer ,
    delivery_time integer ,
    minimum_order_value decimal,
    merchant_zip_code integer ,
    merchant_city varchar,
    merchant_state varchar,
    merchant_country varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/bronze/restaurant/_symlink_format_manifest'
TABLE PROPERTIES('delta.compatibility.symlinkFormatManifest.enabled'='true');
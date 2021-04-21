-- ORDER
CREATE EXTERNAL TABLE bronze.bronze_order (
    customer_id varchar,
    customer_name varchar,
    delivery_address_city varchar,
    delivery_address_country varchar,
    delivery_address_district varchar,
    delivery_address_external_id varchar,
    delivery_address_latitude varchar,
    delivery_address_longitude varchar,
    delivery_address_state varchar,
    delivery_address_zip_code varchar,
    merchant_id varchar,
    merchant_latitude varchar,
    merchant_longitude varchar,
    merchant_timezone varchar,
    order_created_at timestamp ,
    order_id varchar,
    order_scheduled boolean ,
    order_scheduled_date timestamp ,
    order_total_amount decimal,
    origin_platform varchar,
    items varchar,
    pseudonymous_id varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/bronze/order/_symlink_format_manifest'
TABLE PROPERTIES('delta.compatibility.symlinkFormatManifest.enabled'='true');
-- ORDER
CREATE EXTERNAL TABLE gold.gold_order (
    pseudonymous_id varchar,
    customer_id varchar,
    order_id varchar,
    order_items_id varchar,
    merchant_id varchar,
    customer_name varchar,
    delivery_address_city varchar,
    delivery_address_country varchar,
    delivery_address_district varchar,
    delivery_address_external_id varchar,
    delivery_address_latitude varchar,
    delivery_address_longitude varchar,
    delivery_address_state varchar,
    delivery_address_zip_code varchar,
    merchant_latitude varchar,
    merchant_longitude varchar,
    merchant_timezone varchar,
    order_created_at varchar,
    order_scheduled varchar,
    order_scheduled_date varchar,
    order_total_amount varchar,
    origin_platform varchar,
    order_created_at_time varchar,
    order_created_at_date varchar,
    merchant_price_range varchar,
    merchant_takeout_time integer,
    merchant_delivery_time integer,
    merchant_minimum_order_value decimal,
    merchant_city varchar,
    merchant_zip_code integer,
    merchant_state varchar,
    merchant_country varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/gold/order/_symlink_format_manifest'
TABLE PROPERTIES('delta.compatibility.symlinkFormatManifest.enabled'='true');
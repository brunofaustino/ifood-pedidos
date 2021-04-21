-- ORDER ITEMS
CREATE EXTERNAL TABLE silver.silver_order_item (
    order_items_id varchar,
    name varchar,
    quantity integer,
    addition_value decimal,
    addition_currency varchar,
    discount_value decimal,
    discount_currency varchar,
    unit_price_value decimal,
    unit_price_currency varchar,
    total_value_value decimal,
    total_value_currency varchar,
    consumer_note varchar,
    integration_id varchar,
    category_name varchar,
    total_addition_value decimal,
    total_addition_currency varchar,
    total_discount_value decimal,
    total_discount_currency varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/silver/order_item/_symlink_format_manifest'
TABLE PROPERTIES('delta.compatibility.symlinkFormatManifest.enabled'='true');
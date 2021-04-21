-- GARNISH ITEMS
CREATE EXTERNAL TABLE gold.gold_order_garnish_item (
    garnish_items_id varchar,
    name varchar,
    quantity integer,
    addition_value decimal,
    addition_currency varchar,
    discount_value decimal,
    discount_currency varchar,
    sequence varchar,
    unit_price_value decimal,
    unit_price_currency varchar,
    category_id varchar,
    category_name varchar,
    integration_id varchar,
    total_value_value decimal,
    total_value_currency varchar
)
ROW FORMAT SERDE 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe'
STORED AS
INPUTFORMAT 'org.apache.hadoop.hive.ql.io.SymlinkTextInputFormat'
OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat'
LOCATION 's3://{}/delta_lake/gold/garnish_item/_symlink_format_manifest'
TABLE PROPERTIES('delta.compatibility.symlinkFormatManifest.enabled'='true');
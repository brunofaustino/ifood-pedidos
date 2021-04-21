from argparse import ArgumentParser
import psycopg2

def create_redshift_tables(cursor, aws_s3_bucket):
    print('>>> Creating tables...')

    scripts = {
        'bronze': {
            'bronze_restaurant': 'create_table_restaurant',
            'bronze_consumer': 'create_table_consumer',
            'bronze_order': 'create_table_order'
        },
        'silver': {
            'silver_restaurant':'create_table_restaurant',
            'silver_consumer':'create_table_consumer',
            'silver_order':'create_table_order',
            'silver_order_item':'create_table_order_item',
            'silver_order_garnish_item':'create_table_order_garnish_item'
        },
        'gold': {
            'gold_order':'create_table_order',
            'gold_order_item':'create_table_order_item',
            'gold_order_garnish_item':'create_table_order_garnish_item'
        }
    }

    for layer_tables in scripts.items():
        layer = layer_tables[0]
        tables = layer_tables[1]
        print('- {} layer'.format(layer))
        for table in tables.items():
            sql = "DROP TABLE IF EXISTS {}.{};".format(layer, table[0])
            file = open("iac/redshift/{}/{}.sql".format(layer, table[1]), "r").read().format(aws_s3_bucket)
            cursor.execute(sql)
            cursor.execute(file)

def create_redshift_schema(cursor, aws_account_id):
    print('>>> Defining schemas')

    cursor.execute("DROP SCHEMA IF EXISTS bronze;")
    cursor.execute("DROP SCHEMA IF EXISTS silver;")
    cursor.execute("DROP SCHEMA IF EXISTS gold;")

    cursor.execute("create external schema bronze from data catalog"
                   " database 'delta_lake'"
                   " iam_role 'arn:aws:iam::{}:role/Databricks_Redshift_Spectrum_Glue_Catalog'"
                   " create external database if not exists;".format(aws_account_id))
    cursor.execute("create external schema silver from data catalog"
                   " database 'delta_lake'"
                   " iam_role 'arn:aws:iam::{}:role/Databricks_Redshift_Spectrum_Glue_Catalog'"
                   " create external database if not exists;".format(aws_account_id))
    cursor.execute("create external schema gold from data catalog"
                   " database 'delta_lake'"
                   " iam_role 'arn:aws:iam::{}:role/Databricks_Redshift_Spectrum_Glue_Catalog'"
                   " create external database if not exists;".format(aws_account_id))

def create_redshift_conn(host):
    print('Connecting with RedShift...')
    conn = psycopg2.connect(user='awsuser',
                            password='br0sil2020BFHn83Nj3f',
                            host=host,
                            dbname='delta_lake',
                            port=5439)
    conn.autocommit = True
    cursor = conn.cursor()
    return conn, cursor

parser = ArgumentParser()
parser.add_argument("-E", "--endpoint-redshift")
parser.add_argument("-A", "--aws-account-id")
parser.add_argument("-B", "--aws-s3-bucket")
args = vars(parser.parse_args())

if args["aws_account_id"] != None:
    if args["endpoint_redshift"] != None:
        if args["aws_s3_bucket"] != None:
            aws_account_id = args["aws_account_id"]
            endpoint_redshift = args['endpoint_redshift']
            aws_s3_bucket = args['aws_s3_bucket']
            conn, cursor = create_redshift_conn(endpoint_redshift)
            create_redshift_schema(cursor, aws_account_id)
            create_redshift_tables(cursor, aws_s3_bucket)
            # conn.commit()
            conn.close()
            cursor.close()
        else: print("S3 bucket name is missing. Use --aws-s3-bucket")
    else:
        print("RedShift Endpoint is missing. Use --endpoint-redshift")
else:
    print("RedShift AWS account ID is missing. Use --aws-account-id")
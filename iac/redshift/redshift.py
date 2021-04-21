import psycopg2
import uuid

class Redshift:
    def __init__(self, logger):
        self.logger = logger
        self.DATABASE = "dev"
        self.USER = "awsuser"
        self.PASSWORD = "br$sil2020BF"
        self.HOST = "redshift-cluster-2.crmuvp75d67d.us-west-2.redshift.amazonaws.com"
        self.PORT = "5439"
        self.SCHEMA = "public"
        # self.cursor, self.conn = self.create_cursor_connection()

    def create_cursor_connection(self):
        self.conn = psycopg2.connect(user = self.USER,
                                      password = self.PASSWORD,
                                      host = self.HOST,
                                      dbname = self.DATABASE,
                                      port = self.PORT)
        self.cursor = self.conn.cursor()
        self.logger.info("Redshift connection created")

        # return cursor, conn

    def close_connection(self):
        self.cursor.close()
        self.conn.commit()
        self.conn.close()

    def create_job(self, job_name, file_nam, status):
        self.create_cursor_connection()
        id = uuid.uuid1()
        self.cursor.execute("insert into job (id, name, file, status) VALUES ('{0}','{1}','{2}','{3}')".format(id, job_name, file_nam, status))
        self.logger.info("New job id: {}".format(id))
        self.close_connection()
        return id

    def get_job_status(self, id):
        self.create_cursor_connection()
        self.cursor.execute("select * from job where id = '{}'".format(id))
        for row in self.cursor.fetchall():
            job_status = row[3]
            self.logger.info("Job status: {}".format(job_status))
        self.close_connection()
        return job_status
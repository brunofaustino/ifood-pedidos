FROM openjdk:11

EXPOSE 8082

ARG JAR_FILE=build/libs/api-restaurant-0.0.1-SNAPSHOT.jar
ENV REDSHIFT_ENDPOINT="stack-data-redshiftcluster-871b7iyxsy1.crmuvp75d67d.us-west-2.redshift.amazonaws.com"

COPY ${JAR_FILE} api-restaurant.jar

ENTRYPOINT ["java", "-DdatasourceHostname=${REDSHIFT_ENDPOINT}", "-jar", "/api-restaurant.jar"]
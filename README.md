# ge-devices-consumer
POC project for Processing GE Devices Report for multiple tenants

Task 1:
a. start.spring.io , include dependencies - web, kafka, actuator, lombok,posgress
b. docker-compose : include Kafka & Postgress.
c. test Kafka & postgress connections.

Task 2:
a. create a sample S3 bucket structure at src/main/resources
manipal_01/report_01
apollo_01/report_01
b. create a Kafka Consumer - Listener.
c. Trigger an event like a sample S3 upload message giving the path to the s3 bucket file 

Task 3:
a. Create separate schemas in postgres DB for manipal_01, apollo_01
b. In task 2.c method, write logic to separate the tenantID and add the contents of devicesList
into the tenantkey
c. create model and repository for the tenantDevices field in the sample file.
d. persist the reports data into devices table of each schema.


#SQL Statements:
-- Step 1: Create schema if not already
CREATE SCHEMA IF NOT EXISTS apollo01;

-- Step 2: Create table in new schema
CREATE TABLE apollo01.device_data (LIKE public.device_data INCLUDING ALL);

-- Step 3 : Create sequence
CREATE SEQUENCE IF NOT EXISTS apollo01.device_data_seq START 1;



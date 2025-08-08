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

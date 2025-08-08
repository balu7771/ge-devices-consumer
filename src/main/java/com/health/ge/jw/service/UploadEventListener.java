package com.health.ge.jw.service;

import com.health.ge.jw.exception.FileProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadEventListener {

    public static final Logger log = LoggerFactory.getLogger(UploadEventListener.class);


    @Autowired
    FileHandlingService fileHandlingService;


    /**
     * Simulation of S3 bucket file upload is done via Kafka UI to produce message.
     * Message sample is : manipal01/report01.csv
     * That is recieved here in the Kafka listener method.
     *
     * @param uploadEventMessage
     */
    @KafkaListener(topics = "file-upload-events", groupId = "ge-file-processor")
    public void fileUploadListener(String uploadEventMessage) {

        log.info("An event with file upload message to S3 Bucket is received as: " + uploadEventMessage);

        processEvent(uploadEventMessage);


    }

    private void processEvent(String uploadEventMessage) {

        log.info("Extracting the file name from message.");
        String fileName = uploadEventMessage;
        log.info("File name is: " + fileName);


        try {
            log.info("Simulation of downloading contents of the file from S3 bucket");
            String fileContent = readFileFromResources(fileName);


            log.info("Handle the file in another service");
            fileHandlingService.processFileContent(fileContent, fileName);

        } catch (Exception e) {

            throw new FileProcessingException("Application failed to process event.", e);

        }


    }

    private String readFileFromResources(String fileName) throws Exception {

        ClassPathResource resource = new ClassPathResource("S3Bucket/" + fileName);
        return Files.readString(Paths.get(resource.getURI()));

    }


}

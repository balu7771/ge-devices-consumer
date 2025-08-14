package com.health.ge.jw.service;

import com.health.ge.jw.entity.FileProcessingStatus;
import com.health.ge.jw.exception.FileProcessingException;
import com.health.ge.jw.repository.FileProcessingStatusRepository;
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

    @Autowired
    FileProcessingTracker fileProcessingTracker;

    @Autowired
    FileProcessingStatusRepository fileProcessingStatusRepository;

    private static void deleteProcessedFile(String fileName) throws IOException {
        ClassPathResource resource = new ClassPathResource("S3Bucket/" + fileName);
        Files.delete(Paths.get(resource.getURI()));
    }

    /**
     * Simulation of S3 bucket file upload is done via Kafka UI to produce message.
     * Message sample is : manipal01/report01.csv
     * That is recieved here in the Kafka listener method.
     *
     * @param uploadEventMessage
     */
    @KafkaListener(topics = "file-upload-events_v2", groupId = "ge-file-processor_v2")
    public void fileUploadListener(String uploadEventMessage) {

        log.info("An event with file upload message to S3 Bucket is received as: " + uploadEventMessage);

        processEvent(uploadEventMessage);


    }

    private void processEvent(String uploadEventMessage) {

        log.info("Extracting the file name from message.");
        String fileName = uploadEventMessage;
        log.info("File name is: " + fileName);

        // if file was already processed, no need to continue to
        // avoid duplicates.
        if (fileProcessingTracker.isFileAlreadyProcessed(fileName)) {
            log.info("File :" + fileName + " was already processed successfullly, skipping. ");
            return;
        }



        try {
            log.info("Simulation of downloading contents of the file from S3 bucket");
            String fileContent = readFileFromResources(fileName);


            log.info("Handle the file in another service");
            fileHandlingService.processFileContent(fileContent, fileName);


            log.info("Delete the file if processed successfully");
            deleteProcessedFile(fileName);

            markstatus(fileName, "" );

            log.info("file Deleted successfully and process completed.");


        } catch (Exception e) {

            markstatus(fileName, "Failed" );

            throw new FileProcessingException("Application Failed to process event.", e);

        }


    }

    private void markstatus(String fileName, String errorMessage) {
        //get the tenantID from the filename
        FileProcessingStatus fileProcessingStatus =
                fileProcessingStatusRepository.findByFileName(fileName);

        String tenantID = fileProcessingStatus.getTenantId();

        // update the status of the file process to failed.
        if (errorMessage.contains("Failed")) {
            fileProcessingTracker.markStatus(fileName, tenantID,
                    FileProcessingStatus.ProcessStatus.FAILED, errorMessage);
        } else
            fileProcessingTracker.markStatus(fileName, tenantID,
                    FileProcessingStatus.ProcessStatus.SUCCESS, errorMessage);
    }

    private String readFileFromResources(String fileName) throws Exception {

        ClassPathResource resource = new ClassPathResource("S3Bucket/" + fileName);
        return Files.readString(Paths.get(resource.getURI()));

    }


}

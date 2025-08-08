package com.health.ge.jw.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UploadEventListener {

    public static final Logger log = LoggerFactory.getLogger(UploadEventListener.class);

    @KafkaListener(topics="file-upload-events", groupId = "ge-file-processor")
    public void fileUploadListener(String uploadEventMessage){

        log.info("File uploaded to S3 Bucket and the event is : " + uploadEventMessage);

    }


}

package com.health.ge.jw.service;

import com.health.ge.jw.entity.FileProcessingStatus;
import com.health.ge.jw.repository.FileProcessingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class FileProcessingTracker {

    private final static Logger log = LoggerFactory.getLogger(FileProcessingTracker.class);

    @Autowired
    FileProcessingStatusRepository fileProcessingStatusRepository;


    public void markPending(String fileName, String tenantID) {
        FileProcessingStatus status = new FileProcessingStatus(fileName,
                tenantID,
                FileProcessingStatus.ProcessStatus.PENDING,
                "");

        fileProcessingStatusRepository.save(status);
        log.info("Process Started.");


    }


}

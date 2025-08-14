package com.health.ge.jw.service;

import com.health.ge.jw.entity.FileProcessingStatus;
import com.health.ge.jw.repository.FileProcessingStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileProcessingTracker {

    private final static Logger log = LoggerFactory.getLogger(FileProcessingTracker.class);

    @Autowired
    FileProcessingStatusRepository fileProcessingStatusRepository;


    public void markPending(String fileName, String tenantID) {
        FileProcessingStatus status = new FileProcessingStatus(fileName, tenantID,
                FileProcessingStatus.ProcessStatus.PENDING, "");

        fileProcessingStatusRepository.save(status);
        log.info("Process Started.");


    }

    public void markStatus(String fileName, String tenantID,
                           FileProcessingStatus.ProcessStatus message, String errorMessage) {


        FileProcessingStatus status = fileProcessingStatusRepository.findByFileName(fileName);
        if(status!=null){
            status.setTenantId(tenantID);
            status.setStatus(message);
            status.setErrorMessage(errorMessage);
            fileProcessingStatusRepository.save(status);
            log.info("Process completed Successfully or Failed.");
        }




    }

    public boolean isFileAlreadyProcessed(String fileName) {

        try {
            log.info("Checking if file already processed.");
            FileProcessingStatus status = fileProcessingStatusRepository.findByFileName(fileName);
            return status != null && status.getStatus() == FileProcessingStatus.ProcessStatus.SUCCESS;
        } catch (Exception e) {

            // for coding purpose, assume the file already exists if there is an exception
            return true;
        }


    }


}

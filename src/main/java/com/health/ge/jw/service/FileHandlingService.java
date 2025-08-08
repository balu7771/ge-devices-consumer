package com.health.ge.jw.service;

import com.health.ge.jw.entity.DeviceData;
import com.health.ge.jw.repository.DeviceDataRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileHandlingService {

    public static final Logger log = LoggerFactory.getLogger(FileHandlingService.class);

    @Autowired
    DeviceDataRepository deviceDataRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void processFileContent(String fileContent, String fileName) {
        log.info("Inside method to process file content.");

        String tenantID = extractTenantFromFileName(fileName);

        log.info("Tenant ID is : " + tenantID);

        List<DeviceData> deviceDataList = parseCSVContent(fileContent);

        entityManager.createNativeQuery("SET search_path TO " + tenantID.trim()).executeUpdate();

        log.info("Schema changed to : " +tenantID.trim());

        for (DeviceData deviceData : deviceDataList) {
            deviceDataRepository.save(deviceData);
        }

        log.info("Data persisted to Tenant DB");
    }

    private List<DeviceData> parseCSVContent(String fileContent) {

        BufferedReader reader = new BufferedReader(new StringReader(fileContent));

        List<DeviceData> records = reader.lines()
                .skip(1)
                .map(this::mapToDeviceData)
                .collect(Collectors.toList());


        return records;


    }

    private DeviceData mapToDeviceData(String record) {
        String[] fields = record.split(",");

        DeviceData device = new DeviceData();
        device.setTenantId(fields[0].trim());
        device.setDeviceId(fields[1].trim());
        device.setModel(fields[2].trim());
        device.setManufacturer(fields[3].trim());
        device.setDeviceType(fields[4].trim());
        device.setApprovalDate(LocalDate.parse(fields[5].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        return device;
    }


    private String extractTenantFromFileName(String fileName) {
        return fileName.substring(0, fileName.lastIndexOf("/"));
    }
}

package com.health.ge.jw.service;

import com.health.ge.jw.controller.DeviceIds;
import com.health.ge.jw.entity.DeviceData;
import com.health.ge.jw.repository.DeviceDataRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GEDeviceReportService {

    public static final Logger log = LoggerFactory.getLogger(GEDeviceReportService.class);

    @Autowired
    DeviceDataRepository deviceDataRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public List<DeviceData>getDeviceDataForTenant(String tenantID, String deviceID) {
        entityManager.createNativeQuery("SET search_path TO " + tenantID.trim()).executeUpdate();
        log.info("Schema changed to "+tenantID.trim());
        List<DeviceData> deviceDataList =  deviceDataRepository.findByDeviceId(deviceID);

        return deviceDataList;
    }

    /*@Transactional
    public List<DeviceData> getDeviceData(String tenantID, DeviceIds deviceIds) {

        entityManager.createNativeQuery("SET search_path TO " + tenantID.trim()).executeUpdate();
        log.info("Schema changed to "+tenantID.trim());

        return deviceDataRepository.findByDeviceIdIn(deviceIds.deviceIds());
    }*/


}

package com.health.ge.jw.controller;

import com.health.ge.jw.entity.DeviceData;
import com.health.ge.jw.repository.DeviceDataRepository;
import com.health.ge.jw.service.GEDeviceReportService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/devices")
public class GEDeviceReport {

    public static final Logger log = LoggerFactory.getLogger(GEDeviceReport.class);

    @Autowired
    GEDeviceReportService geDeviceReportService;


    /*@PostMapping("/report")
    public List<DeviceData> getReport(@RequestHeader("X-TenantID") String tenantID,
                                      @RequestBody DeviceIds deviceIds){

        log.info("Tenant ID is : " + tenantID);

        return geDeviceReportService.getDeviceData(tenantID, deviceIds);

    }*/

    @GetMapping("report")
    public ResponseEntity<List<DeviceData>> getReport(@RequestHeader("X-TenantID") String tenantID,
                                                     @RequestParam("deviceID") String deviceID) {

        List<DeviceData> deviceDataResponse = geDeviceReportService.getDeviceDataForTenant(tenantID, deviceID);

        return ResponseEntity.ok(deviceDataResponse);
    }


}

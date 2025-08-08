package com.health.ge.jw.repository;

import com.health.ge.jw.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData,Long> {


    List<DeviceData> findByDeviceIdIn(List<String> deviceIds);

    List<DeviceData> findByDeviceId(String deviceID);
}

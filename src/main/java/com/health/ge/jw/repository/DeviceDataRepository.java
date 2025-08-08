package com.health.ge.jw.repository;

import com.health.ge.jw.entity.DeviceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceDataRepository extends JpaRepository<DeviceData,Long> {


}

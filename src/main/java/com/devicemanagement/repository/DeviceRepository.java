package com.devicemanagement.repository;

import com.devicemanagement.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * This is the Repository Interface for Device Entity.
 * This interface is responsible to provide methods to interact with the DB
* */

public interface DeviceRepository extends JpaRepository<Device, Long> {

    /**
     * This method finds devices by their brand
     * @param brand name of the device to search
     * @return a list of devices for the given brand name
     * */
    List<Device> findByBrand(String brand);
}

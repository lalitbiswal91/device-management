package com.devicemanagement.service;

import com.devicemanagement.dto.CreateDeviceDto;
import com.devicemanagement.dto.UpdateDeviceDto;
import com.devicemanagement.entity.Device;

import java.util.List;
import java.util.Optional;


/**
 * Interface which defines the contract for Device Management Service
 * This interface provides methods for maintaining the devices
* */

public interface DeviceService {

    Device addDevice(CreateDeviceDto createDeviceDto);

    Optional<Device> getDeviceById(Long id);

    List<Device> getAllDevices();

    Device updateDevice(Long id, UpdateDeviceDto updateDeviceDto);

    void deleteDevice(Long id);

    List<Device> searchDeviceByBrand(String brand);

}

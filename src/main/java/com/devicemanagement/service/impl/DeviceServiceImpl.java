package com.devicemanagement.service.impl;

import com.devicemanagement.dto.CreateDeviceDto;
import com.devicemanagement.dto.UpdateDeviceDto;
import com.devicemanagement.entity.Device;
import com.devicemanagement.exception.DeviceNotFoundException;
import com.devicemanagement.repository.DeviceRepository;
import com.devicemanagement.service.DeviceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
 * This is the implementation class for Device Service Interface.
 * It provides
 * It uses @RestControllerAdvice to cover the controller classes
 * The logging is done by @Slf4j
 *
 * @author Lalit Biswal
 */


@Slf4j
@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;


    /**
     * This method adds a new device to the DB
     *
     * @param createDeviceDto contains the information of a new device
     * @return the newly created device object
     */
    @Override
    public Device addDevice(CreateDeviceDto createDeviceDto) {

        Device device = Device.builder()
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        log.info("New {} {} has been added", createDeviceDto.getBrand(), createDeviceDto.getName());
        return deviceRepository.save(device);

    }

    /**
     * This method retrieves a device by the id from DB
     *
     * @param id the unique identifier for the device
     * @return an Optional which contains the device if found or return empty.
     */
    @Override
    public Optional<Device> getDeviceById(Long id) {

        log.info("Fetching device with id: {}", id);
        return deviceRepository.findById(id);

    }

    /**
     * This method retrieves all the device details from DB
     *
     * @return a list which contains all device details
     */
    @Override
    public List<Device> getAllDevices() {

        log.info("Fetching all devices");
        return deviceRepository.findAll();

    }

    /**
     * This method updates an existing device fully and partially in the DB
     *
     * @param id              the unique identifier for the device
     * @param updateDeviceDto contains the updated information for the device
     * @return the device object which is updated
     * @throws DeviceNotFoundException if there is no device found with the specific id
     */

    @Override
    public Device updateDevice(Long id, UpdateDeviceDto updateDeviceDto) {

        log.info("Updating device with id: {}", id);

        Optional<Device> optionalDevice = deviceRepository.findById(id);

        if (optionalDevice.isPresent()) {

            Device device = optionalDevice.get();
            log.info("Found device: {}", device);

            boolean isUpdated = false;

            if (updateDeviceDto.getName() != null && !updateDeviceDto.getName().equals(device.getName())) {

                log.info("Updating device name from '{}' to '{}'", device.getName(), updateDeviceDto.getName());
                device.setName(updateDeviceDto.getName());
                isUpdated = true;

            }

            if (updateDeviceDto.getBrand() != null && !updateDeviceDto.getBrand().equals(device.getBrand())) {

                log.info("Updating device brand from '{}' to '{}'", device.getBrand(), updateDeviceDto.getBrand());
                device.setBrand(updateDeviceDto.getBrand());
                isUpdated = true;
            }

            if (isUpdated) {

                Device updatedDevice = deviceRepository.save(device);
                log.info("Device updated successfully: {}", updatedDevice);
                return updatedDevice;

            } else {

                log.info("No updates were made to the device with id: {}", id);
                return device;

            }

        } else {

            String errorMessage = "Device not found with Id: " + id;
            log.error(errorMessage);
            throw new DeviceNotFoundException(errorMessage);

        }
    }

    /**
     * This method deletes a device by its by id in DB
     *
     * @param id of the device which needs to be deleted
     */
    @Override
    public void deleteDevice(Long id) {
        log.info("Deleting device with id: {}", id);
        deviceRepository.deleteById(id);
    }


    /**
     * This method searches for the devices under a specific brand
     *
     * @param brand the name to be searched
     * @return a list of devices associated with the particular brand
     */
    @Override
    public List<Device> searchDeviceByBrand(String brand) {
        log.info("Searching device by brand: {}", brand);
        return deviceRepository.findByBrand(brand);
    }
}

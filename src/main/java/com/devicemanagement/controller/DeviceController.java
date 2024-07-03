package com.devicemanagement.controller;

import com.devicemanagement.dto.CreateDeviceDto;
import com.devicemanagement.dto.UpdateDeviceDto;
import com.devicemanagement.entity.Device;
import com.devicemanagement.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * This class has the REST API endpoints for managing devices.
 * It provides methods to add, retrieve, update, delete, and search devices.
 *
 * @author Lalit Biswal
 */

@Tag(name = "Device APIs")
@Slf4j
@RestController
@RequestMapping("/api/devices")
@AllArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    /**
     * This method is used for adding a new device based on the CreateDeviceDto object.
     *
     * @param createDeviceDto the DTO containing information for device to be created
     * @return a ResponseEntity which contains the newly created Device object having status code 200 (OK)
     * @throws IllegalArgumentException if the CreateDeviceDto object is invalid, as if name or brand is null or empty
     **/

    @Operation(summary = "Add device")
    @PostMapping("/add-device")
    public ResponseEntity<Device> addDevice(@RequestBody @Valid CreateDeviceDto createDeviceDto) {

        log.info("Request to add device : {} {}", createDeviceDto.getBrand(), createDeviceDto.getName());

        Device newDevice = deviceService.addDevice(createDeviceDto);
        return ResponseEntity.ok(newDevice);

    }

    /**
     * This method is used to get a device details by passing the id of that device
     *
     * @param id the unique identifier for a device
     * @return a  ResponseEntity containing a devices object if the device is present, else no content will be displayed
     **/
    @Operation(summary = "Get device by identifier")
    @GetMapping("/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long id) {

        log.info("Request to get device for id: {}", id);

        Optional<Device> device = deviceService.getDeviceById(id);

        return device.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());

    }

    /**
     * This method is used to get all device details
     *
     * @return a  ResponseEntity containing which contains all the device details.
     **/
    @Operation(summary = "List all devices")
    @GetMapping("/all-devices")
    public ResponseEntity<List<Device>> getAllDevices() {

        log.info("Request to get all devices initiated");

        List<Device> devices = deviceService.getAllDevices();
        return ResponseEntity.ok(devices);

    }


    /**
     * This method is used to update a device object based on the input from UpdateDeviceDto object. Updating a device can be full or partial
     *
     * @param updateDeviceDto the DTO containing information for device to be updated
     * @return a  ResponseEntity containing the updated device details
     **/
    @Operation(summary = "Update device")
    @PutMapping("/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long id, @RequestBody UpdateDeviceDto updateDeviceDto) {

        log.info("Request to update device for id: {}", id);

        Device updatedDevice = deviceService.updateDevice(id, updateDeviceDto);
        return ResponseEntity.ok(updatedDevice);

    }

    /**
     * This method is used to delete a device details by passing the id of that device
     *
     * @param id the unique identifier for a device
     * @return a  ResponseEntity containing no-body as the device is deleted and status code 200
     **/
    @Operation(summary = "Delete a device")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {

        log.info("Request to delete device for id: {}", id);

        deviceService.deleteDevice(id);
        return ResponseEntity.ok().build();

    }

    /**
     * This method is used to search all device details associated with a particular brand name
     *
     * @param brand is the brand under which may device can be found
     * @return a  ResponseEntity containing list of devices under a specific brand name.
     **/
    @Operation(summary = "Search a device by brand")
    @GetMapping("/search")
    public ResponseEntity<List<Device>> searchDevicesByBrand(@RequestParam String brand) {

        List<Device> devices = deviceService.searchDeviceByBrand(brand);
        return ResponseEntity.ok(devices);

    }


}

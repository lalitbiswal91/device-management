package com.devicemanagement.service.impl;

import com.devicemanagement.dto.CreateDeviceDto;
import com.devicemanagement.dto.UpdateDeviceDto;
import com.devicemanagement.entity.Device;
import com.devicemanagement.exception.DeviceNotFoundException;
import com.devicemanagement.repository.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void TestAddDevice() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        when(deviceRepository.save(any(Device.class))).thenReturn(device);

        Device newDevice = deviceService.addDevice(createDeviceDto);

        assertNotNull(newDevice);
        assertEquals(createDeviceDto.getName(), newDevice.getName());
        assertEquals(createDeviceDto.getBrand(), newDevice.getBrand());

    }


    @Test
    void TestGetDeviceById() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .id(1L)
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        Optional<Device> presentDevice = deviceService.getDeviceById(1L);
        assertTrue(presentDevice.isPresent());
        assertEquals(1L, presentDevice.get().getId());

    }

    @Test
    void TestGetAllDevices() {

        CreateDeviceDto deviceDtoOne = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device deviceOne = Device.builder()
                .id(1L)
                .name(deviceDtoOne.getName())
                .brand(deviceDtoOne.getBrand())
                .creationTime(LocalDateTime.now())
                .build();


        CreateDeviceDto deviceDtoTwo = CreateDeviceDto.builder()
                .name("Galaxy S 24")
                .brand("Samsung")
                .build();

        Device deviceTwo = Device.builder()
                .id(1L)
                .name(deviceDtoTwo.getName())
                .brand(deviceDtoTwo.getBrand())
                .creationTime(LocalDateTime.now())
                .build();


        List<Device> allDevices = new ArrayList<>();
        allDevices.add(deviceOne);
        allDevices.add(deviceTwo);

        when(deviceRepository.findAll()).thenReturn(allDevices);

        List<Device> devices = deviceService.getAllDevices();

        assertNotNull(devices);
        assertEquals(2, devices.size());


    }

    @Test
    void TestUpdateDevice_UpdateNameAndBrand() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .id(1L)
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();


        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .name("Iphone 16 Pro")
                .brand("Apple")
                .build();


        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);


        Device updatedDevice = deviceService.updateDevice(1L, updateDeviceDto);

        assertNotNull(updatedDevice);
        assertEquals(updateDeviceDto.getName(), updatedDevice.getName());
        assertEquals(updateDeviceDto.getBrand(), updatedDevice.getBrand());

    }

    @Test
    void TestUpdateDevice_UpdateName() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .id(1L)
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();


        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .name("Pixel 6")
                .build();


        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);


        Device updatedDevice = deviceService.updateDevice(1L, updateDeviceDto);


        assertEquals(updateDeviceDto.getName(), updatedDevice.getName());
        verify(deviceRepository, times(1)).save(device);

    }

    @Test
    void TestUpdateDevice_UpdateBrand() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .id(1L)
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();


        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .brand("Apple")
                .build();


        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));
        when(deviceRepository.save(any(Device.class))).thenReturn(device);


        Device updatedDevice = deviceService.updateDevice(1L, updateDeviceDto);

        assertEquals(updateDeviceDto.getBrand(), updatedDevice.getBrand());
        verify(deviceRepository, times(1)).save(device);

    }


    @Test
    void TestUpdateDevice_DeviceNotFound() {

        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .name("Iphone 16 Pro Max")
                .brand("Apple")
                .build();

        when(deviceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DeviceNotFoundException.class, () -> deviceService.updateDevice(1L, updateDeviceDto));

    }


    @Test
    void TestUpdateDevice_NoUpdate() {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device device = Device.builder()
                .id(1L)
                .name(createDeviceDto.getName())
                .brand(createDeviceDto.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();


        when(deviceRepository.findById(1L)).thenReturn(Optional.of(device));

        Device updatedDevice = deviceService.updateDevice(1L, updateDeviceDto);

        assertEquals(device.getName(), updatedDevice.getName());
        assertEquals(device.getBrand(), updatedDevice.getBrand());
        verify(deviceRepository, never()).save(any(Device.class));

    }


    @Test
    void TestDeleteDevice() {
        doNothing().when(deviceRepository).deleteById(1L);
        deviceService.deleteDevice(1L);
        verify(deviceRepository, times(1)).deleteById(1L);
    }


    @Test
    void TestSearchDeviceByBrand() {

        CreateDeviceDto deviceDtoOne = CreateDeviceDto.builder()
                .name("Pixel 8 Pro")
                .brand("Google")
                .build();

        Device deviceOne = Device.builder()
                .id(1L)
                .name(deviceDtoOne.getName())
                .brand(deviceDtoOne.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        CreateDeviceDto deviceDtoTwo = CreateDeviceDto.builder()
                .name("Galaxy S 24")
                .brand("Samsung")
                .build();

        Device deviceTwo = Device.builder()
                .id(1L)
                .name(deviceDtoTwo.getName())
                .brand(deviceDtoTwo.getBrand())
                .creationTime(LocalDateTime.now())
                .build();

        List<Device> allDevices = new ArrayList<>();
        allDevices.add(deviceOne);
        allDevices.add(deviceTwo);

        when(deviceRepository.findByBrand("Samsung")).thenReturn(allDevices);

        List<Device> existingDevices = deviceService.searchDeviceByBrand("Samsung");

        assertNotNull(existingDevices);
        assertEquals(2, existingDevices.size());

    }


}

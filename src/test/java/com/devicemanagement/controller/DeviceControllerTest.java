package com.devicemanagement.controller;

import com.devicemanagement.dto.CreateDeviceDto;
import com.devicemanagement.dto.UpdateDeviceDto;
import com.devicemanagement.entity.Device;
import com.devicemanagement.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
public class DeviceControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    private Device device;


    @BeforeEach
    void setUp() {
        device = Device.builder()
                .id(1L)
                .name("IPhone")
                .brand("Apple")
                .creationTime(LocalDateTime.now())
                .build();
    }

    @Test
    void TestAddDevice() throws Exception {

        CreateDeviceDto createDeviceDto = CreateDeviceDto.builder()
                .name("IPhone")
                .brand("Apple")
                .build();

        Mockito.when(deviceService.addDevice(any(CreateDeviceDto.class))).thenReturn(device);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/devices/add-device")
                        .content("{\"name\":\"IPhone\",\"brand\":\"Apple\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(device.getName()))
                .andExpect(jsonPath("$.brand").value(device.getBrand()));
    }


    @Test
    void TestGetDeviceById() throws Exception {

        Mockito.when(deviceService.getDeviceById(1L)).thenReturn(Optional.of(device));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/devices/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(device.getId()))
                .andExpect(jsonPath("$.name").value(device.getName()))
                .andExpect(jsonPath("$.brand").value(device.getBrand()));
    }

    @Test
    void TestGetAllDevices() throws Exception {

        Device msDevice = Device.builder()
                .id(1L)
                .name("Surface")
                .brand("Microsoft")
                .creationTime(LocalDateTime.now())
                .build();

        List<Device> devices = Arrays.asList(device, msDevice);
        Mockito.when(deviceService.getAllDevices()).thenReturn(devices);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/devices/all-devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(device.getId()))
                .andExpect(jsonPath("$[0].name").value(device.getName()))
                .andExpect(jsonPath("$[0].brand").value(device.getBrand()));
    }


    @Test
    void TestUpdateDevice() throws Exception {

        UpdateDeviceDto updateDeviceDto = UpdateDeviceDto.builder()
                .name("S 24 Ultra")
                .brand("Samsung")
                .build();

        device = Device.builder()
                .name(updateDeviceDto.getName())
                .brand(updateDeviceDto.getBrand())
                .build();

        Mockito.when(deviceService.updateDevice(anyLong(), any(UpdateDeviceDto.class))).thenReturn(device);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/devices/1")
                        .content("{\"name\":\"S 24 Ultra\",\"brand\":\"Samsung\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(device.getName()))
                .andExpect(jsonPath("$.brand").value(device.getBrand()));
    }


    @Test
    void TestDeleteDevice() throws Exception {

        Mockito.doNothing().when(deviceService).deleteDevice(1L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/devices/1"))
                .andExpect(status().isOk());

    }


    @Test
    void TestSearchByBrand() throws Exception {

        String brand = "Apple";

        Device msDevice = Device.builder()
                .id(1L)
                .name("Surface")
                .brand("Microsoft")
                .creationTime(LocalDateTime.now())
                .build();

        List<Device> devices = Arrays.asList(device, msDevice);

        Mockito.when(deviceService.searchDeviceByBrand(brand)).thenReturn(devices);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/devices/search")
                        .param("brand", brand)).andExpect(status().isOk());
    }


}

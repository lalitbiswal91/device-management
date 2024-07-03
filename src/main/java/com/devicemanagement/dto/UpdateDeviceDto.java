package com.devicemanagement.dto;

import lombok.Builder;
import lombok.Data;


/**
 * This is the dto class for updating existing devices.
 * */

@Data
@Builder
public class UpdateDeviceDto {
    private String name;
    private String brand;
}

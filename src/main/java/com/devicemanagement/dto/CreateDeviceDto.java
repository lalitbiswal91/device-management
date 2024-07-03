package com.devicemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Builder;


/**
 * This is the dto class for creating new devices.
 * */

@Data
@Builder
public class CreateDeviceDto {

    @NotBlank(message = "Device name is mandatory and cannot be empty or null")
    private String name;

    @NotBlank(message = "Device brand is mandatory and cannot be empty or null")
    private String brand;

}

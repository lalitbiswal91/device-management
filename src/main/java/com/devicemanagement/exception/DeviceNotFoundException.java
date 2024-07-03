package com.devicemanagement.exception;

/**
 * This is the custom exception class for device management service.
 * This exception is thrown when a device is not found in the DB
 * */

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(String message) {
        super(message);
    }
}

package com.devicemanagement.advice;

import com.devicemanagement.exception.DeviceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


/**
* This is the Controller Advice class for device-management application which handles excepts thrown by the application
 * It uses @RestControllerAdvice to cover the controller classes
 * The logging is done by @Slf4j
 *
 * @author Lalit Biswal
* */

@Slf4j
@RestControllerAdvice
public class DeviceManagementExceptionHandler {

    /**
     * Handles validation errors
     * @param ex thrown a MethodArgumentNotValidException when the validation fails
     * @return a ResponseEntity which contains the errors and status code  400
    * */

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleInvalidArgumentException(MethodArgumentNotValidException ex) {

        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        log.error("Validation errors for request: {}", errorMap);
        return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);

    }

    /**
     * Handles DeviceNotFoundException exception
     * @param ex thrown a DeviceNotFoundException when there is no device with an id
     * @return a ResponseEntity which contains the errors and status code 404
     * */

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DeviceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleDeviceNotFoundException(DeviceNotFoundException ex) {

        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage : ", ex.getMessage());

        log.error("Not found: {}", ex.getMessage());
        return new ResponseEntity<>(errorMap, HttpStatus.NOT_FOUND);

    }

    /**
     * Handles all other exception and errors
     * @param ex thrown an Exception when there generic exception or any error
     * @return a ResponseEntity which contains the errors and status code 500
     * */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {

        log.error("Unexpected error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

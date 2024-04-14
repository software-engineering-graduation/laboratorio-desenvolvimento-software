package com.labssoft.roteiro01.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFieldFormatException.class)
    public Map<String, String> handleInvalidFieldFormatException(InvalidFieldFormatException exception) {
        Map<String, String> map =  new HashMap<>();
        map.put("errorMessage", exception.getMessage());
        map.put("errorClass", exception.getClass().getSimpleName());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return map;
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleException(Exception exception) {
        Map<String, String> map = new HashMap<>();
        ((BindException) exception).getBindingResult().getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        map.put("errorMessage", exception.getMessage());
        map.put("errorClass", exception.getClass().getSimpleName());
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("status", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));

        return map;
    }
}

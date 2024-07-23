package com.BIGT.security.utils;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String > globalExceptionHandler(MethodArgumentNotValidException e) {
        Map<String,String> errorDetails = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) -> {
            errorDetails.put(error.getField(), error.getDefaultMessage());

        });
        errorDetails.put("responseCode", "99");
        errorDetails.put("message", "validation failed");
        return errorDetails;


    }

}

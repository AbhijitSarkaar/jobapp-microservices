package com.microservices.company.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> customMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> customResourceNotFoundExceptionHandler(CustomResourceNotFoundException e) {
        return new ResponseEntity<>(new CustomErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<CustomErrorResponse> customRuntimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(new CustomErrorResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

package com.microservice.review.exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<APIResponse> customNoResourceFoundExceptionHandler(NoResourceFoundException e) {
        return new ResponseEntity<>(new APIResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> customMethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> map = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(item -> {
            map.put(item.getField(), item.getDefaultMessage());
        });

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<APIResponse> customRuntimeExceptionHandler(RuntimeException e) {
        return new ResponseEntity<>(new APIResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomResourceNotFoundException.class)
    public ResponseEntity<APIResponse> customResourceNotFoundExceptionHandler(CustomResourceNotFoundException e) {
        return new ResponseEntity<>(new APIResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }

}

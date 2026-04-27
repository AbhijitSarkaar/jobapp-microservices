package com.microservices.job.exception;


public class CustomResourceNotFoundException extends RuntimeException {
    private String entity;
    private String property;
    private Long value;
    public CustomResourceNotFoundException(String entity, String property, Long value) {
        String message= entity + " with " + property + " = " + value + " not found";
        super(message);
    }
}

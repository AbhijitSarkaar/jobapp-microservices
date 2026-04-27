package com.microservices.company.exception;

public class CustomResourceNotFoundException extends RuntimeException {
    private String entity;
    private String property;
    private String id;

    public CustomResourceNotFoundException(String entity, String property, String value) {
        super(entity + " with " + property + " = " + value + " not found");
    }
}

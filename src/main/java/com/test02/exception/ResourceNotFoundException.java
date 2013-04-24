package com.test02.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("The required resource is not present");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}

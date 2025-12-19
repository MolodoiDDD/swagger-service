package com.example.swagger_service.exception;

public class CitizenAlreadyExitsException extends RuntimeException {
    public CitizenAlreadyExitsException(String message) {
        super(message);
    }
}

package com.example.top.exception;

public class UnknownIdException extends RuntimeException {
    public UnknownIdException(String message) {
        super(message);
    }
}

package com.example.top.exception;

public class DuplicateRoleNameException extends RuntimeException {

    public DuplicateRoleNameException(String message) {
        super(message);
    }
}

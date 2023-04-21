package com.example.top.exception;

public class NoSuchAccount extends RuntimeException {

    public NoSuchAccount(String message) {
        super(message);
    }

}

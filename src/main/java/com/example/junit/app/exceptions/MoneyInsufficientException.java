package com.example.junit.app.exceptions;

public class MoneyInsufficientException extends RuntimeException {
    public MoneyInsufficientException(String message) {
        super(message);
    }
}

package com.stocktracker.base.entity.exception;

public class ApplicationSetupException extends RuntimeException {
    public ApplicationSetupException(String message) {
        super(message);
    }
    public ApplicationSetupException(String message, Throwable cause) {
        super(message, cause);
    }
}

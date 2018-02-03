package com.bookcatalog.registration.validation;

public class InvalidTokenException extends Throwable {

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}

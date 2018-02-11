package com.bookcatalog.registration.validation;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token is invalid.");
    }
}

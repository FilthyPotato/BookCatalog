package com.bookcatalog.registration.validation.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Token is invalid.");
    }
}

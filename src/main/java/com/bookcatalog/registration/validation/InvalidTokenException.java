package com.bookcatalog.registration.validation;

public class InvalidTokenException extends Throwable {

    public InvalidTokenException() {
        super("Token is invalid.");
    }
}

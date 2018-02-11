package com.bookcatalog.registration.validation.exceptions;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super("Email is already taken.");
    }
}

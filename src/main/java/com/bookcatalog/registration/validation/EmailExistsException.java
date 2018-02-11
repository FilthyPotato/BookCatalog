package com.bookcatalog.registration.validation;

public class EmailExistsException extends RuntimeException {

    public EmailExistsException() {
        super("Email is already taken.");
    }
}

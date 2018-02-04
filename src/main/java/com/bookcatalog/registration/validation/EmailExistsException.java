package com.bookcatalog.registration.validation;

public class EmailExistsException extends Throwable {

    public EmailExistsException() {
        super("Email is already taken.");
    }
}

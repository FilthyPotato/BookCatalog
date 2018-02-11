package com.bookcatalog.registration.validation.exceptions;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException() {
        super("Username is already taken.");
    }
}

package com.bookcatalog.registration.validation;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException() {
        super("Username is already taken.");
    }
}

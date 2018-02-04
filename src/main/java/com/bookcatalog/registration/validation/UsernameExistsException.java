package com.bookcatalog.registration.validation;

public class UsernameExistsException extends Throwable {

    public UsernameExistsException() {
        super("Username is already taken.");
    }
}

package com.bookcatalog.registration.validation;

public class UsernameExistsException extends Throwable {

    public UsernameExistsException() {
    }

    public UsernameExistsException(String message) {
        super(message);
    }
}

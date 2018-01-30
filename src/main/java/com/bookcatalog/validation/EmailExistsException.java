package com.bookcatalog.validation;

public class EmailExistsException extends Throwable {

    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        super(message);
    }
}

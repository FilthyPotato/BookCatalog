package com.bookcatalog;

public class InvalidTokenException extends Throwable {

    public InvalidTokenException() {
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}

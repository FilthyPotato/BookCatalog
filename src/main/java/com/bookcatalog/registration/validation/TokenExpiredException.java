package com.bookcatalog.registration.validation;

public class TokenExpiredException extends Throwable {

    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}

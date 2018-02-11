package com.bookcatalog.registration.validation;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token has expired.");
    }
}

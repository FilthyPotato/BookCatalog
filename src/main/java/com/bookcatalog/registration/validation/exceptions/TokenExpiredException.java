package com.bookcatalog.registration.validation.exceptions;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token has expired.");
    }
}

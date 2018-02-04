package com.bookcatalog.registration.validation;

public class TokenExpiredException extends Throwable {

    public TokenExpiredException() {
        super("Token has expired.");
    }
}

package com.bookcatalog;

public class TokenExpiredException extends Throwable {

    public TokenExpiredException() {
    }

    public TokenExpiredException(String message) {
        super(message);
    }
}

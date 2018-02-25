package com.bookcatalog.validation.exceptions;

public class ShelfDoesNotExistException extends RuntimeException{

    public ShelfDoesNotExistException(Long id) {
        super(String.format("Shelf %d doesn't exist.", id));
    }
}

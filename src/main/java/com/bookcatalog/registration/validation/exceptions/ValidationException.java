package com.bookcatalog.registration.validation.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class ValidationException extends RuntimeException {
    private BindingResult bindingResult;

    public ValidationException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
}

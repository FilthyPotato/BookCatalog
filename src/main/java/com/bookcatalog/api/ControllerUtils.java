package com.bookcatalog.api;

import com.bookcatalog.validation.ValidationException;
import org.springframework.validation.BindingResult;

public class ControllerUtils {
    public static void throwValidationExceptionIfErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }
}

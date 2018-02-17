package com.bookcatalog;

import com.bookcatalog.validation.FieldError;
import com.bookcatalog.validation.ValidationErrorDto;
import org.springframework.validation.BindingResult;

public class ValidationUtils {

    public static ValidationErrorDto buildFieldErrors(BindingResult bindingResult) {
        ValidationErrorDto dto = new ValidationErrorDto();
        bindingResult.getFieldErrors()
                .forEach(f -> dto.addFieldError(new FieldError(f.getField(), f.getDefaultMessage())));
        return dto;
    }
}

package com.bookcatalog.exceptionhandlers;

import com.bookcatalog.ValidationUtils;
import com.bookcatalog.validation.ValidationErrorDto;
import com.bookcatalog.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationErrorDto> handle(ValidationException ve) {
        ValidationErrorDto validationErrorDto = ValidationUtils.buildFieldErrors(ve.getBindingResult());
        return new ResponseEntity<>(validationErrorDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}

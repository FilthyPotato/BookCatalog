package com.bookcatalog.registration.controllers.exceptionhandlers;

import com.bookcatalog.registration.model.ValidationErrorDto;
import com.bookcatalog.registration.validation.FieldError;
import com.bookcatalog.registration.validation.exceptions.InvalidTokenException;
import com.bookcatalog.registration.validation.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler({TokenExpiredException.class, InvalidTokenException.class})
    public ResponseEntity<ValidationErrorDto> handle(RuntimeException e) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        validationErrorDto.addFieldError(new FieldError("token", e.getMessage()));
        return new ResponseEntity<>(validationErrorDto, HttpStatus.BAD_REQUEST);
    }
}

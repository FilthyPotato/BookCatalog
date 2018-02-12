package com.bookcatalog.registration.controllers.exceptionhandlers;

import com.bookcatalog.registration.model.ValidationErrorDto;
import com.bookcatalog.registration.validation.FieldError;
import com.bookcatalog.registration.validation.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class UserDtoValidationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ValidationErrorDto> handle(ValidationException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
        List<ObjectError> objectErrors = bindingResult.getGlobalErrors();

        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        fieldErrors.forEach(f -> validationErrorDto.addFieldError(new FieldError(f.getField(), f.getDefaultMessage())));
        objectErrors.forEach(o -> {
            if (Arrays.asList(o.getCodes()).contains("PasswordMatches")) {
                validationErrorDto.addFieldError(new FieldError("password", o.getDefaultMessage()));
                validationErrorDto.addFieldError(new FieldError("confirmPassword", o.getDefaultMessage()));
            }
        });

        return new ResponseEntity<>(validationErrorDto, null, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}

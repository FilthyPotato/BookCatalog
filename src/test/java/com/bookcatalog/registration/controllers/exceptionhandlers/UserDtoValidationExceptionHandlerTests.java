package com.bookcatalog.registration.controllers.exceptionhandlers;

import com.bookcatalog.registration.model.UserRegistrationDto;
import com.bookcatalog.registration.model.ValidationErrorDto;
import com.bookcatalog.registration.validation.FieldError;
import com.bookcatalog.registration.validation.exceptions.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserDtoValidationExceptionHandlerTests {
    private UserDtoValidationExceptionHandler userDtoValidationExceptionHandler;
    private String notRelevant = "test";
    private BindingResult binding;
    private UserRegistrationDto notRelevantDto = new UserRegistrationDto();

    @Before
    public void setUp() {
        userDtoValidationExceptionHandler = new UserDtoValidationExceptionHandler();
        binding = new BeanPropertyBindingResult(notRelevantDto, notRelevant);
    }

    @Test
    public void responseContainsAllFieldErrors() {
        FieldError[] fieldErrors = new FieldError[]{
                new FieldError("username", notRelevant),
                new FieldError("email", notRelevant),
                new FieldError("password", notRelevant),
                new FieldError("confirmPassword", notRelevant),
        };
        for (FieldError fieldError : fieldErrors) {
            binding.rejectValue(fieldError.getField(), notRelevant, fieldError.getMessage());
        }

        ValidationException ve = new ValidationException(binding);
        ResponseEntity<ValidationErrorDto> response = userDtoValidationExceptionHandler.handle(ve);

        assertThat(response.getBody().getFieldErrors(), hasItems(fieldErrors));
    }

    @Test
    public void whenPasswordMatchesErrorThenContainsPasswordAndConfirmPasswordFieldErrors() {
        binding.reject("PasswordMatches", notRelevant);
        FieldError[] fieldErrors = new FieldError[]{
                new FieldError("password", notRelevant),
                new FieldError("confirmPassword", notRelevant),
        };

        ValidationException ve = new ValidationException(binding);
        ResponseEntity<ValidationErrorDto> response = userDtoValidationExceptionHandler.handle(ve);

        assertThat(response.getBody().getFieldErrors(), hasItems(fieldErrors));
    }

    @Test
    public void statusCodeIsUnprocessableEntity() {
        ValidationException ve = new ValidationException(binding);
        ResponseEntity<ValidationErrorDto> response = userDtoValidationExceptionHandler.handle(ve);
        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}

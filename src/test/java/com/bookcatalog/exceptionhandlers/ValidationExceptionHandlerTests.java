package com.bookcatalog.exceptionhandlers;

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.validation.FieldError;
import com.bookcatalog.validation.ValidationErrorDto;
import com.bookcatalog.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

public class ValidationExceptionHandlerTests {

    private ValidationExceptionHandler exceptionHandler;
    @Mock
    private BindingResult bindingResult;

    @Before
    public void setUp() {
        exceptionHandler = new ValidationExceptionHandler();
        bindingResult = new BeanPropertyBindingResult(mock(ShelfDto.class), "");    //TODO: how to get rid of ShelfDto.class?
    }

    @Test
    public void responseContainsAllFieldErrors() {
        FieldError expected = new FieldError("name", "This field cannot be empty.");
        bindingResult.rejectValue(expected.getField(), "", expected.getMessage());

        ResponseEntity<ValidationErrorDto> response = exceptionHandler.handle(new ValidationException(bindingResult));
        List<FieldError> fieldErrors = response.getBody().getFieldErrors();

        assertThat(fieldErrors, hasSize(1));
        assertThat(fieldErrors, hasItems(expected));
    }

    @Test
    public void statusCodeIsUnprocessableEntity() {
        ResponseEntity<ValidationErrorDto> response = exceptionHandler.handle(new ValidationException(bindingResult));

        assertThat(response.getStatusCode(), is(HttpStatus.UNPROCESSABLE_ENTITY));
    }
}

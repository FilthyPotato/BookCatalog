package com.bookcatalog.registration.controllers.exceptionhandlers; 

import com.bookcatalog.registration.model.ValidationErrorDto;
import com.bookcatalog.registration.validation.FieldError;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenExceptionHandlerTests {
    private TokenExceptionHandler tokenExceptionHandler;
    @Mock
    private RuntimeException runtimeException;
    private String message;

    @Before
    public void setUp() {
        tokenExceptionHandler = new TokenExceptionHandler();
    }

    @Test
    public void responseContainsTokenField() {
        when(runtimeException.getMessage()).thenReturn(message);
        ResponseEntity<ValidationErrorDto> response = tokenExceptionHandler.handle(runtimeException);
        ValidationErrorDto body = response.getBody();

        assertThat(body.getFieldErrors(), hasItems(new FieldError("token", message)));
    }

    @Test
    public void responseCodeIsBadRequest() {
        ResponseEntity<ValidationErrorDto> response = tokenExceptionHandler.handle(runtimeException);

        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }
}

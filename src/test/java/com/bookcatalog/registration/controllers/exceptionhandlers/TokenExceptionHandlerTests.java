package com.bookcatalog.registration.controllers.exceptionhandlers;

import com.bookcatalog.validation.FieldError;
import com.bookcatalog.validation.ValidationErrorDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
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

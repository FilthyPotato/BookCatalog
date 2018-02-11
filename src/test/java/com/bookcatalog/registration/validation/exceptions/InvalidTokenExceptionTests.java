package com.bookcatalog.registration.validation.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class InvalidTokenExceptionTests {

    @Test
    public void messageIsCorrect() {
        InvalidTokenException invalidTokenException = new InvalidTokenException();
        assertThat(invalidTokenException.getMessage(), is("Token is invalid."));
    }
}

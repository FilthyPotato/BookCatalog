package com.bookcatalog.registration.validation.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TokenExpiredExceptionTests {

    @Test
    public void messageIsCorrect() {
        TokenExpiredException tokenExpiredException = new TokenExpiredException();
        assertThat(tokenExpiredException.getMessage(), is("Token has expired."));
    }
}

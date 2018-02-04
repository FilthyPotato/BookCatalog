package com.bookcatalog.registration.validation;

import org.junit.Test; 
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class TokenExpiredExceptionTests {

    @Test
    public void messageIsCorrect() {
        TokenExpiredException tokenExpiredException = new TokenExpiredException();
        assertThat(tokenExpiredException.getMessage(), is("Token has expired."));
    }
}

package com.bookcatalog.registration.validation; 

import org.junit.Test; 
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class InvalidTokenExceptionTests {

    @Test
    public void messageIsCorrect() {
        InvalidTokenException invalidTokenException = new InvalidTokenException();
        assertThat(invalidTokenException.getMessage(), is("Token is invalid."));
    }
}

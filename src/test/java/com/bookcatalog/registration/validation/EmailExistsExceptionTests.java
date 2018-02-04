package com.bookcatalog.registration.validation; 

import org.junit.Test; 
import org.junit.Before;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class EmailExistsExceptionTests {

    @Test
    public void messageIsCorrect() {
        EmailExistsException emailExistsException = new EmailExistsException();
        assertThat(emailExistsException.getMessage(), is("Email is already taken."));
    }
}

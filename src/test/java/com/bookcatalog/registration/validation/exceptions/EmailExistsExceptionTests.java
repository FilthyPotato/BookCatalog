package com.bookcatalog.registration.validation.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EmailExistsExceptionTests {

    @Test
    public void messageIsCorrect() {
        EmailExistsException emailExistsException = new EmailExistsException();
        assertThat(emailExistsException.getMessage(), is("Email is already taken."));
    }
}

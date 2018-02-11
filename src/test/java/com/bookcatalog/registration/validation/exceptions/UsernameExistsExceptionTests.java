package com.bookcatalog.registration.validation.exceptions;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UsernameExistsExceptionTests {

    @Test
    public void messageIsCorrect() {
        UsernameExistsException usernameExistsException = new UsernameExistsException();
        assertThat(usernameExistsException.getMessage(), is("Username is already taken."));
    }
}

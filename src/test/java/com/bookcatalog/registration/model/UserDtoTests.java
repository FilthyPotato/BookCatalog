package com.bookcatalog.registration.model;

import com.bookcatalog.registration.validation.EmailNoIntranet;
import com.bookcatalog.registration.validation.PasswordMatches;
import org.junit.Test;

import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;

import static com.bookcatalog.AnnotationUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class UserDtoTests {

    @Test
    public void usernameHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation(UserDto.class,"username");
    }

    @Test
    public void passwordHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation(UserDto.class,"password");
    }

    @Test
    public void confirmPasswordHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation(UserDto.class,"confirmPassword");
    }

    @Test
    public void emailHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation(UserDto.class,"email");
    }

    @Test
    public void emailHasEmailNoIntranetAnnotation() throws NoSuchFieldException {
        hasAnnotation(UserDto.class,"email", EmailNoIntranet.class);
    }

    @Test
    public void usernameHasSizeAnnotation() throws NoSuchFieldException {
        hasAnnotation(UserDto.class,"username", Size.class);
    }

    @Test
    public void usernameSizeIsMin2() throws NoSuchFieldException {
        Annotation size = getAnnotation(UserDto.class,"username", Size.class);
        assertThat(((Size) size).min(), is(2));
    }

    @Test
    public void userRegistrationDtoHasPasswordMatchesAnnotation() {
        assertThat(UserDto.class.getAnnotation(PasswordMatches.class), notNullValue());
    }
}

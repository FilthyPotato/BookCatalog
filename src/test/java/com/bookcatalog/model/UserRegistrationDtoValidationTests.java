package com.bookcatalog.model;

import com.bookcatalog.validation.EmailNoIntranet;
import com.bookcatalog.validation.PasswordMatches;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;


public class UserRegistrationDtoValidationTests {

    @Test
    public void usernameHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation("username");
    }

    @Test
    public void passwordHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation("password");
    }

    @Test
    public void confirmPasswordHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation("confirmPassword");
    }

    @Test
    public void emailHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation("email");
    }

    @Test
    public void emailHasEmailNoIntranetAnnotation() throws NoSuchFieldException {
        hasAnnotation("email", EmailNoIntranet.class);
    }

    @Test
    public void usernameHasSizeAnnotation() throws NoSuchFieldException {
        hasAnnotation("username", Size.class);
    }

    @Test
    public void usernameSizeIsMin2() throws NoSuchFieldException {
        Annotation size = getAnnotation("username", Size.class);
        assertThat(((Size) size).min(), is(2));
    }

    @Test
    public void userRegistrationDtoHasPasswordMatchesAnnotation(){
        assertThat(UserRegistrationDto.class.getAnnotation(PasswordMatches.class), notNullValue());
    }

    private <T extends Annotation> void hasAnnotation(String fieldName, Class<T> annotationClass) throws NoSuchFieldException {
        Annotation annotation = getAnnotation(fieldName, annotationClass);
        assertThat(annotation, notNullValue());
    }

    private <T extends Annotation> Annotation getAnnotation(String fieldName, Class<T> annotationClass) throws NoSuchFieldException {
        Field field = UserRegistrationDto.class.getDeclaredField(fieldName);
        return field.getAnnotation(annotationClass);
    }

    private void hasNotEmptyAnnotation(String fieldName) throws NoSuchFieldException {
        hasAnnotation(fieldName, NotEmpty.class);
    }

}

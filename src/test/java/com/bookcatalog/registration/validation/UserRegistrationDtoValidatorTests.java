package com.bookcatalog.registration.validation;

import com.bookcatalog.registration.UserService;
import com.bookcatalog.registration.model.UserRegistrationDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationDtoValidatorTests { 
    private UserRegistrationDtoValidator userRegistrationDtoValidator;
    @Mock
    private UserService userService;
    @Mock
    private UserRegistrationDto userRegistrationDto;
    @Before
    public void setUp() {
        userRegistrationDtoValidator = new UserRegistrationDtoValidator(userService);
    }

    @Test
    public void whenUsernameExistsThenFieldErrorIsSet() {
        when(userService.usernameExists("test")).thenReturn(true);
        when(userRegistrationDto.getUsername()).thenReturn("test");
        Errors errors = new BeanPropertyBindingResult(userRegistrationDto, "userRegistrationDto");

        userRegistrationDtoValidator.validate(userRegistrationDto, errors);

        assertTrue(errors.hasFieldErrors("username"));
    }

    @Test
    public void whenEmailExistsThenFieldErrorIsSet() {
        when(userService.emailExists("test")).thenReturn(true);
        when(userRegistrationDto.getEmail()).thenReturn("test");
        Errors errors = new BeanPropertyBindingResult(userRegistrationDto, "userRegistrationDto");

        userRegistrationDtoValidator.validate(userRegistrationDto, errors);

        assertTrue(errors.hasFieldErrors("email"));
    }
}

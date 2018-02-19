package com.bookcatalog.registration.validation;

import com.bookcatalog.registration.UserService;
import com.bookcatalog.registration.model.UserDto;
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
public class UserDtoValidatorTests {
    private UserDtoValidator userDtoValidator;
    @Mock
    private UserService userService;
    @Mock
    private UserDto userDto;
    @Before
    public void setUp() {
        userDtoValidator = new UserDtoValidator(userService);
    }

    @Test
    public void whenUsernameExistsThenFieldErrorIsSet() {
        when(userService.usernameExists("test")).thenReturn(true);
        when(userDto.getUsername()).thenReturn("test");
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("username"));
    }

    @Test
    public void whenEmailExistsThenFieldErrorIsSet() {
        when(userService.emailExists("test")).thenReturn(true);
        when(userDto.getEmail()).thenReturn("test");
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void whenPasswordsDoNotMatchThenFieldErrorIsSet() {
        UserDto userDto = new UserDto();
        userDto.setPassword("password");
        userDto.setConfirmPassword("wrongPassword");
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("password"));
        assertTrue(errors.hasFieldErrors("confirmPassword"));
    }
}

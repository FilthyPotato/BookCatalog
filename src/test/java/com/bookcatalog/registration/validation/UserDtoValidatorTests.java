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

    private UserDto userDto;
    @Before
    public void setUp() {
        userDtoValidator = new UserDtoValidator(userService);

        userDto = new UserDto();
        userDto.setUsername("test");
        userDto.setEmail("test@test.com");
        userDto.setPassword("test");
        userDto.setConfirmPassword("test");
    }

    @Test
    public void whenUsernameExistsThenFieldErrorIsSet() {
        when(userService.usernameExists("test")).thenReturn(true);
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("username"));
    }

    @Test
    public void whenEmailExistsThenFieldErrorIsSet() {
        when(userService.emailExists("test@test.com")).thenReturn(true);
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("email"));
    }

    @Test
    public void whenPasswordsDoNotMatchThenFieldErrorIsSet() {
        userDto.setPassword("password");
        userDto.setConfirmPassword("wrongPassword");
        Errors errors = new BeanPropertyBindingResult(userDto, "userDto");

        userDtoValidator.validate(userDto, errors);

        assertTrue(errors.hasFieldErrors("password"));
        assertTrue(errors.hasFieldErrors("confirmPassword"));
    }
}

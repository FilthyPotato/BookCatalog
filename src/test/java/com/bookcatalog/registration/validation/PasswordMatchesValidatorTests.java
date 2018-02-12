package com.bookcatalog.registration.validation;

import com.bookcatalog.registration.model.UserDto;
import org.junit.Test;
import org.mockito.Mock;

import javax.validation.ConstraintValidatorContext;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PasswordMatchesValidatorTests {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void twoTheSamePasswordsAreValid() {
        boolean valid = isValid("password1", "password1");
        assertTrue(valid);
    }

    @Test
    public void twoDifferentPasswordAreNotValid(){
        boolean valid = isValid("password1", "password2");
        assertFalse(valid);
    }

    private boolean isValid(String password, String confirmPassword) {
        UserDto userDto = new UserDto();
        userDto.setPassword(password);
        userDto.setConfirmPassword(confirmPassword);

        PasswordMatchesValidator passwordMatchesValidator = new PasswordMatchesValidator();
        return passwordMatchesValidator.isValid(userDto, constraintValidatorContext);
    }
}

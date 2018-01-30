package com.bookcatalog.validation;

import com.bookcatalog.model.UserRegistrationDto;
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
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setPassword(password);
        userRegistrationDto.setConfirmPassword(confirmPassword);

        PasswordMatchesValidator passwordMatchesValidator = new PasswordMatchesValidator();
        return passwordMatchesValidator.isValid(userRegistrationDto, constraintValidatorContext);
    }
}

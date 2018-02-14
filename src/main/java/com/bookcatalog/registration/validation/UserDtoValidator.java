package com.bookcatalog.registration.validation;

import com.bookcatalog.registration.UserService;
import com.bookcatalog.registration.model.UserDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserDtoValidator implements Validator {
    private UserService userService;

    public UserDtoValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDto dto = (UserDto) target;
        if (userService.usernameExists(dto.getUsername())) {
            errors.rejectValue("username", "","This username is already taken.");
        }

        if (userService.emailExists(dto.getEmail())) {
            errors.rejectValue("email","", "This email is already taken.");
        }
    }
}

package com.bookcatalog.registration;

import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserDto;
import com.bookcatalog.registration.repositories.RoleRepository;
import com.bookcatalog.registration.validation.exceptions.EmailExistsException;
import com.bookcatalog.registration.validation.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRegistrationService {
    private UserService userService;
    private RoleRepository roleRepository;

    @Autowired
    public UserRegistrationService(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    public User registerNewUserAccount(UserDto userDto) throws EmailExistsException, UsernameExistsException {
        if (userService.emailExists(userDto.getEmail())) {
            throw new EmailExistsException();
        }
        if (userService.usernameExists(userDto.getUsername())) {
            throw new UsernameExistsException();
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setEnabled(false);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userService.save(user);
        return user;
    }

}

package com.bookcatalog.registration;

import com.bookcatalog.registration.model.Role;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserRegistrationDto;
import com.bookcatalog.registration.repositories.RoleRepository;
import com.bookcatalog.registration.repositories.UserRepository;
import com.bookcatalog.registration.validation.EmailExistsException;
import com.bookcatalog.registration.validation.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserRegistrationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User registerNewUserAccount(UserRegistrationDto userRegistrationDto) throws EmailExistsException, UsernameExistsException {
        if (emailExists(userRegistrationDto.getEmail())) {
            throw new EmailExistsException();
        }
        if (usernameExists(userRegistrationDto.getUsername())) {
            throw new UsernameExistsException();
        }

        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(userRegistrationDto.getPassword());
        user.setEnabled(false);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
        return user;
    }

    private boolean usernameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}

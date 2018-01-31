package com.bookcatalog;

import com.bookcatalog.model.Role;
import com.bookcatalog.model.User;
import com.bookcatalog.model.UserRegistrationDto;
import com.bookcatalog.repositories.UserRepository;
import com.bookcatalog.validation.EmailExistsException;
import com.bookcatalog.validation.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserRegistrationService {
    private UserRepository userRepository;

    @Autowired
    public UserRegistrationService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
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

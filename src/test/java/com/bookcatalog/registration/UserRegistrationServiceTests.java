package com.bookcatalog.registration;

import com.bookcatalog.registration.model.Role;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserDto;
import com.bookcatalog.registration.repositories.RoleRepository;
import com.bookcatalog.registration.repositories.UserRepository;
import com.bookcatalog.registration.validation.exceptions.EmailExistsException;
import com.bookcatalog.registration.validation.exceptions.UsernameExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private UserRegistrationService userRegistrationService;
    private UserDto userDto;
    private User newlyCreatedUser;
    @Mock
    private Role expectedRole;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setUp() {
        userRegistrationService = new UserRegistrationService(new UserService(userRepository), roleRepository, passwordEncoder);

        userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setPassword("password1");
        userDto.setEmail("test@test.com");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encoded");
    }

    @Test
    public void registerNewUserAccountSetsAllFieldsFromDto() {
        User expectedUser = new User();
        expectedUser.setUsername(userDto.getUsername());
        expectedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        expectedUser.setEmail(userDto.getEmail());

        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userDto);

        assertEquals(expectedUser.getUsername(), newlyCreatedUser.getUsername());
        assertEquals(expectedUser.getEmail(), newlyCreatedUser.getEmail());
        assertEquals(expectedUser.getPassword(), newlyCreatedUser.getPassword());
    }

    @Test
    public void registerNewUserAccountSavesUserToDatabase() {
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(newlyCreatedUser);
    }

    @Test
    public void userAccountIsDisabledWhenCreated() {
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userDto);

        assertFalse(newlyCreatedUser.isEnabled());
    }

    @Test
    public void userHasOnlyUserRoleWhenCreated() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(expectedRole);
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userDto);

        Collection<Role> roles = newlyCreatedUser.getRoles();

        assertThat(roles, hasItems(expectedRole));
        assertThat(roles.size(), is(1));
    }

    @Test(expected = EmailExistsException.class)
    public void throwsEmailExistsExceptionWhenUserWithGivenEmailAlreadyExists() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(new User());

        userRegistrationService.registerNewUserAccount(userDto);
    }

    @Test(expected = Test.None.class)
    public void doesNotThrowsEmailExistsExceptionIfUserDoesNotExistsYet() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(null);

        userRegistrationService.registerNewUserAccount(userDto);
    }

    @Test(expected = UsernameExistsException.class)
    public void throwsUsernameExistsExceptionWhenUserAlreadyExists() {
        when(userRepository.findByUsername("user1")).thenReturn(new User());

        userRegistrationService.registerNewUserAccount(userDto);
    }

    @Test(expected = Test.None.class)
    public void doesNotThrowUsernameExistsExceptionWhenUserDoesNotExistYet() {
        when(userRepository.findByUsername("user1")).thenReturn(null);

        userRegistrationService.registerNewUserAccount(userDto);
    }
}

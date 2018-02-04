package com.bookcatalog.registration;

import com.bookcatalog.registration.UserRegistrationService;
import com.bookcatalog.registration.model.Role;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserRegistrationDto;
import com.bookcatalog.registration.repositories.RoleRepository;
import com.bookcatalog.registration.repositories.UserRepository;
import com.bookcatalog.registration.validation.EmailExistsException;
import com.bookcatalog.registration.validation.UsernameExistsException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collection;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRegistrationServiceTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    private UserRegistrationService userRegistrationService;
    private UserRegistrationDto userRegistrationDto;
    private User newlyCreatedUser;
    @Mock
    private Role expectedRole;

    @Before
    public void setUp() {
        userRegistrationService = new UserRegistrationService(userRepository, roleRepository);

        userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("user1");
        userRegistrationDto.setPassword("password1");
        userRegistrationDto.setEmail("test@test.com");
    }

    @Test
    public void registerNewUserAccountSetsAllFieldsFromDto() throws EmailExistsException, UsernameExistsException {
        User expectedUser = new User();
        expectedUser.setUsername("user1");
        expectedUser.setPassword("password1");
        expectedUser.setEmail("test@test.com");

        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userRegistrationDto);

        assertEquals(expectedUser.getUsername(), newlyCreatedUser.getUsername());
        assertEquals(expectedUser.getEmail(), newlyCreatedUser.getEmail());
        assertEquals(expectedUser.getPassword(), newlyCreatedUser.getPassword());
    }

    @Test
    public void registerNewUserAccountSavesUserToDatabase() throws EmailExistsException, UsernameExistsException {
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userRegistrationDto);

        Mockito.verify(userRepository, Mockito.times(1)).save(newlyCreatedUser);
    }

    @Test
    public void userAccountIsDisabledWhenCreated() throws EmailExistsException, UsernameExistsException {
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userRegistrationDto);

        assertFalse(newlyCreatedUser.isEnabled());
    }

    @Test
    public void userHasOnlyUserRoleWhenCreated() throws EmailExistsException, UsernameExistsException {
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(expectedRole);
        newlyCreatedUser = userRegistrationService.registerNewUserAccount(userRegistrationDto);

        Collection<Role> roles = newlyCreatedUser.getRoles();

        assertThat(roles, hasItems(expectedRole));
        assertThat(roles.size(), is(1));
    }

    @Test(expected = EmailExistsException.class)
    public void throwsEmailExistsExceptionWhenUserWithGivenEmailAlreadyExists() throws EmailExistsException, UsernameExistsException {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(new User());

        userRegistrationService.registerNewUserAccount(userRegistrationDto);
    }

    @Test(expected = Test.None.class)
    public void doesNotThrowsEmailExistsExceptionIfUserDoesNotExistsYet() throws EmailExistsException, UsernameExistsException {
        Mockito.when(userRepository.findByEmail("test@test.com")).thenReturn(null);

        userRegistrationService.registerNewUserAccount(userRegistrationDto);
    }

    @Test(expected = UsernameExistsException.class)
    public void throwsUsernameExistsExceptionWhenUserAlreadyExists() throws EmailExistsException, UsernameExistsException {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(new User());

        userRegistrationService.registerNewUserAccount(userRegistrationDto);
    }

    @Test(expected = Test.None.class)
    public void doesNotThrowUsernameExistsExceptionWhenUserDoesNotExistYet() throws EmailExistsException, UsernameExistsException {
        Mockito.when(userRepository.findByUsername("user1")).thenReturn(null);

        userRegistrationService.registerNewUserAccount(userRegistrationDto);
    }
}

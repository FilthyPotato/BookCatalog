package com.bookcatalog;

import com.bookcatalog.model.UserProfile;
import com.bookcatalog.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTests {
    @Mock
    private UserProfileRepository userProfileRepository;
    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    public void findByEmail() {
        String email = "";
        UserProfile expected = mock(UserProfile.class);
        when(userProfileRepository.findByUserEmail(email)).thenReturn(expected);

        UserProfile result = userProfileService.findByEmail(email);

        assertThat(result, is(expected));
    }

    @Test
    public void save() {
        UserProfile expected = mock(UserProfile.class);
        when(userProfileRepository.save(expected)).thenReturn(expected);

        UserProfile result = userProfileService.save(expected);

        verify(userProfileRepository, times(1)).save(expected);
        assertThat(result, is(expected));
    }
}

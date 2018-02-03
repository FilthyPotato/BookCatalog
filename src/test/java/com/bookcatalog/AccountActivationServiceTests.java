package com.bookcatalog;

import com.bookcatalog.model.User;
import com.bookcatalog.model.VerificationToken;
import com.bookcatalog.repositories.UserRepository;
import com.bookcatalog.repositories.VerificationTokenRepository;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class AccountActivationServiceTests {
    private AccountActivationService accountActivationService;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VerificationToken token;
    @Spy
    private User user;
    private String emptyTokenValue = "";

    @Before
    public void setUp() {
        accountActivationService = new AccountActivationService(verificationTokenRepository, userRepository);
    }

    @Test(expected = InvalidTokenException.class)
    public void throwsInvalidTokenExceptionIfTokenIsInvalid() throws InvalidTokenException, TokenExpiredException {
        //Token is invalid if it's not present in a database.
        when(verificationTokenRepository.findByToken(emptyTokenValue)).thenReturn(null);
        accountActivationService.activateAccount(emptyTokenValue);
    }

    @Test(expected = TokenExpiredException.class)
    public void throwsTokenExpiredExceptionIfTokenHasExpired() throws InvalidTokenException, TokenExpiredException {
        when(verificationTokenRepository.findByToken(emptyTokenValue)).thenReturn(token);
        when(token.hasExpired()).thenReturn(true);
        accountActivationService.activateAccount(emptyTokenValue);
    }

    @Test
    public void whenTokenIsValidAndDidNotExpireThenUserAccountIsEnabled() throws TokenExpiredException, InvalidTokenException {
        whenTokenIsValidAndDidNotExpire(emptyTokenValue);
        accountActivationService.activateAccount(emptyTokenValue);
        assertThat(user.isEnabled(), is(true));
    }

    @Test
    public void whenTokenIsValidAndDidNotExpireThenUserIsUpdatedInDatabase() throws TokenExpiredException, InvalidTokenException {
        whenTokenIsValidAndDidNotExpire(emptyTokenValue);
        accountActivationService.activateAccount(emptyTokenValue);
        verify(userRepository, times(1)).save(user);
    }

    private void whenTokenIsValidAndDidNotExpire(String tokenValue){
        when(verificationTokenRepository.findByToken(tokenValue)).thenReturn(token);
        when(token.hasExpired()).thenReturn(false);
        when(token.getUser()).thenReturn(user);
    }
}

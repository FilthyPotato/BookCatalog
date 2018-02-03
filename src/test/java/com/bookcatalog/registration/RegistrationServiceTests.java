package com.bookcatalog.registration;

import com.bookcatalog.registration.MailService;
import com.bookcatalog.registration.RegistrationMailGenerator;
import com.bookcatalog.registration.RegistrationService;
import com.bookcatalog.registration.VerificationTokenMaker;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.VerificationToken;
import com.bookcatalog.registration.repositories.VerificationTokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceTests {
    private RegistrationService registrationService;
    @Mock
    private VerificationTokenMaker verificationTokenMaker;
    @Mock
    private MailService mailService;
    @Mock
    private RegistrationMailGenerator registrationMailGenerator;
    @Mock
    private VerificationTokenRepository verificationTokenRepository;
    @Mock
    private VerificationToken expectedToken;
    @Mock
    private User user;
    private String mailDestination = "";
    private String host = "";
    private String contextName = "";
    private String token = "";
    @Mock
    private SimpleMailMessage mailMessage;

    @Before
    public void setUp(){
        registrationService = new RegistrationService(verificationTokenRepository, verificationTokenMaker, mailService, registrationMailGenerator);
    }

    @Test
    public void initRegistrationConfirmationSavesVerificationTokenToDatabase() {
        //1. saves verificationToken to db (no need to see if the data is valid, since verificationTokenMaker is
        //responsible for that and it has been tested)
        when(verificationTokenMaker.createVerificationToken(user)).thenReturn(expectedToken);
        registrationService.initRegistrationConfirmation(user, null, null);
        verify(verificationTokenRepository, times(1)).save(expectedToken);
    }

    @Test
    public void initRegistrationConfirmationSendsEmailToUser() {
        when(verificationTokenMaker.createVerificationToken(user)).thenReturn(expectedToken);
        when(user.getEmail()).thenReturn(mailDestination);
        when(expectedToken.getToken()).thenReturn(token);
        when(registrationMailGenerator.generateMail(mailDestination, host, contextName, token)).thenReturn(mailMessage);
        registrationService.initRegistrationConfirmation(user, host, contextName);
        verify(mailService, times(1)).send(mailMessage);
    }
}

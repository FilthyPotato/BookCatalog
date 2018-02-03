package com.bookcatalog.registration;

import com.bookcatalog.UUIDGenerator;
import com.bookcatalog.registration.VerificationTokenMaker;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.VerificationToken;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class VerificationTokenMakerTests {
    @Mock
    private UUIDGenerator uuidGenerator;
    @Mock
    private User user;

    private VerificationTokenMaker verificationTokenMaker;
    private VerificationToken verificationToken;


    @Before
    public void setUp(){
        Mockito.when(uuidGenerator.generateUUID()).thenReturn("12345");
        verificationTokenMaker = new VerificationTokenMaker(uuidGenerator);
        verificationToken = verificationTokenMaker.createVerificationToken(user);
    }

    @Test
    public void tokenIsUUID(){
        assertThat(verificationToken.getToken(), is("12345"));
    }

    @Test
    public void userIsSet(){
        assertThat(verificationToken.getUser(), is(user));
    }
}

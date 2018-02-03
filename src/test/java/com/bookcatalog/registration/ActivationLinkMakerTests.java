package com.bookcatalog.registration;

import com.bookcatalog.registration.ActivationLinkMaker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ActivationLinkMakerTests {
    // link format: http://host/contextName/confirmRegistration?token=TOKEN
    private ActivationLinkMaker activationLinkMaker;
    private final String host = "localhost:8080";
    private final String token = "12345";

    @Before
    public void setUp(){
        activationLinkMaker = new ActivationLinkMaker();
    }

    @Test
    public void activationLinkWithEmptyContextNameIsCorrect(){
        String contextName = "";
        String link = activationLinkMaker.createActivationLink(host, contextName, token);
        assertThat(link, is(String.format("http://%s/confirmRegistration?token=%s", host, token)));
    }

    @Test
    public void activationLinkWithNonEmptyContextNameIsCorrect(){
        String contextName = "/app";
        String link = activationLinkMaker.createActivationLink(host, contextName, token);
        assertThat(link, is(String.format("http://%s/app/confirmRegistration?token=%s", host, token)));
    }
}

package com.bookcatalog.registration;

import com.bookcatalog.registration.ActivationLinkMaker;
import com.bookcatalog.registration.RegistrationMailGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationMailGeneratorTests {
    private RegistrationMailGenerator registrationMailGenerator;
    private SimpleMailMessage mailMessage;
    private String mailDestination = "test@test.com";
    @Mock
    private ActivationLinkMaker activationLinkMaker;
    private String host = "www.host.com";
    private String contextName = "";
    private String token = "9999";
    private String activationLink = "http://www.dummyactivationlink.com";

    @Before
    public void setUp() {
        Mockito.when(activationLinkMaker.createActivationLink(host, contextName, token)).thenReturn(activationLink);
        registrationMailGenerator = new RegistrationMailGenerator(activationLinkMaker);
        mailMessage = registrationMailGenerator.generateMail(mailDestination, host, contextName, token);
    }

    @Test
    public void titleIsAccountActivation() {
        assertThat(mailMessage.getSubject(), is("Account Activation"));
    }

    @Test
    public void destinationIsCorrect() {
        assertThat(mailMessage.getTo()[0], is(mailDestination));
    }

    @Test
    /*
    I think I should only test if the mailMessage's content CONTAINS the activationLink. IDK.
     */
    public void mailContentIsCorrect() {
        String expectedContent = "To activate your account click on this link: " + activationLink;
        assertThat(mailMessage.getText(), is(expectedContent));
    }
}

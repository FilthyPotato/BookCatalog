package com.bookcatalog;

import org.mockito.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceTests {
    @Mock
    private JavaMailSender javaMailSender;
    private MailService mailService;
    private SimpleMailMessage mailMessage;


    @Before
    public void setUp() {
        mailService = new MailService(javaMailSender);
        mailMessage = new SimpleMailMessage();
    }

    @Test
    public void sendMailSucceeded() {
        mailService.send(mailMessage);
        Mockito.verify(javaMailSender, Mockito.times(1)).send(mailMessage);
    }
}

/*
Notes:
1.
public void send(SimpleMailMessage mailMessage) {
    javaMailSender.send(mailMessage);
}
Mock JavaMailSender and verify if it has been called once.
JavaMailSender is responsible for sending the mail, so JavaMailSender#send invoked == mail sent.
Also no need to test if the mail has been sent to proper destination and with proper data since JavaMailSender is
responsible for taking care of it.

2.
public void send(String from, String to, String subject, String body) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setTo(to);
    mailMessage.setFrom(from);
    mailMessage.setSubject(subject);
    mailMessage.setText(body);
    javaMailSender.send(mailMessage);
}
Use Wiser to verify if the data has been set correctly and if the mail has been sent.
assertReceivedMessage(wiser)
    .to("to@domain.com")
    .from("from@domain.com")
    .withSubject("subject")
    .withContent("body");

3. If you send an mail via REST endpoint, do the same as in 2.
(see http://blog.codeleak.pl/2014/09/testing-mail-code-in-spring-boot.html)
*/
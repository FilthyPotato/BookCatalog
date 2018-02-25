package com.bookcatalog.integration;

import com.bookcatalog.registration.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@PropertySource("classpath:gmail.properties")
public class GmailMailSendingIntegrationTest {
    @Autowired
    private MailService mailService;

    @Test(expected = Test.None.class)
    public void sendingEmailViaGmailWorks() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("springbookcatalog@gmail.com");
        mailMessage.setSubject("Spring GMAIL test");
        mailMessage.setText("Just testing if sending emails via Spring works.");
        mailService.send(mailMessage);
    }
}

package com.bookcatalog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Integration test
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@PropertySource("gmail.properties")
public class GmailMailSendingTests {
    @Autowired
    private MailService mailService;

    @Test(expected = Test.None.class)
    public void sendingEmailViaGmailWorks() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("reolgoddess@gmail.com");
        mailMessage.setSubject("Spring GMAIL test");
        mailMessage.setText("Just testing if sending emails via Spring works.");
        mailService.send(mailMessage);
    }
}

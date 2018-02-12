package com.bookcatalog.registration;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
class RegistrationMailGenerator {
    private ActivationLinkMaker activationLinkMaker;

    RegistrationMailGenerator(ActivationLinkMaker activationLinkMaker) {
        this.activationLinkMaker = activationLinkMaker;
    }

    public SimpleMailMessage generateMail(String mailDestination, String host, String contextName, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Account Activation");
        mailMessage.setTo(mailDestination);
        mailMessage.setText("To activate your account click on this link: " + activationLinkMaker.createActivationLink(host, contextName, token));
        return mailMessage;
    }
}

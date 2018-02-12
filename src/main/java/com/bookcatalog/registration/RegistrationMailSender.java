package com.bookcatalog.registration;

import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.VerificationToken;
import com.bookcatalog.registration.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class RegistrationMailSender {
    private VerificationTokenRepository verificationTokenRepository;
    private VerificationTokenMaker verificationTokenMaker;
    private MailService mailService;
    private RegistrationMailGenerator registrationMailGenerator;

    @Autowired
    public RegistrationMailSender(VerificationTokenRepository verificationTokenRepository, VerificationTokenMaker verificationTokenMaker,
                                  MailService mailService, RegistrationMailGenerator registrationMailGenerator) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.verificationTokenMaker = verificationTokenMaker;
        this.mailService = mailService;
        this.registrationMailGenerator = registrationMailGenerator;
    }

    public void sendRegistrationMail(User user, String host, String contextName) {
        VerificationToken verificationToken = verificationTokenMaker.createVerificationToken(user);
        verificationTokenRepository.save(verificationToken);

        SimpleMailMessage mailMessage = registrationMailGenerator.generateMail(user.getEmail(), host, contextName, verificationToken.getToken());
        mailService.send(mailMessage);
    }
}

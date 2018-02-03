package com.bookcatalog;

import com.bookcatalog.model.User;
import com.bookcatalog.model.VerificationToken;
import com.bookcatalog.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;

public class RegistrationService {
    private VerificationTokenRepository verificationTokenRepository;
    private VerificationTokenMaker verificationTokenMaker;
    private MailService mailService;
    private RegistrationMailGenerator registrationMailGenerator;

    @Autowired
    public RegistrationService(VerificationTokenRepository verificationTokenRepository, VerificationTokenMaker verificationTokenMaker,
                               MailService mailService, RegistrationMailGenerator registrationMailGenerator) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.verificationTokenMaker = verificationTokenMaker;
        this.mailService = mailService;
        this.registrationMailGenerator = registrationMailGenerator;
    }

    public void initRegistrationConfirmation(User user, String host, String contextName) {
        VerificationToken verificationToken = verificationTokenMaker.createVerificationToken(user);
        verificationTokenRepository.save(verificationToken);

        SimpleMailMessage mailMessage = registrationMailGenerator.generateMail(user.getEmail(), host, contextName, verificationToken.getToken());
        mailService.send(mailMessage);
    }
}

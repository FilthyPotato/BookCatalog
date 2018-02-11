package com.bookcatalog.registration;

import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.VerificationToken;
import com.bookcatalog.registration.repositories.UserRepository;
import com.bookcatalog.registration.repositories.VerificationTokenRepository;
import com.bookcatalog.registration.validation.exceptions.InvalidTokenException;
import com.bookcatalog.registration.validation.exceptions.TokenExpiredException;

public class AccountActivationService {
    private VerificationTokenRepository verificationTokenRepository;
    private UserRepository userRepository;

    public AccountActivationService(VerificationTokenRepository verificationTokenRepository, UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    public void activateAccount(String token) throws InvalidTokenException, TokenExpiredException {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new InvalidTokenException();
        }

        if (verificationToken.hasExpired()) {
            throw new TokenExpiredException();
        }

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }
}

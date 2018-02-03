package com.bookcatalog;

import com.bookcatalog.model.User;
import com.bookcatalog.model.VerificationToken;
import com.bookcatalog.repositories.UserRepository;
import com.bookcatalog.repositories.VerificationTokenRepository;

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

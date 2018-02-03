package com.bookcatalog.registration;

import com.bookcatalog.UUIDGenerator;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.VerificationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class VerificationTokenMaker {
    private UUIDGenerator uuidGenerator;

    @Autowired
    VerificationTokenMaker(UUIDGenerator uuidGenerator) {
        this.uuidGenerator = uuidGenerator;
    }

    public VerificationToken createVerificationToken(User user) {
        return new VerificationToken(user, uuidGenerator.generateUUID());
    }
}

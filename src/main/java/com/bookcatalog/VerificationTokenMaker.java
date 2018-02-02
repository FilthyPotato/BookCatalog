package com.bookcatalog;

import com.bookcatalog.model.User;
import com.bookcatalog.model.VerificationToken;
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

package com.bookcatalog.repositories;

import com.bookcatalog.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
}

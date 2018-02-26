package com.bookcatalog.service;

import com.bookcatalog.model.UserProfile;
import com.bookcatalog.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;

    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByUserEmail(email);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

}

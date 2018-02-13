package com.bookcatalog.registration;

import org.springframework.stereotype.Service;

@Service
class ActivationLinkMaker {
    public String createActivationLink(String host, String contextName, String token) {
        return String.format("http://%s%s/registration?token=%s", host, contextName, token);
    }
}

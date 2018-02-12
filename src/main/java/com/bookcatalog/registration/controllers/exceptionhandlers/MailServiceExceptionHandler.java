package com.bookcatalog.registration.controllers.exceptionhandlers;

import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MailServiceExceptionHandler {

    @ExceptionHandler({MailException.class})
    public void handleMailException(MailException e){
        throw e;    // TODO: logging system (AOP?)
    }
}

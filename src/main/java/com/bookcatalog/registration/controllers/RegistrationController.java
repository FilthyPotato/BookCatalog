package com.bookcatalog.registration.controllers;

import com.bookcatalog.registration.RegistrationMailSender;
import com.bookcatalog.registration.UserRegistrationService;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserRegistrationDto;
import com.bookcatalog.registration.validation.UserRegistrationDtoValidator;
import com.bookcatalog.registration.validation.exceptions.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private UserRegistrationService userRegistrationService;
    private RegistrationMailSender registrationMailSender;
    private UserRegistrationDtoValidator userRegistrationDtoValidator;

    public RegistrationController(UserRegistrationService userRegistrationService, RegistrationMailSender registrationMailSender, UserRegistrationDtoValidator userRegistrationDtoValidator) {
        this.userRegistrationService = userRegistrationService;
        this.registrationMailSender = registrationMailSender;
        this.userRegistrationDtoValidator = userRegistrationDtoValidator;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder)
    {
        binder.addValidators(userRegistrationDtoValidator);
    }

    @PostMapping
    public void register(@Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        User user = userRegistrationService.registerNewUserAccount(userRegistrationDto);
        registrationMailSender.sendRegistrationMail(user, "localhost:8080", webRequest.getContextPath());
    }

}

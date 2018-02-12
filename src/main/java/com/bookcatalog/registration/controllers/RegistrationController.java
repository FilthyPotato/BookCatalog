package com.bookcatalog.registration.controllers;

import com.bookcatalog.registration.AccountActivationService;
import com.bookcatalog.registration.RegistrationMailSender;
import com.bookcatalog.registration.UserRegistrationService;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserDto;
import com.bookcatalog.registration.validation.UserRegistrationDtoValidator;
import com.bookcatalog.registration.validation.exceptions.ValidationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    private UserRegistrationService userRegistrationService;
    private RegistrationMailSender registrationMailSender;
    private UserRegistrationDtoValidator userRegistrationDtoValidator;

    private AccountActivationService accountActivationService;

    public RegistrationController(UserRegistrationService userRegistrationService, RegistrationMailSender registrationMailSender,
                                  UserRegistrationDtoValidator userRegistrationDtoValidator, AccountActivationService accountActivationService) {
        this.userRegistrationService = userRegistrationService;
        this.registrationMailSender = registrationMailSender;
        this.userRegistrationDtoValidator = userRegistrationDtoValidator;
        this.accountActivationService = accountActivationService;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(userRegistrationDtoValidator);
    }

    @PostMapping
    public void register(@Valid UserDto userDto, BindingResult bindingResult, WebRequest webRequest) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        User user = userRegistrationService.registerNewUserAccount(userDto);
        registrationMailSender.sendRegistrationMail(user, "localhost:8080", webRequest.getContextPath());
    }

    @GetMapping
    public void register(@RequestParam String token) {
        accountActivationService.activateAccount(token);
    }

}

package com.bookcatalog.registration.controllers;

import com.bookcatalog.registration.AccountActivationService;
import com.bookcatalog.registration.RegistrationMailSender;
import com.bookcatalog.registration.UserRegistrationService;
import com.bookcatalog.registration.model.User;
import com.bookcatalog.registration.model.UserDto;
import com.bookcatalog.registration.validation.UserDtoValidator;
import com.bookcatalog.validation.ValidationException;
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
    private UserDtoValidator userDtoValidator;

    private AccountActivationService accountActivationService;

    public RegistrationController(UserRegistrationService userRegistrationService, RegistrationMailSender registrationMailSender,
                                  UserDtoValidator userDtoValidator, AccountActivationService accountActivationService) {
        this.userRegistrationService = userRegistrationService;
        this.registrationMailSender = registrationMailSender;
        this.userDtoValidator = userDtoValidator;
        this.accountActivationService = accountActivationService;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(userDtoValidator);
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

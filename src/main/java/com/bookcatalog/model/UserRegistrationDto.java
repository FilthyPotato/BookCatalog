package com.bookcatalog.model;

import com.bookcatalog.validation.EmailNoIntranet;
import com.bookcatalog.validation.PasswordMatches;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


@Data
@PasswordMatches
public class UserRegistrationDto {
    @NotEmpty 
    @Size(min = 2)
    private String username;
    @NotEmpty
    @EmailNoIntranet
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}

package com.bookcatalog.registration.model;

import com.bookcatalog.registration.validation.EmailNoIntranet;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;


@Data
public class UserDto {
    @NotEmpty
    @Size(min = 2)
    private String username;
    @NotEmpty
    @EmailNoIntranet()
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;
}

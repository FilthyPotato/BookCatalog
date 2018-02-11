package com.bookcatalog.registration.validation;

import org.hibernate.validator.constraints.Email;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

// Using Commons Validator: https://gist.github.com/robertoschwald/ce23c8c23ebd5b93fc3f60c150e35cea
@Email(regexp=".+@.+\\..+")
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface EmailNoIntranet {
    @OverridesAttribute(constraint = Email.class, name = "message") String message() default "Invalid email address.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
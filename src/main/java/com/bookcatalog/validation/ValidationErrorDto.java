package com.bookcatalog.validation;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class ValidationErrorDto {
    private List<FieldError> fieldErrors = new ArrayList<>();

    public void addFieldError(FieldError fieldError){
        fieldErrors.add(fieldError);
    }
}

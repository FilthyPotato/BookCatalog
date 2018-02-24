package com.bookcatalog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class AuthorDto {
    @NotEmpty
    private String name;

    public AuthorDto() {
    }

    public AuthorDto(String name) {
        this.name = name;
    }
}

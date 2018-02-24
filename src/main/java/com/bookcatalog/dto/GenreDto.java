package com.bookcatalog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class GenreDto {
    @NotEmpty
    private String name;

    public GenreDto() {
    }

    public GenreDto(String name) {
        this.name = name;
    }
}

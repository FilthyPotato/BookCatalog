package com.bookcatalog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Data
public class NewBookDto extends BookDto {
    @NotEmpty
    private List<String> shelfNames = new ArrayList<>();
}

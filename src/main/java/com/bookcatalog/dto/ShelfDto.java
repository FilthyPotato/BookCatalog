package com.bookcatalog.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class ShelfDto {

    private Long id;
    @NotEmpty
    private String name;
}

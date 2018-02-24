package com.bookcatalog.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
public class BookDto {
    private Long id;
    @NotEmpty
    private String title;
    @Valid
    private List<AuthorDto> authors = new ArrayList<>();
    private String description;
    private Integer pages;
    private String publishedDate;
    private String publisher;
    @Valid
    private List<GenreDto> genres = new ArrayList<>();

    public AuthorDto getAuthorDto(int index) {
        return authors.get(index);
    }

    public GenreDto getGenreDto(int index) {
        return genres.get(index);
    }
}

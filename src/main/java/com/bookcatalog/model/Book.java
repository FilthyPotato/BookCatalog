package com.bookcatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@ToString(exclude = "shelves")
@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"))
    private List<Author> authors = new ArrayList<>();
    private String description;
    private Integer pages;
    private String publishedDate;
    private String publisher;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "books_genres",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id", referencedColumnName = "id"))
    private List<Genre> genres;

    @JsonIgnore
    @ManyToMany(mappedBy = "books")
    private List<Shelf> shelves = new ArrayList<>();
}

package com.bookcatalog.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Entity
@Data
public class Shelf {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "shelves_books",
            joinColumns = @JoinColumn(name = "shelf_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book){
        books.add(book);
    }

    public void removeBook(Long bookId) {
        books.removeIf(b -> b.getId().equals(bookId));
    }

    public Optional<Book> getBook(Long bookId) {
        return books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst();
    }
}

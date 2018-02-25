package com.bookcatalog.model;

import com.bookcatalog.registration.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

@Entity
@Data
public class UserProfile {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @OneToOne(mappedBy = "userProfile")
    private User user;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "id")
    private List<Shelf> shelves = new ArrayList<>();

    public void addShelf(Shelf shelf) {
        shelves.add(shelf);
    }

    public Optional<Shelf> getShelf(Long id) {
        return getShelf(s -> s.getId().equals(id));
    }

    public Optional<Shelf> getShelf(String name) {
        return getShelf(s -> s.getName().equals(name));
    }

    private Optional<Shelf> getShelf(Predicate<Shelf> predicate) {
        return shelves.stream()
                .filter(predicate)
                .findFirst();
    }

    public List<Book> getAllBooks() {
        return shelves.stream()
                .flatMap(s -> s.getBooks().stream())
                .collect(toList());
    }

    public boolean hasBook(Long bookId) {
        return shelves.stream()
                .flatMap(s -> s.getBooks().stream())
                .anyMatch(book -> book.getId().equals(bookId));
    }

    public boolean hasShelf(Long shelfId) {
        return shelves.stream()
                .anyMatch(shelf -> shelf.getId().equals(shelfId));
    }

}

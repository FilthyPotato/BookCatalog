package com.bookcatalog;

import com.bookcatalog.dto.*;
import com.bookcatalog.model.*;
import com.bookcatalog.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;
    private ShelfService shelfService;
    private BookService bookService;
    private BookDtoConverter bookDtoConverter;

    public UserProfileService(UserProfileRepository userProfileRepository, ShelfService shelfService, BookService bookService, BookDtoConverter bookDtoConverter) {
        this.userProfileRepository = userProfileRepository;
        this.shelfService = shelfService;
        this.bookService = bookService;
        this.bookDtoConverter = bookDtoConverter;
    }

    public List<Book> findAllBooksDistinct(String email){
        return userProfileRepository.findAllBooksDistinctOrderByBookId(email);
    }

    public Book findOneBook(String email, Long bookId) {
        return userProfileRepository.findOneBook(email, bookId);
    }

    public List<String> findAllShelfNames(String email) {
        return userProfileRepository.findAllShelfNamesOrderByShelfId(email);
    }

    public List<Book> findBooksOnShelf(String email, Long shelfId) {
        return userProfileRepository.findBooksOnShelfOrderByBookId(email, shelfId);
    }

    public UserProfile findByEmail(String email) {
        return userProfileRepository.findByUserEmail(email);
    }

    public UserProfile save(UserProfile userProfile) {
        return userProfileRepository.save(userProfile);
    }

    //TODO: shouldn't I construct new shelfDto instead of reusing the parameter?
    public ShelfDto addShelf(String email, ShelfDto shelfDto) {
        Shelf shelf = shelfService.save(shelfDto);  // Just to get the ID
        this.addShelf(email, shelf);

        shelfDto.setId(shelf.getId());
        return shelfDto;
    }

    private void addShelf(String email, Shelf shelf) {
        UserProfile userProfile = this.findByEmail(email);
        userProfile.addShelf(shelf);
        this.save(userProfile);
    }

    //removes all books with given id (so duplicates too)
    public void removeBookFromShelf(String email, Long bookId, Long shelfId) {
        ifShelfBelongsToUser(email, shelfId, (s, userProfile) -> {
            s.removeBook(bookId);
            shelfService.save(s);
            this.save(userProfile);
        });
    }

    public void updateShelf(String email, Long shelfId, ShelfDto shelfDto) {
        ifShelfBelongsToUser(email, shelfId, (s, userProfile) -> {
            s.setName(shelfDto.getName());
            shelfService.save(s);
        });
    }

    public void removeBookFromAllShelves(String email, Long bookId) {
        UserProfile userProfile = findByEmail(email);
        List<Shelf> shelves = userProfile.getShelves();
        shelves.forEach(s -> {
            s.removeBook(bookId);
            shelfService.save(s);
        });

        this.save(userProfile);
    }

    //TODO: spec is wrong - if user doesn't own shelf with 'shelfId', dto without an id will be returned from controller
    //TODO: a dto with 'permission denied' message should be sent instead.
    //TODO: maybe throwPermissionDeniedIfShelfDoesNotBelongToUser
    public BookDto addBookToShelf(String email, Long shelfId, BookDto bookDto) {
        ifShelfBelongsToUser(email, shelfId, (shelf, userProfile) -> {
            Book book = bookService.save(bookDto);
            addBookToShelfAndUpdate(book, shelf);
            bookDto.setId(book.getId());
            this.save(userProfile);
        });

        return bookDto;
    }

    private void ifShelfBelongsToUser(String email, Long shelfId, BiConsumer<Shelf, UserProfile> consumer) {
        UserProfile userProfile = findByEmail(email);
        Optional<Shelf> shelf = userProfile.getShelf(shelfId);
        shelf.ifPresent(s -> consumer.accept(s, userProfile));
    }


    //TODO: not tested (+ the method below)
    public void replaceBook(String email, Long bookId, BookDto bookDto) {
        ifBookBelongsToUser(email, bookId,
                b -> replaceBook(bookId, bookDto));
    }

    private void replaceBook(Long bookId, BookDto bookDto) {
        Book newBook = bookDtoConverter.createFromDto(bookDto);
        newBook.setId(bookId);
        bookService.save(newBook);
    }

    //TODO: not tested (+ all the private methods are also not tested. They should be tested via this public method.)
    public void updateBook(String email, Long bookId, BookDto bookDto) {
        ifBookBelongsToUser(email, bookId, b -> {
            updateBookFields(b, bookDto);
            bookService.save(b);
        });
    }

    private void ifBookBelongsToUser(String email, Long bookId, Consumer<Book> consumer) {
        UserProfile userProfile = findByEmail(email);
        Optional<Book> book = userProfile.getShelves().stream()
                .flatMap(s -> s.getBooks().stream())
                .filter(b -> b.getId().equals(bookId))
                .findFirst();

        book.ifPresent(consumer);
    }

    private void updateBookFields(Book book, BookDto bookDto) {
        setIfNotNull(book::setTitle, bookDto::getTitle);
        setIfNotNull(book::setDescription, bookDto::getDescription);
        setIfNotNull(book::setAuthors, () -> createAuthors(bookDto.getAuthors()));
        setIfNotNull(book::setGenres, () -> createGenres(bookDto.getGenres()));
        setIfNotNull(book::setPages, bookDto::getPages);
        setIfNotNull(book::setPublishedDate, bookDto::getPublishedDate);
        setIfNotNull(book::setPublisher, bookDto::getPublisher);
    }

    private <A, D> List<A> mapCollection(Collection<D> dtos, BiConsumer<A, D> fieldMapper, Supplier<A> newInstance) {
        if(dtos.isEmpty())
            return null;

        List<A> result = new ArrayList<>();
        dtos.forEach(dto -> {
            A entity = newInstance.get();
            fieldMapper.accept(entity, dto);
            result.add(entity);
        });
        return result;
    }

    private List<Author> createAuthors(Collection<AuthorDto> authorDtos) {
        return mapCollection(authorDtos, this::authorFieldsMapper, Author::new);
    }


    private List<Genre> createGenres(Collection<GenreDto> genreDtos) {
        return mapCollection(genreDtos, this::genreFieldsMapper, Genre::new);
    }

    private void authorFieldsMapper(Author author, AuthorDto authorDto) {
        setIfNotNull(author::setName, authorDto::getName);
    }

    private void genreFieldsMapper(Genre genre, GenreDto genreDto) {
        setIfNotNull(genre::setName, genreDto::getName);
    }

    private <T> void setIfNotNull(Consumer<T> setter, Supplier<T> getter) {
        if (getter.get() != null) {
            setter.accept(getter.get());
        }
    }

    //TODO: should return the book added
    //TODO: test if only one book has been created
    public BookDto addBook(String email, NewBookDto dto) {
        final Book book = bookService.save(bookDtoConverter.createFromDto(dto));
        UserProfile userProfile = findByEmail(email);

        dto.getShelfNames().forEach(name -> {
            Optional<Shelf> shelf = userProfile.getShelf(name);
            shelf.ifPresent(s -> addBookToShelfAndUpdate(book, s));
        });

        dto.setId(book.getId());
        return dto;
    }

    private void addBookToShelfAndUpdate(Book book, Shelf shelf) {
        shelf.addBook(book);
        shelfService.save(shelf);
    }
}

package com.bookcatalog.service.book;

import com.bookcatalog.BookService;
import com.bookcatalog.ShelfService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.validation.exceptions.ShelfDoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
class BookAdder {

    private BookService bookService;
    private ShelfService shelfService;

    BookAdder(BookService bookService, ShelfService shelfService) {
        this.bookService = bookService;
        this.shelfService = shelfService;
    }

    public Long addBookToShelf(Long shelfId, BookDto bookDto) {
        Shelf shelf = findOrThrow(shelfId);
        Book book = createBookFromDtoAndSave(bookDto);
        addBookToShelf(book, shelf);
        return book.getId();
    }

    public Long addBook(NewBookDto dto) {
        Book savedBook = createBookFromDtoAndSave(dto);

        List<Shelf> shelves = dto.getShelfIds().stream()
                .map(this::findOrThrow)
                .collect(toList());

        shelves.forEach(shelf ->
                addBookToShelf(savedBook, shelf));

        return savedBook.getId();
    }

    private Shelf findOrThrow(Long shelfId) throws ShelfDoesNotExistException {
        Shelf shelf = shelfService.findOne(shelfId);
        if (shelf == null)
            throw new ShelfDoesNotExistException(shelfId);
        return shelf;
    }

    private void addBookToShelf(Book book, Shelf shelf) {
        shelf.addBook(book);
        shelfService.save(shelf);
    }

    private Book createBookFromDtoAndSave(BookDto bookDto) {
        Book book = bookService.createFromDto(bookDto);
        return bookService.save(book);
    }

}

package com.bookcatalog.service.book;

import com.bookcatalog.BookService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.model.Book;
import org.springframework.stereotype.Service;

@Service
class BookUpdater {

    private BookService bookService;
    private BookFieldsUpdater bookFieldsUpdater;

    BookUpdater(BookService bookService, BookFieldsUpdater bookFieldsUpdater) {
        this.bookService = bookService;
        this.bookFieldsUpdater = bookFieldsUpdater;
    }

    public void replaceBook(Long bookId, BookDto bookDto) {
        Book newBook = bookService.createFromDto(bookDto);
        newBook.setId(bookId);
        bookService.save(newBook);
    }

    //TODO: not tested
    public void updateBook(Long bookId, BookDto bookDto) {
        Book book = bookService.findOne(bookId);
        if (book != null) {
            bookFieldsUpdater.updateBookFields(book, bookDto);
            bookService.save(book);
        }
    }
}

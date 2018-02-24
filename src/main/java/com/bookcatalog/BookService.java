package com.bookcatalog;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.BookDtoConverter;
import com.bookcatalog.model.Book;
import com.bookcatalog.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;
    private BookDtoConverter bookDtoConverter;

    public BookService(BookRepository bookRepository, BookDtoConverter bookDtoConverter) {
        this.bookRepository = bookRepository;
        this.bookDtoConverter = bookDtoConverter;
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book save(BookDto bookDto) {
        Book book = bookDtoConverter.createFromDto(bookDto);
        return this.save(book);
    }
}

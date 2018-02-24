package com.bookcatalog; 

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.BookDtoConverter;
import com.bookcatalog.model.Book;
import com.bookcatalog.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTests {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookDtoConverter bookDtoConverter;
    @InjectMocks
    private BookService bookService;

    @Test
    public void save() {
        Book book = mock(Book.class);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(book);

        verify(bookRepository).save(book);
        assertEquals(result, book);
    }

    @Test
    public void saveDtoSavesEntityToDatabase() {
        BookDto bookDto = mock(BookDto.class);
        Book book = mock(Book.class);
        when(bookDtoConverter.createFromDto(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.save(bookDto);

        verify(bookRepository).save(book);
        assertEquals(result, book);
    }


}

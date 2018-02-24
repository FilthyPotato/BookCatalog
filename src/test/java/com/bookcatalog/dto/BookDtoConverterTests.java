package com.bookcatalog.dto; 

import com.bookcatalog.config.SpringConfig;
import com.bookcatalog.model.Book;
import org.junit.Test;
import org.junit.Before;
import org.modelmapper.ModelMapper;

import javax.swing.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class BookDtoConverterTests {
    private BookDtoConverter bookDtoConverter;
        
    @Before
    public void setUp() {
        SpringConfig springConfig = new SpringConfig();
        bookDtoConverter = new BookDtoConverter(springConfig.modelMapper());
    }

    @Test
    public void idIsNotSet() {
        BookDto bookDto = new BookDto();
        bookDto.setId(5L);

        Book book = bookDtoConverter.createFromDto(bookDto);

        assertThat(book.getId(), is(nullValue()));
    }
}

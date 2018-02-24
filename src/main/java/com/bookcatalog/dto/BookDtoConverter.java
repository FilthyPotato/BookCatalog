package com.bookcatalog.dto;

import com.bookcatalog.dto.AuthorDto;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.model.Author;
import com.bookcatalog.model.Book;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDtoConverter {
    private ModelMapper modelMapper;

    public BookDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Book createFromDto(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}

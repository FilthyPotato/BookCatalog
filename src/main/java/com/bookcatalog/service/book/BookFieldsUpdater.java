package com.bookcatalog.service.book;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.model.Book;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
class BookFieldsUpdater {

    private ModelMapper modelMapper;

    BookFieldsUpdater(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void updateBookFields(Book destination, BookDto bookData) {
        modelMapper.map(bookData, destination, "update");
    }
}

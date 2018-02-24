package com.bookcatalog.dto;

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Shelf;
import org.springframework.stereotype.Component;

@Component
public class ShelfDtoConverter {

    public Shelf createFromDto(ShelfDto shelfDto) {
        Shelf shelf = new Shelf();
        shelf.setName(shelfDto.getName());
        return shelf;
    }
}

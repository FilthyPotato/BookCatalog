package com.bookcatalog.dto;

import com.bookcatalog.model.Shelf;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ShelfDtoConverter {

    private ModelMapper modelMapper;

    public ShelfDtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Shelf fromDto(ShelfDto shelfDto) {
        return modelMapper.map(shelfDto, Shelf.class);
    }

    public ShelfDto toDto(Shelf shelf) {
        return modelMapper.map(shelf, ShelfDto.class);
    }


}

package com.bookcatalog.service.shelf;

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Shelf;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
class ShelfFieldsUpdater {
    private ModelMapper modelMapper;

    ShelfFieldsUpdater(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public void updateShelfFields(Shelf destination, ShelfDto shelfData) {
        modelMapper.map(shelfData, destination, "update");
    }
}

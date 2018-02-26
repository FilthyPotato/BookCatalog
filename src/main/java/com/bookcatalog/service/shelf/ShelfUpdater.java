package com.bookcatalog.service.shelf;

import com.bookcatalog.service.ShelfService;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Shelf;
import org.springframework.stereotype.Service;

@Service
class ShelfUpdater {

    private ShelfService shelfService;
    private ShelfFieldsUpdater shelfFieldsUpdater;

    ShelfUpdater(ShelfService shelfService, ShelfFieldsUpdater shelfFieldsUpdater) {
        this.shelfService = shelfService;
        this.shelfFieldsUpdater = shelfFieldsUpdater;
    }

    public void updateShelf(Long shelfId, ShelfDto shelfDto) {
        Shelf shelf = shelfService.findOne(shelfId);
        shelfFieldsUpdater.updateShelfFields(shelf, shelfDto);
        shelfService.save(shelf);
    }
}

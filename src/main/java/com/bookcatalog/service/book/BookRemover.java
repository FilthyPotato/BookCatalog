package com.bookcatalog.service.book;

import com.bookcatalog.service.ShelfService;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookRemover {

    private ShelfService shelfService;

    public BookRemover(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    public void removeBookFromShelf(Long bookId, Long shelfId) {
        Shelf shelf = shelfService.findOne(shelfId);
        if (shelf != null) {
            removeBookFromShelf(bookId, shelf);
        }
    }

    public void removeBookFromAllUserShelves(Long bookId, UserProfile userProfile) {
        List<Shelf> shelves = userProfile.getShelves();
        shelves.forEach(shelf ->
                removeBookFromShelf(bookId, shelf));
    }

    private void removeBookFromShelf(Long bookId, Shelf shelf) {
        shelf.removeBook(bookId);
        shelfService.save(shelf);
    }
}

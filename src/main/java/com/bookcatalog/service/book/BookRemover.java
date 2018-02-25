package com.bookcatalog.service.book;

import com.bookcatalog.ShelfService;
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

    /*TODO remove this comment later
            Shelf N:M Book
                - remove record in SHELVES_BOOKS
                - if the book doesn't belong to any shelf, remove it
                - keep the DB up-to-date (this applies to every operation).
        */
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

package com.bookcatalog.service.book;

import com.bookcatalog.service.UserProfileService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.UserProfile;
import com.bookcatalog.validation.exceptions.ShelfDoesNotExistException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookFacade {
    private UserProfileService userProfileService;
    private BookRemover bookRemover;
    private BookFinder bookFinder;
    private BookUpdater bookUpdater;
    private BookAdder bookAdder;

    public BookFacade(UserProfileService userProfileService, BookRemover bookRemover, BookFinder bookFinder, BookUpdater bookUpdater, BookAdder bookAdder) {
        this.userProfileService = userProfileService;
        this.bookRemover = bookRemover;
        this.bookFinder = bookFinder;
        this.bookUpdater = bookUpdater;
        this.bookAdder = bookAdder;
    }

    private boolean shelfBelongsToUser(Long shelfId, String email) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        return userProfile.hasShelf(shelfId);
    }

    private boolean bookBelongsToUser(Long bookId, String email) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        return userProfile.hasBook(bookId);
    }

    public void removeBookFromShelf(String email, Long bookId, Long shelfId) {
        if (shelfBelongsToUser(shelfId, email)) {
            bookRemover.removeBookFromShelf(bookId, shelfId);
        }
    }

    public void removeBookFromAllUserShelves(Long bookId, String email) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        if (userProfile.hasBook(bookId)) {
            bookRemover.removeBookFromAllUserShelves(bookId, userProfile);
        }
    }

    public List<Book> findAllBooksDistinct(String email) {
        return bookFinder.findAllBooksDistinctSortById(email);
    }

    public Book findOneBook(Long bookId, String email) {
        if (bookBelongsToUser(bookId, email)) {
            return bookFinder.findOneBook(bookId);
        }

        return null;
    }

    public List<Book> findAllBooksOnShelf(Long shelfId, String email) {
        if (shelfBelongsToUser(shelfId, email)) {
            return bookFinder.findAllBooksOnShelf(shelfId);
        }

        return new ArrayList<>();
    }

    public void replaceBook(String email, Long bookId, BookDto bookDto) {
        if (bookBelongsToUser(bookId, email)) {
            bookUpdater.replaceBook(bookId, bookDto);
        }
    }

    public void updateBook(String email, Long bookId, BookDto bookDto) {
        if (bookBelongsToUser(bookId, email)) {
            bookUpdater.updateBook(bookId, bookDto);
        }
    }

    public Long addBookToShelf(String email, Long shelfId, BookDto bookDto) {
        if (shelfBelongsToUser(shelfId, email)) {
            return bookAdder.addBookToShelf(shelfId, bookDto);
        }

        return null;
    }

    public Long addBook(String email, NewBookDto newBookDto) {
        //if(allShelvesBelongToUser())
        UserProfile userProfile = userProfileService.findByEmail(email);
        List<Long> shelfIds = newBookDto.getShelfIds();
        shelfIds.forEach(id -> throwIfShelfDoesNotBelongToUser(id, userProfile));

        return bookAdder.addBook(newBookDto);
    }

    private void throwIfShelfDoesNotBelongToUser(Long shelfId, UserProfile userProfile) {
        if (!userProfile.hasShelf(shelfId)) {
            throw new ShelfDoesNotExistException(shelfId);
        }
    }
}

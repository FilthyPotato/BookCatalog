package com.bookcatalog.service.book;

import com.bookcatalog.BookService;
import com.bookcatalog.ShelfService;
import com.bookcatalog.UserProfileService;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
class BookFinder {

    private BookService bookService;
    private ShelfService shelfService;
    private UserProfileService userProfileService;

    BookFinder(BookService bookService, ShelfService shelfService, UserProfileService userProfileService) {
        this.bookService = bookService;
        this.shelfService = shelfService;
        this.userProfileService = userProfileService;
    }

    public List<Book> findAllBooksDistinctSortById(String email){
        UserProfile userProfile = userProfileService.findByEmail(email);
        return userProfile.getAllBooks().stream()
                .distinct()
                .sorted(Comparator.comparing(Book::getId))
                .collect(toList());
    }

    public Book findOneBook(Long bookId) {
        return bookService.findOne(bookId);
    }

    public List<Book> findAllBooksOnShelf(Long shelfId) {
        Shelf shelf = shelfService.findOne(shelfId);
        return shelf.getBooks();
    }
}

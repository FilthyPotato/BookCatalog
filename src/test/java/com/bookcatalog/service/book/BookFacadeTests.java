package com.bookcatalog.service.book;

import com.bookcatalog.service.BookService;
import com.bookcatalog.service.ShelfService;
import com.bookcatalog.service.UserProfileService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;
import com.bookcatalog.validation.exceptions.ShelfDoesNotExistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BookFacadeTests {

    @Mock
    private BookFacade bookFacade;
    @Mock
    private UserProfileService userProfileService;
    @Mock
    private ShelfService shelfService;
    @Mock
    private BookService bookService;
    @Mock
    private BookFieldsUpdater bookFieldsUpdater;
    private BookRemover bookRemover;
    private BookAdder bookAdder;
    private BookUpdater bookUpdater;
    private BookFinder bookFinder;
    private UserProfile userMain;
    private UserProfile userEmpty;
    private String emailMain = "main@test.com";
    private String emailEmpty = "empty@test.com";

    @Before
    public void setUp() {
        bookRemover = new BookRemover(shelfService);
        bookAdder = new BookAdder(bookService, shelfService);
        bookUpdater = new BookUpdater(bookService, bookFieldsUpdater);
        bookFinder = new BookFinder(bookService, shelfService, userProfileService);
        bookFacade = new BookFacade(userProfileService, bookRemover, bookFinder, bookUpdater, bookAdder);

        userMain = DatabaseBuilder.user(shelfService, bookService)
                .shelf(1L, 1L, 2L)
                .shelf(2L, 1L, 2L, 3L)
                .shelf(3L)
                .build();

        userEmpty = DatabaseBuilder.user(shelfService, bookService)
                .shelf(5L)
                .build();

        when(userProfileService.findByEmail(emailMain)).thenReturn(userMain);
        when(userProfileService.findByEmail(emailEmpty)).thenReturn(userEmpty);
    }

    @Test
    public void ownerHasBook_removeBookFromShelf_removeBook() {
        Shelf shelfIdOne = getShelf(userMain, 1L);

        bookFacade.removeBookFromShelf(emailMain, 1L, 1L);

        assertFalse(shelfIdOne.hasBook(1L));
        verify(shelfService).save(shelfIdOne);
    }

//    @Test
//    public void ownerHasBookButOnDifferentShelfThanSpecified_...() {
//    }

    //    @Test
//    public void ownerDoesNotHaveBook_removeBookFromShelf_doNothing() {
//        Shelf shelfIdOne = getShelf(userMain, 1L);
//
//        bookFacade.removeBookFromShelf(emailMain, 10L, 1L);
//
//        verify(shelfService, never()).save(shelfIdOne);
//    }

    @Test
    public void notOwner_removeBookFromShelf_doNothing() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).book(15L).build();
        when(shelfService.findOne(10L)).thenReturn(shelfIdTen);

        bookFacade.removeBookFromShelf(emailMain, 15L, 10L);

        assertTrue(shelfIdTen.hasBook(15L));
        verify(shelfService, never()).save(shelfIdTen);
    }

    @Test
    public void owner_removeBookFromAllUserShelves_removeBooks() {
        Shelf shelfIdOne = getShelf(userMain, 1L);
        Shelf shelfIdTwo = getShelf(userMain, 2L);

        bookFacade.removeBookFromAllUserShelves(1L, emailMain);

        assertFalse(userMain.hasBook(1L));
        verify(shelfService).save(shelfIdOne);
        verify(shelfService).save(shelfIdTwo);
    }

    @Test
    public void notOwner_removeBookFromAllUserShelves_doNothing() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).book(15L).build();
        when(shelfService.findOne(10L)).thenReturn(shelfIdTen);

        bookFacade.removeBookFromAllUserShelves(15L, emailMain);

        assertTrue(shelfIdTen.hasBook(15L));
        verify(shelfService, never()).save(shelfIdTen);
    }

    @Test
    public void ownerHasBooks_findAllBooksDistinct_returnAllBooks() {
        List<Book> allBooks = bookFacade.findAllBooksDistinct(emailMain);

        assertThat(allBooks, hasSize(3));
        assertThat(allBooks.get(0).getId(), is(1L));
        assertThat(allBooks.get(1).getId(), is(2L));
        assertThat(allBooks.get(2).getId(), is(3L));
    }

    @Test
    public void ownerDoesNotHaveBooks_findAllBooksDistinct_returnEmptyList() {
        List<Book> allBooks = bookFacade.findAllBooksDistinct(emailEmpty);

        assertThat(allBooks, empty());
    }


    @Test
    public void ownerHasBook_findOneBook_returnBook() {
        Book book = bookFacade.findOneBook(1L, emailMain);

        assertThat(book.getId(), is(1L));
    }

    @Test
    public void ownerDoesNotHaveBook_findOneBook_returnNull() {
        Book book = bookFacade.findOneBook(10L, emailMain);

        assertThat(book, is(nullValue()));
    }

    @Test
    public void notOwner_findOneBook_returnNull() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).book(15L).build();
        when(shelfService.findOne(10L)).thenReturn(shelfIdTen);
        when(bookService.findOne(15L)).thenReturn(shelfIdTen.getBook(15L).get());

        Book book = bookFacade.findOneBook(15L, emailMain);

        assertThat(book, is(nullValue()));
    }

    @Test
    public void ownerHasBooks_findAllBooksOnShelf_returnAllBooks() {
        Shelf shelfIdOne = getShelf(userMain, 1L);

        List<Book> allBooks = bookFacade.findAllBooksOnShelf(1L, emailMain);

        assertThat(allBooks, is(shelfIdOne.getBooks()));
    }

    @Test
    public void ownerDoesNotHaveBooksOnShelf_findAllBooksOnShelf_returnEmptyList() {
        List<Book> allBooks = bookFacade.findAllBooksOnShelf(3L, emailMain);

        assertThat(allBooks, is(empty()));
    }

    @Test
    public void notOwner_findAllBooksOnShelf_returnEmptyList() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).book(15L).build();
        when(shelfService.findOne(10L)).thenReturn(shelfIdTen);
        when(bookService.findOne(15L)).thenReturn(shelfIdTen.getBook(15L).get());

        List<Book> allBooks = bookFacade.findAllBooksOnShelf(10L, emailMain);

        assertThat(allBooks, is(empty()));
    }

    @Test
    public void owner_replaceBook_replaceBookDataOnAllShelves() {
        //Create new book, set id of the old, save = replace
        BookDto bookDto = new BookDto();
        Book book = new Book();
        book.setId(null);
        when(bookService.createFromDto(bookDto)).thenReturn(book);  //asserts that the data has been mapped

        bookFacade.replaceBook(emailMain, 1L, bookDto);

        //assert that a book with id 1L has been saved == book with id 1L in DB has been replaced
        //userProfile object that has this book on his shelves is probably left in inconsistent state
        //but it doesn't matter since I always fetch it anew with every request.
        assertThat(book.getId(), is(1L));
        verify(bookService).save(book);
    }

    @Test
    public void notOwner_replaceBook_doNothing() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).book(15L).build();
        when(bookService.findOne(15L)).thenReturn(shelfIdTen.getBook(15L).get());
        BookDto bookDto = mock(BookDto.class);

        bookFacade.replaceBook(emailMain, 15L, bookDto);

        verify(bookService, never()).save(any(Book.class));
    }

    @Test
    public void owner_updateBook_updateBookDataOnAllShelves() {
        Book book = getBook(userMain, 1L);
        BookDto bookData = mock(BookDto.class);

        bookFacade.updateBook(emailMain, 1L, bookData);

        verify(bookFieldsUpdater).updateBookFields(book, bookData); //asserts that the data has been mapped
        verify(bookService).save(book); // and that the book has been updated in DB
    }

    @Test
    public void notOwner_updateBook_doNothing() {
        BookDto bookData = mock(BookDto.class);

        bookFacade.updateBook(emailMain, 15L, bookData);

        verify(bookFieldsUpdater, never()).updateBookFields(any(Book.class), eq(bookData));
        verify(bookService, never()).save(any(Book.class));
    }

    @Test
    public void shelfOwner_addBookToShelf_addBookAndReturnItsId() {
        BookDto bookDto = new BookDto();
        Book newBook = new Book();
        Book savedBook = new Book();
        savedBook.setId(15L);
        when(bookService.createFromDto(bookDto)).thenReturn(newBook);
        when(bookService.save(newBook)).thenReturn(savedBook);

        Long newBookId = bookFacade.addBookToShelf(emailMain, 1L, bookDto);

        assertThat(newBookId, is(savedBook.getId()));
        assertTrue(userMain.hasBook(15L));
        verify(bookService).save(newBook);
    }

    @Test
    public void notShelfOwner_addBookToShelf_doNothingAndReturnNullAsId() {
        Shelf shelfIdTen = ShelfDataBuilder.shelf(10L).build();
        when(shelfService.findOne(10L)).thenReturn(shelfIdTen);
        BookDto bookDto = new BookDto();

        Long newBookId = bookFacade.addBookToShelf(emailMain, 10L, bookDto);

        assertThat(newBookId, is(nullValue()));
        verify(bookService, never()).createFromDto(bookDto);
        verify(bookService, never()).save(any(Book.class));
    }

    @Test
    public void ownerOfAllShelves_addBook_createOnlyOneBook() {
        NewBookDto newBookDto = new NewBookDto();
        when(bookService.save(any(Book.class))).thenReturn(new Book());

        bookFacade.addBook(emailMain, newBookDto);

        verify(bookService, times(1)).createFromDto(newBookDto);
        verify(bookService).save(any(Book.class));
    }

    @Test
    public void ownerOfAllShelves_addBook_addBookAndReturnItsId() {
        NewBookDto newBookDto = new NewBookDto();
        newBookDto.setShelfIds(Arrays.asList(1L, 3L));
        Book newBook = new Book();
        Book savedBook = new Book();
        savedBook.setId(15L);

        Shelf shelfIdOne = getShelf(userMain, 1L);
        Shelf shelfIdTwo = getShelf(userMain, 2L);
        Shelf shelfIdThree = getShelf(userMain, 3L);

        when(bookService.createFromDto(newBookDto)).thenReturn(newBook);
        when(bookService.save(newBook)).thenReturn(savedBook);

        Long newBookId = bookFacade.addBook(emailMain, newBookDto);

        assertThat(newBookId, is(savedBook.getId()));

        assertTrue(shelfIdOne.hasBook(15L));
        assertTrue(shelfIdThree.hasBook(15L));
        assertFalse(shelfIdTwo.hasBook(15L));

        verify(bookService).save(newBook);
        verify(shelfService).save(shelfIdOne);
        verify(shelfService).save(shelfIdThree);
        verify(shelfService, never()).save(shelfIdTwo);
    }

    @Test(expected = ShelfDoesNotExistException.class)
    public void notOwnerOFAllShelves_addBook_throwShelfDoesNotExistException() {
        when(shelfService.findOne(10L)).thenReturn(ShelfDataBuilder.shelf(10L).build());
        NewBookDto newBookDto = new NewBookDto();
        newBookDto.setShelfIds(Arrays.asList(1L, 3L, 10L));

        bookFacade.addBook(emailMain, newBookDto);
    }

    private Shelf getShelf(UserProfile userProfile, Long shelfId) {
        return userProfile.getShelf(shelfId).get();
    }

    private Book getBook(UserProfile userProfile, Long bookId) {
        return userProfile.getBook(bookId).get();
    }

    private static class ShelfDataBuilder {
        private Shelf shelf;

        static ShelfDataBuilder shelf(Long shelfId) {
            return new ShelfDataBuilder(shelfId);
        }

        private ShelfDataBuilder(Long shelfId) {
            shelf = new Shelf();
            shelf.setId(shelfId);
        }

        ShelfDataBuilder book(Long bookId) {
            Book book = new Book();
            book.setId(bookId);
            shelf.addBook(book);
            return this;
        }

        Shelf build() {
            return shelf;
        }

    }


    private static class DatabaseBuilder {
        private UserProfile userProfile;
        private Shelf lastShelf;
        private ShelfService shelfService;
        private BookService bookService;

        static DatabaseBuilder user(ShelfService shelfService, BookService bookService) {
            return new DatabaseBuilder(shelfService, bookService);
        }

        private DatabaseBuilder(ShelfService shelfService, BookService bookService) {
            userProfile = new UserProfile();
            this.shelfService = shelfService;
            this.bookService = bookService;
        }

        DatabaseBuilder shelf(Long shelfId) {
            Shelf shelf = new Shelf();
            shelf.setId(shelfId);
            userProfile.addShelf(shelf);
            lastShelf = shelf;

            when(shelfService.findOne(shelfId)).thenReturn(shelf);
            return this;
        }

        DatabaseBuilder shelf(Long shelfId, Long... bookIds) {
            shelf(shelfId);

            for (Long bookId : bookIds) {
                book(bookId);
            }

            return this;
        }

        DatabaseBuilder book(Long bookId) {
            Book book = new Book();
            book.setId(bookId);
            lastShelf.addBook(book);

            when(bookService.findOne(bookId)).thenReturn(book);
            return this;
        }

        UserProfile build() {
            return userProfile;
        }
    }
}

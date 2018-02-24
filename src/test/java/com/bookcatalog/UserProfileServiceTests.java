package com.bookcatalog;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.BookDtoConverter;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;
import com.bookcatalog.repository.UserProfileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserProfileServiceTests {
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private ShelfService shelfService;
    @Mock
    private BookService bookService;
    @Mock
    private BookDtoConverter bookDtoConverter;
    @InjectMocks
    private UserProfileService userProfileService;

    @Test
    public void whenFindAllBooksThenReturnAllBooks() {
        String email = "";
        List<Book> expected = Arrays.asList(mock(Book.class), mock(Book.class), mock(Book.class));
        when(userProfileRepository.findAllBooksDistinctOrderByBookId(email)).thenReturn(expected);

        List<Book> result = userProfileService.findAllBooksDistinct(email);

        assertEquals(result, expected);
    }

    @Test
    public void whenFindOneBookThenReturnOneBook() {
        String email = "";
        Long bookId = 1L;
        Book expected = mock(Book.class);
        when(userProfileRepository.findOneBook(email, bookId)).thenReturn(expected);

        Book result = userProfileService.findOneBook(email, bookId);

        assertEquals(result, expected);
    }

    @Test
    public void whenFindAllShelfNamesThenReturnAllShelfNames() {
        String email = "";
        List<String> expected = Arrays.asList("test", "test");
        when(userProfileRepository.findAllShelfNamesOrderByShelfId(email)).thenReturn(expected);

        List<String> result = userProfileService.findAllShelfNames(email);

        assertEquals(result, expected);
    }

    @Test
    public void whenFindBooksOnShelfThenReturnBooksOnShelf() {
        String email = "";
        Long shelfId = 1L;
        List<Book> expected = Arrays.asList(mock(Book.class), mock(Book.class));
        when(userProfileRepository.findBooksOnShelfOrderByBookId(email, shelfId)).thenReturn(expected);

        List<Book> result = userProfileService.findBooksOnShelf(email, shelfId);

        assertEquals(result, expected);
    }

    @Test
    public void findByEmail() {
        String email = "";
        UserProfile expected = mock(UserProfile.class);
        when(userProfileRepository.findByUserEmail(email)).thenReturn(expected);

        UserProfile result = userProfileService.findByEmail(email);

        assertThat(result, is(expected));
    }

    @Test
    public void save() {
        UserProfile expected = mock(UserProfile.class);
        when(userProfileRepository.save(expected)).thenReturn(expected);

        UserProfile result = userProfileService.save(expected);

        verify(userProfileRepository, times(1)).save(expected);
        assertThat(result, is(expected));
    }

    @Test
    public void addShelfAddsShelf() {
        String email = "";
        ShelfDto shelfDto = mock(ShelfDto.class);
        Shelf shelf = new Shelf();
        shelf.setId(3L);
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        when(shelfService.save(shelfDto)).thenReturn(shelf);

        userProfileService.addShelf(email, shelfDto);

        assertNotNull(userProfile.getShelf(3L).get());
    }

    @Test
    public void addShelfUpdatesUserProfile() {
        String email = "";
        UserProfile userProfile = mock(UserProfile.class);
        ShelfDto shelfDto = mock(ShelfDto.class);
        when(shelfService.save(shelfDto)).thenReturn(mock(Shelf.class));        // Bad practice?
        when(userProfileRepository.findByUserEmail(email)).thenReturn(userProfile);

        userProfileService.addShelf(email, shelfDto);

        verify(userProfileRepository).save(userProfile);
    }

    @Test
    public void addShelfReturnsDtoWithIdOfNewEntity() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        Shelf shelf = new Shelf();
        shelf.setId(10L);
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setId(null);
        when(shelfService.save(shelfDto)).thenReturn(shelf);
        when(userProfileRepository.findByUserEmail(email)).thenReturn(userProfile);

        ShelfDto result = userProfileService.addShelf(email, shelfDto);
        assertThat(result.getId(), is(shelf.getId()));
    }

    @Test
    public void removeBookFromShelfRemovesBookFromShelf() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);

        userProfileService.removeBookFromShelf(email, 1L, 1L);
        Shelf shelf = userProfile.getShelf(1L).get();
        Shelf shelf2 = userProfile.getShelf(2L).get();

        assertThat(shelf.getBooks(), hasSize(1));
        assertNotNull(shelf.getBook(2L).get());
        assertThat(shelf2.getBooks(), hasSize(2));
    }

    @Test
    public void removeBookFromShelfUpdatesUserProfileAndShelf() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        Shelf shelf = userProfile.getShelf(1L).get();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);

        userProfileService.removeBookFromShelf(email, 1L, 1L);

        verify(userProfileRepository).save(userProfile);
        verify(shelfService).save(shelf);
    }

    @Test
    public void updateShelfUpdatesShelfName() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        ShelfDto dto = new ShelfDto();
        dto.setName("newName");

        userProfileService.updateShelf(email, 1L, dto);
        Shelf shelf = userProfile.getShelf(1L).get();

        assertThat(shelf.getName(), is(dto.getName()));
    }

    @Test
    public void removeBookFromAllShelves() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        Long bookId = 1L;

        userProfileService.removeBookFromAllShelves(email, bookId);
        Shelf shelf1 = userProfile.getShelf(1L).get();
        Shelf shelf2 = userProfile.getShelf(2L).get();

        assertThat(shelf1.getBooks(), hasSize(1));
        assertNotNull(shelf1.getBook(2L).get());
        assertThat(shelf2.getBooks(), hasSize(1));
        assertNotNull(shelf2.getBook(2L).get());
    }

    @Test
    public void removeBookFromAllShelvesUpdatesUserProfileAndShelves() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        Long bookId = 1L;

        userProfileService.removeBookFromAllShelves(email, bookId);
        Shelf shelf1 = userProfile.getShelf(1L).get();
        Shelf shelf2 = userProfile.getShelf(2L).get();

        verify(shelfService).save(shelf1);
        verify(shelfService).save(shelf2);
        verify(userProfileRepository).save(userProfile);
    }

    @Test
    public void addBookToShelfAddsBookToShelf() {
        String email = "";
        BookDto bookDto = mock(BookDto.class);
        Book book = mock(Book.class);
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        when(bookService.save(bookDto)).thenReturn(book);
        long shelfId = 1L;

        userProfileService.addBookToShelf(email, shelfId, bookDto);
        Shelf shelf = userProfile.getShelf(shelfId).get();

        assertThat(shelf.getBooks(), hasItem(book));
    }

    @Test
    public void addBookToShelfUpdatesUserProfileAndShelves() {
        String email = "";
        BookDto bookDto = mock(BookDto.class);
        Book book = mock(Book.class);
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);
        when(bookService.save(bookDto)).thenReturn(book);

        userProfileService.addBookToShelf(email, 1L, bookDto);
        Shelf shelf = userProfile.getShelf(1L).get();

        verify(userProfileRepository).save(userProfile);
        verify(shelfService).save(shelf);
    }

    @Test
    public void addBookToShelfReturnsDtoWithIdOfNewEntity() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        Book book = new Book();
        book.setId(10L);
        BookDto bookDto = new BookDto();
        bookDto.setId(null);
        when(bookService.save(bookDto)).thenReturn(book);
        when(userProfileRepository.findByUserEmail(email)).thenReturn(userProfile);

        BookDto result = userProfileService.addBookToShelf(email, 1L, bookDto);

        assertThat(result.getId(), is(book.getId()));
    }

    @Test
    public void addBookShouldBeAddedToEverySpecifiedShelf() {
        String email = "";
        NewBookDto dto = new NewBookDto();
        dto.setShelfNames(Arrays.asList("shelf1", "shelf2"));
        Book book = mock(Book.class);
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        when(bookService.save(any(Book.class))).thenReturn(book);
        when(userProfileService.findByEmail(email)).thenReturn(userProfile);

        userProfileService.addBook(email, dto);
        Shelf shelf1 = userProfile.getShelf(1L).get();
        Shelf shelf2 = userProfile.getShelf(2L).get();

        assertThat(shelf1.getBooks(), hasItem(book));
        assertThat(shelf2.getBooks(), hasItem(book));
    }

    @Test
    public void addBookShouldCreateOnlyOneBook() {
        when(bookService.save(any(Book.class))).thenReturn(new Book());
        String email = "";
        NewBookDto dto = mock(NewBookDto.class);
        userProfileService.addBook(email, dto);

        verify(bookService, times(1)).save(any(Book.class));
    }

    @Test
    public void addBookReturnsDtoWithIdOfNewEntity() {
        String email = "";
        UserProfile userProfile = UserProfileDataGenerator.generateData();
        Book book = new Book();
        book.setId(10L);
        NewBookDto bookDto = new NewBookDto();
        bookDto.setId(null);
        when(bookService.save(any(Book.class))).thenReturn(book);
        when(userProfileRepository.findByUserEmail(email)).thenReturn(userProfile);

        BookDto result = userProfileService.addBook(email, bookDto);

        assertThat(result.getId(), is(book.getId()));
    }

    /*
    POST /book
    1. shelfNames cannot be empty (the book must belong to at least one shelf)
        - check via @NotEmpty
    2. the book should be added to every shelf in shelfNames. (Shelf's names should be unique

    @PostMapping("/book")
    public void addBook(@ResponseBody @Valid NewBookDto dto, BindingResult bindingResult, Principal principal){
        checkForErrors

        userProfileRepository.addBook(principal.getName(), dto);
    }


     */
}

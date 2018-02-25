package com.bookcatalog.api;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.service.book.BookFacade;
import com.bookcatalog.service.shelf.ShelfFacade;
import com.bookcatalog.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.security.Principal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShelfControllerTests {
    @Mock
    private BookFacade bookFacade;
    @Mock
    private ShelfFacade shelfFacade;
    private ShelfController shelfController;
    private Principal principal;
    @Mock
    private BindingResult bindingResult;
    @Mock
    private ShelfDto shelfDto;
    @Mock
    private BookDto bookDto;

    @Before
    public void setUp() {
        shelfController = new ShelfController(bookFacade, shelfFacade);
        principal = () -> "test";
    }

    @Test
    public void addShelfAddsNewShelfToDatabase() {
        when(bindingResult.hasErrors()).thenReturn(false);

        shelfController.addShelf(shelfDto, bindingResult, principal);

        verify(shelfFacade, times(1)).addShelf(principal.getName(), shelfDto);
    }

    @Test(expected = ValidationException.class)
    public void addShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.addShelf(shelfDto, bindingResult, principal);
    }

    @Test
    public void deleteBookFromShelfRemovesBook() {
        shelfController.deleteBookFromShelf(1L, 1L, principal);

        verify(bookFacade).removeBookFromShelf(principal.getName(), 1L, 1L);
    }

    @Test(expected = ValidationException.class)
    public void updateShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.updateShelf(shelfDto, bindingResult, 1L, principal);
    }

    @Test
    public void updateShelfUpdatesShelfName() {
        shelfController.updateShelf(shelfDto, bindingResult, 1L, principal);

        verify(shelfFacade).updateShelf(principal.getName(), 1L, shelfDto);
    }

    @Test
    public void addBookToShelf() {
        shelfController.addBookToShelf(bookDto, bindingResult, 1L, principal);

        verify(bookFacade).addBookToShelf(principal.getName(), 1L, bookDto);
    }

    @Test(expected = ValidationException.class)
    public void addBookToShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.addBookToShelf(bookDto, bindingResult, 1L, principal);
    }
}

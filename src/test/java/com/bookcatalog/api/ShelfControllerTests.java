package com.bookcatalog.api;

import com.bookcatalog.UserProfileService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import javax.naming.Binding;

import java.security.Principal;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShelfControllerTests {
    @Mock
    private UserProfileService userProfileService;
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
        shelfController = new ShelfController(userProfileService);
        principal = () -> "test";
    }

    @Test
    public void addShelfAddsNewShelfToDatabase() {
        when(bindingResult.hasErrors()).thenReturn(false);

        shelfController.addShelf(shelfDto, bindingResult, principal);

        verify(userProfileService, times(1)).addShelf(principal.getName(), shelfDto);
    }

    @Test(expected = ValidationException.class)
    public void addShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.addShelf(shelfDto, bindingResult, principal);
    }

    @Test
    public void deleteBookFromShelfRemovesBook() {
        shelfController.deleteBookFromShelf(1L, 1L, principal);

        verify(userProfileService).removeBookFromShelf(principal.getName(), 1L, 1L);
    }

    @Test(expected = ValidationException.class)
    public void updateShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.updateShelf(shelfDto, bindingResult, 1L, principal);
    }

    @Test
    public void updateShelfUpdatesShelfName() {
        shelfController.updateShelf(shelfDto, bindingResult, 1L, principal);

        verify(userProfileService).updateShelf(principal.getName(), 1L, shelfDto);
    }

    @Test
    public void addBookToShelf() {
        shelfController.addBookToShelf(bookDto, bindingResult, 1L, principal);

        verify(userProfileService).addBookToShelf(principal.getName(), 1L, bookDto);
    }

    @Test(expected = ValidationException.class)
    public void addBookToShelfThrowsExceptionWhenValidationErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        shelfController.addBookToShelf(bookDto, bindingResult, 1L, principal);
    }
}

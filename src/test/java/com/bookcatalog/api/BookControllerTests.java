package com.bookcatalog.api;

import com.bookcatalog.UserProfileService;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.BindingResult;

import java.security.Principal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookControllerTests {

    @Mock
    private UserProfileService userProfileService;
    @InjectMocks
    private BookController bookController;
    private Principal principal = () -> "test1@test.com";
    @Mock
    private BindingResult bindingResult;
    @Mock
    private NewBookDto newBookDto;
    @Test
    public void deleteBookFromAllShelves() {
        bookController.deleteBookFromAllShelves(1L, principal);

        verify(userProfileService).removeBookFromAllShelves(principal.getName(), 1L);
    }

    @Test
    public void addBook() {
        bookController.addBook(newBookDto, bindingResult, principal);

        verify(userProfileService).addBook(principal.getName(), newBookDto);

    }

    @Test(expected = ValidationException.class)
    public void addBookThrowsValidationExceptionWhenErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        bookController.addBook(newBookDto, bindingResult, principal);
    }
}

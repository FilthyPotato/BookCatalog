package com.bookcatalog.api;

import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.service.book.BookFacade;
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
    private BookFacade bookFacade;
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

        verify(bookFacade).removeBookFromAllUserShelves(1L, principal.getName());
    }

    @Test
    public void addBook() {
        bookController.addBook(newBookDto, bindingResult, principal);

        verify(bookFacade).addBook(principal.getName(), newBookDto);
    }

    @Test(expected = ValidationException.class)
    public void addBookThrowsValidationExceptionWhenErrors() {
        when(bindingResult.hasErrors()).thenReturn(true);

        bookController.addBook(newBookDto, bindingResult, principal);
    }
}

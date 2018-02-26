package com.bookcatalog.controllers;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.service.book.BookFacade;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class BookController {

    private BookFacade bookFacade;

    public BookController(BookFacade bookFacade) {
        this.bookFacade = bookFacade;
    }

    @GetMapping("/books")
    public List<Book> allBooks(Principal principal) {
        return bookFacade.findAllBooksDistinct(principal.getName());
    }

    @GetMapping("/book/{id}")
    public Book oneBook(@PathVariable Long id, Principal principal) {
        return bookFacade.findOneBook(id, principal.getName());
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromAllShelves(@PathVariable Long id, Principal principal) {
        bookFacade.removeBookFromAllUserShelves(id, principal.getName());
    }

    //TODO: not tested
    @PatchMapping("/book/{id}")
    public void updateBook(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        bookFacade.updateBook(principal.getName(), id, bookDto);
    }

    //TODO: not tested
    @PutMapping("/book/{id}")
    public void replaceBook(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        bookFacade.replaceBook(principal.getName(), id, bookDto);
    }

    @PostMapping("/book")
    public BookDto addBook(@RequestBody @Valid NewBookDto newBookDto, BindingResult bindingResult, Principal principal){
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        Long newBookId = bookFacade.addBook(principal.getName(), newBookDto);
        newBookDto.setId(newBookId);
        return newBookDto;
    }
}

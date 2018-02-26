package com.bookcatalog.api;

import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.service.book.BookFacade;
import com.bookcatalog.service.shelf.ShelfFacade;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class ShelfController {

    private BookFacade bookFacade;
    private ShelfFacade shelfFacade;

    public ShelfController(BookFacade bookFacade, ShelfFacade shelfFacade) {
        this.bookFacade = bookFacade;
        this.shelfFacade = shelfFacade;
    }

    @GetMapping("/shelves")
    public List<ShelfDto> getAllShelves(Principal principal) {
        return shelfFacade.findAllShelvesSortById(principal.getName());
    }

    @GetMapping("/shelf/{shelfId}/books")
    public List<Book> findAllBooksOnShelf(@PathVariable Long shelfId, Principal principal) {
        return bookFacade.findAllBooksOnShelf(shelfId, principal.getName());
    }

    @PostMapping("/shelf")
    public ShelfDto addShelf(@RequestBody @Valid ShelfDto shelfDto, BindingResult bindingResult, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        Long result = shelfFacade.addShelf(principal.getName(), shelfDto);
        shelfDto.setId(result);
        return shelfDto;
    }

    @DeleteMapping("/shelf/{shelfId}/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromShelf(@PathVariable Long shelfId, @PathVariable Long bookId, Principal principal) {
        bookFacade.removeBookFromShelf(principal.getName(), bookId, shelfId);
    }

    @PatchMapping("/shelf/{shelfId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateShelf(@RequestBody @Valid ShelfDto shelfDto, BindingResult bindingResult, @PathVariable Long shelfId, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        shelfFacade.updateShelf(principal.getName(), shelfId, shelfDto);
    }

    @PostMapping("/shelf/{shelfId}/book")
    public BookDto addBookToShelf(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long shelfId, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        Long newBookId = bookFacade.addBookToShelf(principal.getName(), shelfId, bookDto);

        bookDto.setId(newBookId);
        return bookDto;
    }

}

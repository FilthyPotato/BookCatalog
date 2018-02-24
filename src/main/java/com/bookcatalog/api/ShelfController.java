package com.bookcatalog.api;

import com.bookcatalog.ShelfService;
import com.bookcatalog.UserProfileService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.validation.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class ShelfController {

    private UserProfileService userProfileService;

    public ShelfController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/shelves")
    public List<String> getAllShelfNames(Principal principal) {
        return userProfileService.findAllShelfNames(principal.getName());
    }

    @GetMapping("/shelf/{id}")
    public List<Book> booksOnShelf(@PathVariable Long id, Principal principal) {
        return userProfileService.findBooksOnShelf(principal.getName(), id);
    }

    @PostMapping("/shelf")
    public ResponseEntity<ShelfDto> addShelf(@RequestBody @Valid ShelfDto shelfDto, BindingResult bindingResult, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        ShelfDto result = userProfileService.addShelf(principal.getName(), shelfDto);
        return ResponseEntity.ok(result);
    }

    //#removeBookFromShelf does nothing when ids do not exist for given user
    @DeleteMapping("/shelf/{shelfId}/book/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromShelf(@PathVariable Long shelfId, @PathVariable Long bookId, Principal principal) {
        userProfileService.removeBookFromShelf(principal.getName(), bookId, shelfId);
    }

    @PatchMapping("/shelf/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateShelf(@RequestBody @Valid ShelfDto shelfDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        userProfileService.updateShelf(principal.getName(), id, shelfDto);
    }

    @PostMapping("/shelf/{id}/book")
    public ResponseEntity<BookDto> addBookToShelf(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        BookDto result = userProfileService.addBookToShelf(principal.getName(), id, bookDto);
        return ResponseEntity.ok(result);
    }

}

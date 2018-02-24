package com.bookcatalog.api;

import com.bookcatalog.UserProfileService;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.model.Book;
import com.bookcatalog.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.security.Principal;
import java.util.List;

@RestController
public class BookController {

    private UserProfileService userProfileService;

    public BookController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping("/books")
    public List<Book> allBooks(Principal principal) {
        return userProfileService.findAllBooksDistinct(principal.getName());
    }

    @GetMapping("/book/{id}")
    public Book oneBook(@PathVariable Long id, Principal principal) {
        return userProfileService.findOneBook(principal.getName(), id);
    }

    @DeleteMapping("/book/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookFromAllShelves(@PathVariable Long id, Principal principal) {
        userProfileService.removeBookFromAllShelves(principal.getName(), id);
    }

    //TODO: not tested
    @PatchMapping("/book/{id}")
    public void updateBook(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        userProfileService.updateBook(principal.getName(), id, bookDto);
    }

    //TODO: not tested
    @PutMapping("/book/{id}")
    public void replaceBook(@RequestBody @Valid BookDto bookDto, BindingResult bindingResult, @PathVariable Long id, Principal principal) {
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        userProfileService.replaceBook(principal.getName(), id, bookDto);
    }

    @PostMapping("/book")
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid NewBookDto dto, BindingResult bindingResult, Principal principal){
        ControllerUtils.throwValidationExceptionIfErrors(bindingResult);

        BookDto result = userProfileService.addBook(principal.getName(), dto);
        return ResponseEntity.ok(result);
    }
}

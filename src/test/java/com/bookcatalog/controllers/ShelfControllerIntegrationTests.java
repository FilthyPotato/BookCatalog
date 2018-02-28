package com.bookcatalog.controllers;

import com.bookcatalog.dto.AuthorDto;
import com.bookcatalog.dto.BookDto;
import com.bookcatalog.dto.GenreDto;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.exceptionhandlers.ValidationExceptionHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class ShelfControllerIntegrationTests {
    private PojoUtils pojoUtils;
    private MockMvc mockMvc;
    @Autowired
    private ShelfController shelfController;
    private Principal principal = () -> "test1@test.com";

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(shelfController)
                .setControllerAdvice(new ValidationExceptionHandler())
                .build();
        pojoUtils = new PojoUtils(new ObjectMapper());
    }

    @Test
    public void allShelfNames() throws Exception {
        mockMvc.perform(get("/shelves")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Shelf1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Shelf2")));
    }

    @Test
    public void booksOnShelf() throws Exception {
        mockMvc.perform(get("/shelf/{id}/books", 1)
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void addShelfNoValidationErrors() throws Exception {
        String json = buildShelfDtoJson("testShelf");

        mockMvc.perform(post("/shelf")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(any(Number.class))))
                .andExpect(jsonPath("$.name", is("testShelf")));

    }

    @Test
    public void addShelfWithValidationErrors() throws Exception {
        String json = buildShelfDtoJson("");

        mockMvc.perform(post("/shelf")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is("may not be empty")));

    }

    @Test
    public void deleteBookFromShelf() throws Exception {
        mockMvc.perform(delete("/shelf/{shelfId}/book/{bookId}", 1L, 1L)
                .principal(principal))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void updateShelfNoErrors() throws Exception {
        String json = buildShelfDtoJson("testName");

        mockMvc.perform(patch("/shelf/{id}", 1L)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void updateShelfWithErrors() throws Exception {
        String json = buildShelfDtoJson("");

        mockMvc.perform(patch("/shelf/{id}", 1L)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("name")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is("may not be empty")));
    }

    @Test
    public void addBookToShelfNoErrors() throws Exception {
        BookDto bookDto = buildBookDto("title");
        String json = toJson(bookDto);

        mockMvc.perform(post("/shelf/{id}/book", 1L)
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(any(Number.class))))
                .andExpect(jsonPath("$.title", is(bookDto.getTitle())))
                .andExpect(jsonPath("$.description", is(bookDto.getDescription())))
                .andExpect(jsonPath("$.authors[0].name", is(bookDto.getAuthorDto(0).getName())))
                .andExpect(jsonPath("$.authors[1].name", is(bookDto.getAuthorDto(1).getName())))
                .andExpect(jsonPath("$.genres[0].name", is(bookDto.getGenreDto(0).getName())))
                .andExpect(jsonPath("$.genres[1].name", is(bookDto.getGenreDto(1).getName())))
                .andExpect(jsonPath("$.pages", is(bookDto.getPages())))
                .andExpect(jsonPath("$.publisher", is(bookDto.getPublisher())))
                .andExpect(jsonPath("$.publishedDate", is(bookDto.getPublishedDate())));
    }

    @Test
    //TODO
    @Ignore
    public void addBookToShelfWithErrors() {
        fail();
    }

    private BookDto buildBookDto(String title) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(title);
        bookDto.setDescription("description");
        bookDto.setAuthors(Arrays.asList(
                new AuthorDto("author1"),
                new AuthorDto("author2")
        ));
        bookDto.setGenres(Arrays.asList(
                new GenreDto("genre1"),
                new GenreDto("genre2")
        ));
        bookDto.setPages(100);
        bookDto.setPublisher("publisher");
        bookDto.setPublishedDate("2015-12-12");

        return bookDto;
    }

    private String toJson(Object object) throws JsonProcessingException {
        return pojoUtils.toJson(object);
    }

    private String buildShelfDtoJson(String name) throws JsonProcessingException {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setName(name);
        return toJson(shelfDto);
    }
}

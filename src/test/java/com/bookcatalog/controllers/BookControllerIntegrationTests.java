package com.bookcatalog.controllers;

import com.bookcatalog.dto.NewBookDto;
import com.bookcatalog.exceptionhandlers.ValidationExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:test.properties")
public class BookControllerIntegrationTests {
    private MockMvc mockMvc;
    private PojoUtils pojoUtils;
    @Autowired
    private BookController bookController;
    private Principal principal = () -> "test1@test.com";

    @Before
    public void setUp() {
        mockMvc = standaloneSetup(bookController)
                .setControllerAdvice(new ValidationExceptionHandler())
                .build();
        pojoUtils = new PojoUtils(new ObjectMapper());
    }

    @Test
    public void allBooks() throws Exception {
        mockMvc.perform(get("/books")
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[2].id", is(3)));
    }

    @Test
    public void oneBook() throws Exception {
        mockMvc.perform(get("/book/{id}", 1)
                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title1")));

    }

    @Test
    public void deleteBookFromAllShelves() throws Exception {
        mockMvc.perform(delete("/book/{id}", 1L)
                .principal(principal))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }

    @Test
    public void addBookNoErrors() throws Exception {
        NewBookDto dto = new NewBookDto();
        dto.setTitle("title");
        dto.setShelfIds(Arrays.asList(1L, 2L));
        String json = pojoUtils.toJson(dto);

        mockMvc.perform(post("/book")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(dto.getTitle())))
                .andExpect(jsonPath("$.shelfIds[0]", is(1)))
                .andExpect(jsonPath("$.shelfIds[1]", is(2)));
    }

    @Test
    public void addBookWithErrors() throws Exception {
        NewBookDto dto = new NewBookDto();
        dto.setTitle("title");
        dto.setShelfIds(null);
        String json = pojoUtils.toJson(dto);

        mockMvc.perform(post("/book")
                .principal(principal)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.fieldErrors", hasSize(1)))
                .andExpect(jsonPath("$.fieldErrors[0].field", is("shelfIds")))
                .andExpect(jsonPath("$.fieldErrors[0].message", is("may not be empty")));
    }
}

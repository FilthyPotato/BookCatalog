package com.bookcatalog.repository;

import com.bookcatalog.model.Book;
import com.bookcatalog.model.UserProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Matchers.notNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class UserProfileRepositoryTests {

    @Autowired
    private UserProfileRepository userProfileRepository;
    private String email = "test1@test.com";

    @Test
    @Transactional
    public void findAllBooksDistinctOrderByBookId() {
        UserProfile userProfile = userProfileRepository.findByUserEmail(email);
        List<Book> expected = userProfile.getShelves().stream()
                .flatMap(s -> s.getBooks().stream())
                .distinct()
                .collect(toList());
        List<Book> books = userProfileRepository.findAllBooksDistinctOrderByBookId(email);
        assertThat(books, hasSize(3));
        assertEquals(books, expected);
    }

    @Test
    @Transactional
    public void findOne() {
        UserProfile userProfile = userProfileRepository.findByUserEmail(email);
        long bookId = 1L;
        Book expected = userProfile.getShelves().stream()
                .flatMap(shelf -> shelf.getBooks().stream())
                .filter(b -> b.getId().equals(bookId))
                .findFirst().get();

        Book book = userProfileRepository.findOneBook(email, bookId);

        assertThat(book, is(notNullValue()));
        assertThat(book, is(expected));
    }

    @Test
    public void findAllShelfNamesOrderByShelfId() {
        List<String> expected = Arrays.asList("Shelf1", "Shelf2");

        List<String> shelfNames = userProfileRepository.findAllShelfNamesOrderByShelfId(email);

        assertEquals(shelfNames, expected);
    }

    @Test
    @Transactional
    public void givenShelfExistsFindBooksOnShelfOrderByBookId() {
        Long shelfId = 1L;
        UserProfile userProfile = userProfileRepository.findByUserEmail(email);
        List<Book> expected = userProfile.getShelves().stream()
                .filter(s -> s.getId().equals(shelfId))
                .flatMap(s -> s.getBooks().stream())
                .collect(toList());

        List<Book> booksOnShelf = userProfileRepository.findBooksOnShelfOrderByBookId(email, shelfId);

        assertThat(booksOnShelf, is(expected));

    }

    @Test
    public void givenShelfDoesNotExistReturnZeroBooks() {
        Long shelfId = 99L;
        List<Book> expected = new ArrayList<>();

        List<Book> result = userProfileRepository.findBooksOnShelfOrderByBookId(email, shelfId);

        assertEquals(result, expected);
    }
}

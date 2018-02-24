package com.bookcatalog.repository;

import com.bookcatalog.model.Book;
import com.bookcatalog.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query(value = "SELECT DISTINCT b FROM UserProfile p left JOIN  p.shelves s left JOIN s.books b where p.user.email = :userEmail ORDER BY b.id")
    List<Book> findAllBooksDistinctOrderByBookId(@Param("userEmail") String userEmail);

    @Query(value = "SELECT b FROM UserProfile p JOIN p.shelves s JOIN s.books b where p.user.email = :userEmail " +
            "and b.id = :bookId")
    Book findOneBook(@Param("userEmail") String userEmail, @Param("bookId") Long bookId);

    @Query(value = "SELECT s.name FROM UserProfile p JOIN p.shelves s where p.user.email = :userEmail ORDER BY s.id")
    List<String> findAllShelfNamesOrderByShelfId(@Param("userEmail") String userEmail);

    @Query(value = "SELECT b FROM UserProfile p JOIN p.shelves s JOIN s.books b where p.user.email = :userEmail and s.id = :shelfId ORDER BY b.id")
    List<Book> findBooksOnShelfOrderByBookId(@Param("userEmail") String userEmail, @Param("shelfId") Long shelfId);

    UserProfile findByUserEmail(String email);
}

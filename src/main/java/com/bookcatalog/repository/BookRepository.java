package com.bookcatalog.repository;

import com.bookcatalog.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long>{
}

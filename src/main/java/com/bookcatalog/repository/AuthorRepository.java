package com.bookcatalog.repository;

import com.bookcatalog.model.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long>{
}

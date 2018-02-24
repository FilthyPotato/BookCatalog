package com.bookcatalog.repository;

import com.bookcatalog.model.Shelf;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShelfRepository extends CrudRepository<Shelf, Long>{
    Optional<Shelf> findByName(String name);
}

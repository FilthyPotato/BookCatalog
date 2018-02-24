package com.bookcatalog;

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.repository.ShelfRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShelfService {

    private ShelfRepository shelfRepository;
    private ShelfDtoConverter shelfDtoConverter;

    public ShelfService(ShelfRepository shelfRepository, ShelfDtoConverter shelfDtoConverter) {
        this.shelfRepository = shelfRepository;
        this.shelfDtoConverter = shelfDtoConverter;
    }

    public Shelf save(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    public boolean shelfExists(String name) {
        return findByName(name).isPresent();
    }

    public Shelf save(ShelfDto shelfDto) {
        Shelf shelf = shelfDtoConverter.createFromDto(shelfDto);
        return this.save(shelf);
    }

    public Optional<Shelf> findByName(String name) {
        return shelfRepository.findByName(name);
    }

}

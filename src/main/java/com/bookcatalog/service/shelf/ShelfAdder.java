package com.bookcatalog.service.shelf;

import com.bookcatalog.service.UserProfileService;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;
import org.springframework.stereotype.Service;

@Service
class ShelfAdder {

    private ShelfDtoConverter shelfDtoConverter;
    private ShelfService shelfService;
    private UserProfileService userProfileService;

    ShelfAdder(ShelfDtoConverter shelfDtoConverter, ShelfService shelfService, UserProfileService userProfileService) {
        this.shelfDtoConverter = shelfDtoConverter;
        this.shelfService = shelfService;
        this.userProfileService = userProfileService;
    }

    public Long addShelf(UserProfile userProfile, ShelfDto shelfDto) {
        Shelf shelf = createShelfFromDtoAndSave(shelfDto);

        userProfile.addShelf(shelf);
        userProfileService.save(userProfile);

        return shelf.getId();
    }

    private Shelf createShelfFromDtoAndSave(ShelfDto shelfDto) {
        Shelf shelf = shelfDtoConverter.fromDto(shelfDto);
        return shelfService.save(shelf);
    }
}

package com.bookcatalog.service.shelf;

import com.bookcatalog.service.UserProfileService;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShelfFacade {

    private ShelfAdder shelfAdder;
    private ShelfFinder shelfFinder;
    private ShelfRemover shelfRemover;
    private ShelfUpdater shelfUpdater;
    private UserProfileService userProfileService;

    public ShelfFacade(ShelfAdder shelfAdder, ShelfFinder shelfFinder, ShelfRemover shelfRemover, ShelfUpdater shelfUpdater, UserProfileService userProfileService) {
        this.shelfAdder = shelfAdder;
        this.shelfFinder = shelfFinder;
        this.shelfRemover = shelfRemover;
        this.shelfUpdater = shelfUpdater;
        this.userProfileService = userProfileService;
    }

    public Long addShelf(String email, ShelfDto shelfDto) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        return shelfAdder.addShelf(userProfile, shelfDto);
    }


    public List<ShelfDto> findAllShelvesSortById(String email) {
        return shelfFinder.findAllShelvesSortById(email);
    }

    public void updateShelf(String email, Long shelfId, ShelfDto shelfDto) {
        if (shelfBelongsToUser(email, shelfId)) {
            shelfUpdater.updateShelf(shelfId, shelfDto);
        }
    }

    private boolean shelfBelongsToUser(String email, Long shelfId) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        return userProfile.hasShelf(shelfId);
    }
}

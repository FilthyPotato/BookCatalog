package com.bookcatalog.service.shelf;

import com.bookcatalog.UserProfileService;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.UserProfile;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
class ShelfFinder {
    private UserProfileService userProfileService;
    private ShelfDtoConverter shelfDtoConverter;

    ShelfFinder(UserProfileService userProfileService, ShelfDtoConverter shelfDtoConverter) {
        this.userProfileService = userProfileService;
        this.shelfDtoConverter = shelfDtoConverter;
    }

    public List<ShelfDto> findAllShelvesSortById(String email) {
        UserProfile userProfile = userProfileService.findByEmail(email);
        return userProfile.getShelves().stream()
                .map(shelf -> shelfDtoConverter.toDto(shelf))
                .sorted(Comparator.comparing(ShelfDto::getId))
                .collect(toList());
    }
}

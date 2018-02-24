package com.bookcatalog;

import com.bookcatalog.model.Book;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.model.UserProfile;

import java.util.stream.LongStream;

public class UserProfileDataGenerator {
    public static UserProfile generateData() {
        UserProfile userProfile = new UserProfile();
        LongStream.rangeClosed(1, 2).forEachOrdered(i -> {
            Shelf shelf = new Shelf();
            shelf.setId(i);
            shelf.setName("shelf" + i);
            userProfile.addShelf(shelf);

            LongStream.rangeClosed(1, 2).forEachOrdered(j -> {
                Book book = new Book();
                book.setId(j);
                shelf.addBook(book);
            });
        });
        return userProfile;
    }
}

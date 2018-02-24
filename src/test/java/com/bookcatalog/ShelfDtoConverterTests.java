package com.bookcatalog; 

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.Shelf;
import org.junit.Test;
import org.junit.Before;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ShelfDtoConverterTests {

    private ShelfDtoConverter shelfDtoConverter;

    @Before
    public void setUp() {
        shelfDtoConverter = new ShelfDtoConverter();
    }

    @Test
    public void shelfHasAllFieldsSet() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setName("testName");

        Shelf result = shelfDtoConverter.createFromDto(shelfDto);

        assertThat(result.getName(), is(shelfDto.getName()));
    }
}

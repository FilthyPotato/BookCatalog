package com.bookcatalog.dto;

import com.bookcatalog.config.SpringConfig;
import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.Shelf;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ShelfDtoConverterTests {

    private ShelfDtoConverter shelfDtoConverter;

    @Before
    public void setUp() {
        SpringConfig springConfig = new SpringConfig();
        shelfDtoConverter = new ShelfDtoConverter(springConfig.modelMapper());
    }

    @Test
    public void shelfHasAllFieldsSet() {
        ShelfDto shelfDto = new ShelfDto();
        shelfDto.setName("testName");

        Shelf result = shelfDtoConverter.fromDto(shelfDto);

        assertThat(result.getName(), is(shelfDto.getName()));
    }
}

package com.bookcatalog;

import com.bookcatalog.dto.ShelfDto;
import com.bookcatalog.dto.ShelfDtoConverter;
import com.bookcatalog.model.Shelf;
import com.bookcatalog.repository.ShelfRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ShelfServiceTests {
    @Mock
    private ShelfRepository shelfRepository;
    @Mock
    private ShelfDtoConverter shelfDtoConverter;
    @InjectMocks
    private ShelfService shelfService;

    @Test
    public void save() {
        Shelf expected = mock(Shelf.class);
        when(shelfRepository.save(expected)).thenReturn(expected);

        Shelf result = shelfService.save(expected);

        verify(shelfRepository, times(1)).save(expected);
        assertThat(result, is(expected));
    }

    @Test
    public void saveShelfDtoSavesShelfEntityToDatabase() {
        ShelfDto dto = mock(ShelfDto.class);
        Shelf expected = mock(Shelf.class);
        when(shelfDtoConverter.fromDto(dto)).thenReturn(expected);

        shelfService.save(dto);

        verify(shelfRepository, times(1)).save(expected);
    }

    @Test
    public void saveShelfDtoReturnsSavedEntity() {
        ShelfDto dto = mock(ShelfDto.class);
        Shelf expected = mock(Shelf.class);
        when(shelfDtoConverter.fromDto(dto)).thenReturn(expected);
        when(shelfRepository.save(expected)).thenReturn(expected);

        Shelf result = shelfService.save(dto);

        assertThat(result, is(expected));
    }

}

package com.bookcatalog.dto;

import com.bookcatalog.AnnotationUtils;
import org.junit.Test;

public class BookDtoTests {

    @Test
    public void titleHasNotEmptyAnnotation() throws NoSuchFieldException {
        AnnotationUtils.hasNotEmptyAnnotation(BookDto.class, "title");
    }
}

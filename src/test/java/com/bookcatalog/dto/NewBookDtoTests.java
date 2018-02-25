package com.bookcatalog.dto;

import com.bookcatalog.AnnotationUtils;
import org.junit.Test;

public class NewBookDtoTests {

    @Test
    public void shelfNamesHasNotEmptyAnnotation() throws NoSuchFieldException {
        AnnotationUtils.hasNotEmptyAnnotation(NewBookDto.class, "shelfIds");
    }
}

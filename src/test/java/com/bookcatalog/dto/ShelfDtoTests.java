package com.bookcatalog.dto;

import org.junit.Test;

import static com.bookcatalog.AnnotationUtils.hasNotEmptyAnnotation;

public class ShelfDtoTests {

    @Test
    public void nameHasNotEmptyAnnotation() throws NoSuchFieldException {
        hasNotEmptyAnnotation(ShelfDto.class,"name");
    }
}

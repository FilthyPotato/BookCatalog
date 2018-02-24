package com.bookcatalog.dto;

import com.bookcatalog.AnnotationUtils;
import org.junit.Test;

public class GenreDtoTests {

    @Test
    public void nameHasNotEmptyAnnotation() throws NoSuchFieldException {
        AnnotationUtils.hasNotEmptyAnnotation(GenreDto.class, "name");
    }
}

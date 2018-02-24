package com.bookcatalog.dto;

import com.bookcatalog.AnnotationUtils;
import org.junit.Test;

public class AuthorDtoTests {

    @Test
    public void nameHasNotEmptyAnnotation() throws NoSuchFieldException {
        AnnotationUtils.hasNotEmptyAnnotation(AuthorDto.class, "name");
    }
}

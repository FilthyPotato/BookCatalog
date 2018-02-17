package com.bookcatalog;

import org.hibernate.validator.constraints.NotEmpty;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AnnotationUtils {

    public static <T extends Annotation, U> void hasAnnotation(Class<U> type, String fieldName, Class<T> annotationClass) throws NoSuchFieldException {
        Annotation annotation = getAnnotation(type,fieldName, annotationClass);
        assertThat(annotation, notNullValue());
    }

    public static <T extends Annotation, U> Annotation getAnnotation(Class<U> type, String fieldName, Class<T> annotationClass) throws NoSuchFieldException {
        Field field = type.getDeclaredField(fieldName);
        return field.getAnnotation(annotationClass);
    }

    public static <U> void hasNotEmptyAnnotation(Class<U> type, String fieldName) throws NoSuchFieldException {
        hasAnnotation(type, fieldName, NotEmpty.class);
    }
}

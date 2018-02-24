package com.bookcatalog.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PojoUtils {
    private ObjectMapper objectMapper;

    public PojoUtils(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
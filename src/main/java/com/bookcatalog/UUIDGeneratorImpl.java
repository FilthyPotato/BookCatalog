package com.bookcatalog;

import java.util.UUID;

public class UUIDGeneratorImpl implements UUIDGenerator {
    @Override
    public String generateUUID() {
        return UUID.randomUUID().toString();
    }
}

package com.project.cinemamanagement.Ultility;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class UUIDGenerator {
    public static UUID generateUUID(String input) {
        byte[] bytes = input.getBytes(StandardCharsets.UTF_8);
        return UUID.nameUUIDFromBytes(bytes);
    }
}

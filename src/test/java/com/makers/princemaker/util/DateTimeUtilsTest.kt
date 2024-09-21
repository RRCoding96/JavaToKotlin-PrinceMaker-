package com.makers.princemaker.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DateTimeUtilsTest {
    @Test
    void getLocalDateTimeStringTest() {
        String result = DateTimeUtils.getLocalDateTimeString(
                LocalDateTime.of(2023, 12, 21, 10, 10)
        );

        assertEquals("2023-12-21 탄생", result);
    }
}
package com.makers.princemaker.util

import com.makers.princemaker.util.DateTimeUtils.getLocalDateTimeString
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DateTimeUtilsTest {
    @Test
    fun localDateTimeStringTest() {
        val result = getLocalDateTimeString(
            LocalDateTime.of(2023, 12, 21, 10, 10)
        )
        Assertions.assertEquals("2023-12-21 탄생", result)
    }
}
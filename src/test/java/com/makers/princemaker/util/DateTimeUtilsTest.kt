package com.makers.princemaker.util

import com.makers.princemaker.util.DateTimeUtils.getLocalDateTimeString
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

internal class DateTimeUtilsTest : StringSpec({

    "탄생일 출력 검증" {
        val result = getLocalDateTimeString(
            LocalDateTime.of(2023, 12, 21, 10, 10)
        )

        result shouldBe "2023-12-21 탄생"
    }
})
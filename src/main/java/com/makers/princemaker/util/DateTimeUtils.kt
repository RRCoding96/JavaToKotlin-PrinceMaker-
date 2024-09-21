package com.makers.princemaker.util;


import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateTimeUtils {
    public static String getLocalDateTimeString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter
                .ofPattern("yyyy-MM-dd 탄생"));
    }
}

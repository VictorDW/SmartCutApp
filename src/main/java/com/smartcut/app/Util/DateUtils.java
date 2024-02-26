package com.smartcut.app.Util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private DateUtils() {}

    public static String dateFormat(LocalDateTime localDateTime) {
        return localDateTime.format(formatter);
    }
}

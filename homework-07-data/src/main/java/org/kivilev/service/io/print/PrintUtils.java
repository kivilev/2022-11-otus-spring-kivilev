package org.kivilev.service.io.print;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrintUtils {
    public static String dateToString(LocalDate date, DateTimeFormatter formatter) {
        return date == null ? "" : date.format(formatter);
    }

    public static String dateTimeToString(LocalDateTime date, DateTimeFormatter formatter) {
        return date == null ? "" : date.format(formatter);
    }
}

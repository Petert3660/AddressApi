package com.ptconsultancy.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtils {

    public static String getDateString(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }

    public static String getTimeAsString(LocalDateTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return time.format(formatter);
    }
}

package com.cloudflare.hellourld.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static boolean isFutureDate(String dateStr){
        LocalDateTime dateTime = LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME);
        return(dateTime.isAfter(LocalDateTime.now()));
    }
}

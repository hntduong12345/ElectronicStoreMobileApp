package com.example.electronicstoremobileapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Helpers {
    static String format = "dd/MM/yyyy HH:mm:ss";
    public static String getDateTime(String datetimeString) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getDateString(Date date, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    public static String returnEmptyStringOrValue(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}

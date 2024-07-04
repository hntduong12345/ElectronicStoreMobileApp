package com.example.electronicstoremobileapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Helpers {
    public static String getDateTime(String formater, String datetimeString) {
        DateFormat dateFormat = new SimpleDateFormat();
        Date date = new Date();
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

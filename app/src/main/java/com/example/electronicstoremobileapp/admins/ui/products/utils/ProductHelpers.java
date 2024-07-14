package com.example.electronicstoremobileapp.admins.ui.products.utils;

import android.util.Log;
import android.widget.ImageView;

import com.example.electronicstoremobileapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ProductHelpers {
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalDate getDateNow(){
        return LocalDate.now();
    }
    public static LocalTime getTimeNow(){
        return LocalTime.now();
    }
    public static LocalDateTime getDateTimeNow(){
        return LocalDateTime.now();
    }
    public static LocalDateTime parseFromDateTimeString(String localDateTimeString){
        return LocalDateTime.parse(localDateTimeString, DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
    public static LocalDate parseFromDateString(String localDate){
        return LocalDate.parse(localDate,DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
    public static LocalTime parseFromTimeString(String localTime){
        return LocalTime.parse(localTime,DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
    public static String getFormatedTimeString(LocalTime localTime){
        return localTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }
    public static String getFormatedDateString(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
    public static String getFormatedDateTimeString(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern(DATETIME_FORMAT));
    }
    public static String getDateFromParam(int year, int month, int day){
        LocalDate date = LocalDate.of(year,month,day);
        String parsedString = getFormatedDateString(date);
        return parsedString;
    }
    public static String getTimeFromParam(int hour, int minute){
        LocalTime time = LocalTime.of(hour,minute);
        String parsedString = getFormatedTimeString(time);
        return parsedString;
    }

    public static boolean isDateTimeAfterOrEqualNow_WithSpanHour(LocalTime time, LocalDate date,int hourMargin){
        LocalDateTime dateTime = LocalDateTime.of(date, time);
        LocalDateTime dateTimeNow = getDateTimeNow();
        if(dateTime.isBefore(dateTimeNow) || dateTime.isEqual(dateTimeNow)){
            return false;
        }
        if(dateTime.isAfter(dateTimeNow)){
            dateTime = dateTime.plusHours(hourMargin);
            if(dateTime.isAfter(dateTimeNow) || dateTime.isEqual(dateTimeNow)){
                return true;
            }
        }
        return false;
    }
    public static void setImageWithWebUrl(String url, ImageView imageView){
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_notifications_black_24dp)
                .error(R.drawable.ic_launcher_foreground)
                .fit()
                .into(imageView);
    }
}

package com.dev4lazy.notepad2.utils;

import android.icu.text.SimpleDateFormat;
//import android.net.ParseException;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Date long2Date( Long timeStamp) {
        return new Date(timeStamp);
    }

    @TypeConverter
    public Long date2Long(Date date) {
        return date.getTime();
    }

    @TypeConverter
    public String date2String( Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); //"yyyy-MM-dd HH:mm:ss"
        return dateFormat.format(date);
    }

    public Date string2Date( String dateInString) throws ParseException {
        String datePattern = "yyyy-MM-dd";
        return string2DateWithFormat(dateInString,datePattern);
    }

    public Date string2DateWithFormat( String dateInString, String dateFPattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFPattern);
        return dateFormat.parse(dateInString);
    }

}
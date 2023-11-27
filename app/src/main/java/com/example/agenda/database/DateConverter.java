package com.example.agenda.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long milliSeconds){
        return milliSeconds==null?null:new Date(milliSeconds);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date==null?null:date.getTime();
    }
}

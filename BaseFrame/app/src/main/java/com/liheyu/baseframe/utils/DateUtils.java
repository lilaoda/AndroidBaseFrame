package com.liheyu.baseframe.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lilaoda on 2016/10/24.
 */
public class DateUtils {
    public static final String dateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        String time = simpleDateFormat.format(date);
        return time;
    }

    public static long getCurrenTimestemp() {
        return new Date().getTime();
    }

    public static long getCurrenTimestemp(String time) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date date = new Date();
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getCurrentTime(Long timestemp){
        Date date = new Date(timestemp);
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(date);
    }
}

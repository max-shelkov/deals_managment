package com.gecko.dealsmanagment;

import java.util.Calendar;

public class GeckoUtils {
    public static String dateCalendarToString(Calendar date){
        if (date == null) return "no date";
        StringBuilder sb = new StringBuilder();
        int day = date.get(Calendar.DAY_OF_MONTH);
        if(day<10){
            sb.append("0");
        }
        sb.append(day).append(".");
        int month = date.get(Calendar.MONTH)+1;
        if (month < 10){
            sb.append("0");
        }
        sb.append(month).append(".");
        sb.append(date.get(Calendar.YEAR));
        return sb.toString();
    }

    public static String monthCalendarToString(Calendar date){
        if (date == null) return "no date";
        StringBuilder sb = new StringBuilder();

        int month = date.get(Calendar.MONTH)+1;
        if (month < 10){
            sb.append("0");
        }
        sb.append(month).append(".");
        sb.append(date.get(Calendar.YEAR));
        return sb.toString();
    }
}

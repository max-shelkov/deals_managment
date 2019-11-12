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

    public static Calendar msXlsDateToCalendar(int msXlsDate){
        Calendar date = Calendar.getInstance();
        date.set(1900,00,01);
        date.add(Calendar.DAY_OF_MONTH, msXlsDate-2);
        return date;
    }

    public static String formattedInt(int num){

        /*
        * разделить входящее число на 100 с остатком
        * целую часть - в рубли
        * остаток от деления - в копейки*/

        String res = "" + num;
        int size = res.length();

/*
        StringBuilder sb = new StringBuilder();
        sb.append(res.substring(0, size-2));
        sb.append(",");
        sb.append(res.substring(size-2));
*/
        int rub = num / 100;
        int kop = num % 100;
        StringBuilder sb = new StringBuilder();
        sb.append(rub).append(",").append(kop);
        if (kop == 0) sb.append("0");

        int index = sb.toString().length();
        index = index-6;

        while(index > 0){
            sb.insert(index, " ");
            index -=3;
        }


        return sb.toString();
    }

    public static int msXlsCellToInt(String strNum){
//        strNum = strNum.replace(" ","");
        int res = 0;
        int multiplicator=0;
        if (strNum.contains(".")){
            int length = strNum.length();
            int index = strNum.indexOf(".");
            multiplicator = length - index;
            strNum = strNum.replace(".","");
        }
        res = Integer.parseInt(strNum);
        switch (multiplicator){
            case 0: res*=100;   break;
            case 1: res*=100;   break;
            case 2: res*=10;    break;
            case 3:             break;

        }
        return res;
    }
}

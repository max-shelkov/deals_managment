package com.gecko.dealsmanagment;

import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class GeckoUtils {

    public static final String TAG = "myLog";

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
        sb.append(rub).append(",");
        if (kop >= 0 && kop <= 9) sb.append("0");
        sb.append(kop);

        int index = sb.toString().length();
        index = index-6;

        while(index > 0){
            sb.insert(index, " ");
            index -=3;
        }
        return sb.toString();
    }

    public static String intToMoneyString(int sum){



        return null;
    }

    public static int msXlsCellToInt(String strNum){
        strNum = strNum.replace(" ","");
        strNum = strNum.replace(",",".");
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

    public static int monthsBetween(Calendar start, Calendar finish){
//        Calendar current = Calendar.getInstance();
        int months = 0;
//        Log.d(TAG, "finish month = " + finish.get(Calendar.MONTH) + " start month = " +start.get(Calendar.MONTH));
        if (start.get(Calendar.YEAR) == finish.get(Calendar.YEAR)&&finish.get(Calendar.MONTH) >= start.get(Calendar.MONTH)){
            months = finish.get(Calendar.MONTH) - start.get(Calendar.MONTH) +1;
        }

        if (start.get(Calendar.YEAR) < finish.get(Calendar.YEAR)){
            int x = (finish.get(Calendar.YEAR) - start.get(Calendar.YEAR)) * 12;
            int p1 = (x - start.get(Calendar.MONTH));
            int p2 = (finish.get(Calendar.MONTH)+1);
            Log.d(TAG, "p1 = " + p1 + " p2 = " + p2);
            months = p1+p2;
        }

        if (start.get(Calendar.YEAR) > finish.get(Calendar.YEAR)){
            months = -1;
        }

        if (start.get(Calendar.YEAR) == finish.get(Calendar.YEAR)&& finish.get(Calendar.MONTH) < start.get(Calendar.MONTH)){
            months = -1;
        }
        return months;
    }


}

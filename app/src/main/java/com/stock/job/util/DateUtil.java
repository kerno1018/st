package com.stock.job.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kerno on 2016/4/9.
 */
public class DateUtil {
    private static Long startMDate = null;
    private static Long endMDate = null;
    private static Long startADate = null;
    private static Long endADate = null;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
    private static volatile Calendar calendar = null;

    public static synchronized void  init() {
//        if(calendar != null){
//            return;
//        }
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        String startMorningTime = day.format(new Date()) + " 09:30:00";
        String endMorningTime = day.format(new Date()) + " 11:30:00";
        String startAfternoonTime = day.format(new Date()) + " 13:00:00";
        String endAfternoonTime = day.format(new Date()) + " 15:00:00";
        try {
            startMDate = format.parse(startMorningTime).getTime();
            endMDate = format.parse(endMorningTime).getTime();
            startADate = format.parse(startAfternoonTime).getTime();
            endADate = format.parse(endAfternoonTime).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Boolean isValidTime(){
        long time = new Date().getTime();
        return isValidDay() && ((time >= startMDate && time <= endMDate) || (time >= startADate && time < endADate));
    }

    public static Boolean isValidDay(){
        if(calendar == null){
            init();
        }
        return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY;
    }

    public static Boolean isValidLoginEndTime(){
        Long time = new Date().getTime();
        return isValidDay() && time < endADate;
    }

    public static void main(String[] args) {
        init();
        System.out.println(isValidDay());
    }

}

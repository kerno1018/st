package com.stock.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kerno on 1/15/2016.
 */
public class CacluateUtil {
    private static DecimalFormat format = new DecimalFormat("#0.0000");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat dateFormat4 = new SimpleDateFormat("yMd HH:mm:ss");
    private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-");

    public static String format(Double value){
        return format.format(value);
    }

    public static Double format(Double value,int fix){
        BigDecimal b = new BigDecimal(value);
        return b.setScale(fix, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String format(Date date){
        return dateFormat.format(date);
    }

    public static String shortFormat(Date date){
        return dateFormat4.format(date);
    }

    public static String getDateByMD(String md){
        return dayFormat.format(new Date())+md;
    }
    public static Date getToday(){
        try {
            return dateFormat2.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date convertToDate(String str){
        try {
            return dateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String getTodayAsParam(){
        return dateFormat3.format(new Date());
    }
    public static Date format(String date){
        try {
            return dateFormat2.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(dateFormat4.format(new Date()));

    }

}

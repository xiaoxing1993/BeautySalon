package com.wiipu.beautysalon_new_v2.utils;

import java.text.SimpleDateFormat;

/**
 * Created by Ken~Jc on 2016/3/22.
 */
public class GetDatesUtil {

    public static String getDatesAll(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date=simpleDateFormat.format(new java.util.Date());
        return date;
    }
    public static String getDatesYM(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM");
        String date=simpleDateFormat.format(new java.util.Date());
        return date;
    }
    public static String getOrderId(){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddhhmm");
        return simpleDateFormat.format(new java.util.Date());
    }

}

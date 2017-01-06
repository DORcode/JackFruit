package com.jackfruit.mall.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Stats on 2016-12-02.
 */

public class DateUtils {

    //两个时间差
    public static int dateDiff(long date1, long date2) {
        long intervalMilli = date1 - date2;
        return (int) intervalMilli / (24 * 60 * 60 * 1000);
    }

    public static long removeHMS(long now) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(now)).getTime();
    }

    public static long removeHMS() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(new Date().getTime())).getTime();
    }

    public static String getDatetime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:sss");
        return sdf.format(new Date().getTime());
    }
}

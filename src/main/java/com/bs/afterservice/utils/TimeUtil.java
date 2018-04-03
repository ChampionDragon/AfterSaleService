package com.bs.afterservice.utils;

import com.bs.afterservice.constant.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Description: 时间相关工具类
 * AUTHOR: Champion Dragon
 * created at 2018/3/9
 **/

public class TimeUtil {
    /**
     * 以指定字符串形式返回当前系统时间
     */
    public static String getSystem() {
        SimpleDateFormat format = new SimpleDateFormat(Constant.cformatsecond, Locale.getDefault());
        Date date = new Date(System.currentTimeMillis());
        String s = format.format(date);
        return s;
    }

    /**
     * 时间戳转成指定字符串
     */
    public static String long2time(long time, String formatStyle) {
        SimpleDateFormat format = new SimpleDateFormat(formatStyle,Locale.getDefault());
        Date date = new Date(time);
        String s = format.format(date);
        return s;
    }

    /**
     * 指定字符串转为时间戳
     *
     * @param time
     *            指定字符串
     * @param formatStyle
     *            字符串格式
     */
    public static long time2long(String time, String formatStyle) {
        SimpleDateFormat format = new SimpleDateFormat(formatStyle,Locale.getDefault());
        Date date;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            date = new Date();//如果格式错误就返回当前时间搓
            e.printStackTrace();
            Logs.d(e.getMessage());
        }
        return date.getTime();

    }

    /**
     * 根据一段时间戳返回耗时时间字符
     */
    public static String long2time(long time) {
        String str = "";
        long h = 60 * 60 * 1000;
        long m = 60 * 1000;
        long s = 1000;
        if (time > h) {
            long hh = time / h;
            long mm = time / m;
            long ss = time / s;
            str = hh + "小时" + mm + "分钟" + ss + "秒";
        } else if (time > m) {
            long mm = time / m;
            long ss = time / s;
            str = mm + "分钟" + ss + "秒";
        } else {
            long ss = time / s;
            str = ss + "秒";
        }

        return str;
    }
}

package com.bs.afterservice.utils;

import android.util.Log;

/**
 * Description: LOG的工具类
 * AUTHOR: Champion Dragon
 * created at 2018/2/23
 **/

public class Logs {
    private static final String TAG = "lcb";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        Log.i(TAG, msg);
    }

    public static void d(String msg) {
        Log.d(TAG, msg);
    }

    public static void e(String msg) {
        Log.e(TAG, msg);
    }

    public static void w(String msg) {
        Log.w(TAG, msg);
    }

    public static void v(String msg) {
        Log.v(TAG, msg);
    }

    // 下面是传入类名打印log
    public static void i(Class<?> _class, String msg) {
        Log.i(_class.getName(), msg);
    }

    public static void d(Class<?> _class, String msg) {
        Log.d(_class.getName(), msg);
    }

    public static void e(Class<?> _class, String msg) {
        Log.e(_class.getName(), msg);
    }

    public static void w(Class<?> _class, String msg) {
        Log.w(_class.getName(), msg);
    }

    public static void v(Class<?> _class, String msg) {
        Log.v(_class.getName(), msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {
        Log.v(tag, msg);
    }
}

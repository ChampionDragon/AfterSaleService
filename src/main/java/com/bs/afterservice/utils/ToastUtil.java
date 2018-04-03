package com.bs.afterservice.utils;

import android.widget.Toast;

import com.bs.afterservice.base.BaseApplication;

/**
 * Description:吐司的工具类
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/

public class ToastUtil {
    private static Toast toast;

    public static void showShort(int a) {
        String s = BaseApplication.getInstance().getResources().getString(a);
        showShort(s);
    }

    public static void showShort(String s) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), s,
                    Toast.LENGTH_SHORT);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(s);
        toast.show();
    }

    public static void showLong(int a) {
        String s = BaseApplication.getInstance().getResources().getString(a);
        showLong(s);
    }

    public static void showLong(String s) {
        if (toast == null) {
            toast = Toast.makeText(BaseApplication.getInstance(), s,
                    Toast.LENGTH_LONG);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setText(s);
        toast.show();
    }
}

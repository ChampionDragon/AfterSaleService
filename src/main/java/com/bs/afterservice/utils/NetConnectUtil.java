package com.bs.afterservice.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bs.afterservice.base.BaseApplication;

/**
 * Description: 网络工具类
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/

public class NetConnectUtil {
    static Context context = BaseApplication.context;
    static ConnectivityManager manager = (ConnectivityManager) context
            .getSystemService(Context.CONNECTIVITY_SERVICE);

    /**
     * 判断WIFI是否连接
     */
    public static boolean WIFI(Context context) {
        NetworkInfo networkInfo = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnectedOrConnecting();
    }

    /**
     * 判断MOBILE是否连接
     */
    public static boolean Mobile(Context context) {
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo.getType()!=ConnectivityManager.TYPE_MOBILE) {
            return false;
        }
        return networkInfo.isConnectedOrConnecting();
    }

    /**
     * 判断是否有网
     */
    public static boolean NetConnect(Context context) {
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        }
        return networkInfo.isConnectedOrConnecting();
    }
}

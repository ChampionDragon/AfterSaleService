package com.bs.afterservice.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiManager;

import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.db.DbManager;
import com.bs.afterservice.utils.SpUtil;
import com.bs.afterservice.utils.ToastUtil;

import java.util.Stack;

/**
 * Description: 基程序
 * AUTHOR: Champion Dragon
 * created at 2018/2/23
 **/

public class BaseApplication extends Application {
    private static BaseApplication mInstance = null;
    public static Context context;
    private long firsttime;
    public SpUtil sp;
    public DbManager managerDb;
    public static WifiManager.MulticastLock lock;
    public static WifiManager wfm;

    public static BaseApplication getInstance() {
        if (mInstance == null) {
            mInstance = new BaseApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (activityStack == null) {
            activityStack = new Stack<>();
        }
        if (activityStackTem == null) {
            activityStackTem = new Stack<>();
        }

        mInstance = this;
        context = getApplicationContext();
        sp = SpUtil.getInstance(SpKey.SP_name, MODE_PRIVATE);
        initUDP();
        managerDb = DbManager.getmInstance(this, Constant.dbDiveceBsmk, Constant.dbVersion);
    }

    private void initUDP() {
        // 有的手机不能直接接收UDP包，可能是手机厂商在定制Rom的时候把这个功能给关掉了。实例化一个WifiManager.MulticastLock
        // 对象lock, 在调用广播发送、接收报文之前先调用lock.acquire()方法；
        // 用完之后及时调用lock.release()释放资源，否决多次调用lock.acquire()方法，程序可能会崩.
        wfm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        lock = wfm.createMulticastLock("wifi lcb");
    }

    /**
     * 退出整个程序
     */
    public void exitApp() {
        long secondtime = System.currentTimeMillis();
        if (secondtime - firsttime > 1000) {
            ToastUtil.showLong("再点一次就退出哦！");
            firsttime = secondtime;
        } else {
            onTerminate();
        }
    }


    @Override
    public void onTerminate() {
        //杀死栈里面的所有Activity
        finishAllActivity();
        System.exit(0);
        super.onTerminate();
    }


    /* ---------------------------------自定义activity管理栈------------------------------------------------------------- */
    private static Stack<Activity> activityStack, activityStackTem;

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
//        Logs.v("添加activity：    " + activity);
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
//        Logs.w("移除activity：  " + activity);
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
//            Logs.d("106当前activity数量：" + activityStack.size());
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }


    //添加Activity到临时的堆栈
    public void TemaddActivity(Activity activity) {
        activityStackTem.add(activity);
    }

    //结束临时堆栈所有Activity
    public void TemfinishAllActivity() {
        for (int i = 0, size = activityStackTem.size(); i < size; i++) {
            if (null != activityStackTem.get(i)) {
                activityStackTem.get(i).finish();
            }
        }
//        Logs.w("157当前activity数量：" + activityStackTem.size());
        activityStackTem.clear();
//        Logs.d("159当前activity数量：" + activityStackTem.size());
    }

    //结束临时堆栈指定的Activity
    public void TemfinishActivity(Activity activity) {
        if (activity != null) {
            activityStackTem.remove(activity);
            activity.finish();
            activity = null;
//            Logs.e("168当前activity数量：" + activityStackTem.size());
        }
    }


}

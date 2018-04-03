package com.bs.afterservice.utils;

import android.content.Context;

import com.bs.afterservice.base.BaseApplication;
import com.bs.afterservice.bean.UserInfo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Description: 对象存储
 * AUTHOR: Champion Dragon
 * created at 2018/2/26
 **/

public class ObjectSave {
    private static UserInfo info;

    /**
     * 读取文件存储数据
     *
     * @return 返回数据对象
     */
    public static Object getObject(String name) {
        FileInputStream fi = null;
        ObjectInputStream oi = null;
        try {
            fi = BaseApplication.context.openFileInput(name);
            oi = new ObjectInputStream(fi);
            return oi.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fi != null) {
                try {
                    fi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oi != null) {
                try {
                    oi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将指定文件存储到系统文件夹中
     *
     * @param name   文件名
     * @param object 文件对象
     */
    public static void SaveObject(String name, Object object) {
        FileOutputStream fo = null;
        ObjectOutputStream oo = null;
        try {
            fo = BaseApplication.context.openFileOutput(name,
                    Context.MODE_PRIVATE);
            oo = new ObjectOutputStream(fo);
            oo.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fo != null) {
                try {
                    fo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (oo != null) {
                try {
                    oo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 得到UserInfo类的对象
     */
    public static UserInfo getUserInfo() {
        if (info == null) {
            info = (UserInfo) getObject("userinfo.dat");
            if (info == null) {
                info = new UserInfo();
            }
        }
        return info;
    }

    /**
     * 存储UserInfo类的对象
     */
    public static void SaveUserInfo(UserInfo userInfo) {
        if (info == null) {
            info = (UserInfo) getObject("userinfo.dat");
            if (info == null) {
                info = new UserInfo();
            }
        }
        info = userInfo;
        SaveObject("userinfo.dat", info);

    }
}

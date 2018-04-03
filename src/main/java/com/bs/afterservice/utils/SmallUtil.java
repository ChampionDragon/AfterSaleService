package com.bs.afterservice.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;

import com.bs.afterservice.constant.Constant;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/**
 * Description: 创建小工具类
 * AUTHOR: Champion Dragon
 * created at 2018/2/23
 **/

public class SmallUtil {
    /**
     * 直接跳转到某个界面
     */
    public static void getActivity(Activity a, Class<?> cls) {
        a.startActivity(new Intent(a, cls));
    }

    /**
     * 携带bundle值跳转到某个界面
     */
    public static void getActivity(Activity a, Class<?> cls, Bundle bundle) {
        a.startActivity(new Intent(a, cls).putExtras(bundle));
    }

    /**
     * 将传入的int值转变为网络地址.例如:192.168.0.1
     */
    public static String intToString(int a) {
        StringBuffer sb = new StringBuffer();
        int b = (a >> 0) & 0xff;
        sb.append(b + ".");
        b = (a >> 8) & 0xff;
        sb.append(b + ".");
        b = (a >> 16) & 0xff;
        sb.append(b + ".");
        b = (a >> 24) & 0xff;
        sb.append(b);
        return sb.toString();
    }

    public static String Str2hexStr(String str) {
        return Str2hexStr(str, "");
    }

    /**
     * 字符串转换为16进制字符串
     *
     * @param charsetName 用于编码 String 的 Charset
     */
    public static String Str2hexStr(String str, String charsetName) {
        byte[] bytes = new byte[0];
        String hexString = "0123456789abcdef";
        // 使用给定的 charset 将此 String 编码到 byte 序列
        if (!charsetName.equals("")) {
            try {
                bytes = str.getBytes(charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // 根据默认编码获取字节数组
            bytes = str.getBytes();
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f)));
        }
        return sb.toString();
    }

    public static String hexStr2Str(String hexStr) {
        return hexStr2Str(hexStr, null);
    }

    /**
     * 16进制字符串转换为字符串
     *
     * @param charsetName 用于编码 String 的 Charset
     */
    public static String hexStr2Str(String hexStr, String charsetName) {
        hexStr = hexStr.toUpperCase();
        // hexStr.replace(" ", "");
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xFF);
        }
        String returnStr = "";// 返回的字符串
        if (charsetName == null) {
            // 编译器默认解码指定的 byte 数组，构造一个新的 String,
            // 比如我的集成开发工具即编码器android studio的默认编码格式为"utf-8"
            returnStr = new String(bytes);
        } else {
            // 指定的 charset 解码指定的 byte 数组，构造一个新的 String
            // utf-8中文字符占三个字节，GB18030兼容GBK兼容GB2312中文字符占两个字节，ISO8859-1是拉丁字符（ASCII字符）占一个字节
            try {
                returnStr = new String(bytes, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        // charset还有utf-8,gbk随需求改变
        return returnStr;
    }

    /**
     * bytes转换成十六进制字符串
     *
     * @param b byte数组
     */
    public static String Bytes2hexStr(byte[] b) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");

        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            // sb.append(" ");//每个Byte值之间空格分隔
        }

        return sb.toString().toUpperCase().trim();

    }

    /**
     * 十六进制字符串转bytes
     *
     * @param src 16进制字符串
     * @return 字节数组
     */
    public static byte[] hexStr2Bytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = (byte) Integer
                    .valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }
        return ret;
    }

    /**
     * @param root 文件目录
     * @param path 碰到此路径停止删除
     */
    public static void FileAlldelete(File root, String path) {
        File[] roots = File.listRoots();
        if (roots != null) {
            for (File file : roots) {
                if (file.getAbsolutePath().equals(path))
                    continue;
                if (file.isDirectory()) {
                    FileAlldelete(file, path);
                    file.delete();
                } else {
                    if (file.exists())
                        file.delete();
                }
            }
        }
    }

    /**
     * long转byte数组
     */
    public static byte[] long2Bytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(x);
        return buffer.array();
    }

    /**
     * byte数组转long
     */
    public static long bytes2Long(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();

    }

    /**
     * byte转成int
     */
    public static int byte2Int(byte b) {
        // Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值  
        return b & 0xff;
    }

    /**
     * byte数组转int
     */
    public static int bytes2Int(byte[] b) {
        // return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
        // | (b[0] & 0xff) << 24;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put(b);
        buffer.flip();
        return buffer.getInt();
    }

    /**
     * int转byte数组
     */
    public static byte[] int2Bytes(int a) {

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(a);
        return buffer.array();
    }


    /**
     * 判定字符串是否是数字
     */
    public static boolean isNumer(String str) {
        for (int i = 0; i < str.length(); i++) {
           /*str.charAt(i)返回指定索引处的 char 值。*/
            if (!Character.isDigit(str.charAt(i))) {
                /*Character.isDigit 确定指定字符是否为数字*/
                return false;
            }
        }
        return true;
    }


    /**
     * 返回字符串指定部分
     *
     * @param father 原字符串长度
     * @param index  所要字符串之前的字符串 id:
     * @param length 所需要字符串的长度  BDYYIJG=7
     *               比如"small:sada  id:BDYYIJG" 结果BDYYIJG
     */
    public static String getPartString(String father, String index, int length) {
        String children = "";
        int start = father.indexOf(index);
        Logs.d("smallUtil 248  " + start);
        start = start + index.length();
        children = father.substring(start, start + length);
        Logs.w("smallUtil 251  " + children);
        return children;
    }

    /**
     * 将指定的activity设置成透明导航栏和透明状态栏
     *
     * @param activity 设置的对象
     */
    public static void setScreen(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }


    /*创建文件夹*/
    public static boolean setFile(String path) {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            String rootPath = Environment.getExternalStorageDirectory().toString();
            File f = new File(new File(rootPath, Constant.fileRoot), path);
            if (!f.exists()) {
                try {
//                    mkdir()   只能在已经存在的目录中创建创建文件夹 
//                    mkdirs()   可以在不存在的目录中创建文件夹
                    f.mkdirs();
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /*判断文件是否存在*/
    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }


    /**
     * 设置添加屏幕的背景透明度
     */
    public static void backgroundAlpaha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow()
                .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }


}

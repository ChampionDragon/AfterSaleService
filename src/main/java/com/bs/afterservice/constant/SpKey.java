package com.bs.afterservice.constant;

/**
 * Description: sharedpreferences的常量值
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/

public class SpKey {
    // sharedpreferences表名
    public static final String SP_name = "baisheng";//整个APP其余的SP
    public static final String SP_user = "user";//关于用户的SP

    /*user表的key值*/
    //switchButton的sp
    public static final String user_fingerprint = "fingerprint";//指纹登录
    public static final String user_news = "news";//新消息推送
    public static final String user_voice = "voice";//声音
    public static final String user_vibration = "vibration";//震动
    //欢迎界面的sp
    public static final String isFirst = "isFirst";
    public static final String isLogin = "Login";
    public static final String preVer = "preVer";
    public static final String UserName = "username";//用户名关系到相关数据的读取


}

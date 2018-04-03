package com.bs.afterservice.constant;

import android.os.Environment;

import com.bs.afterservice.utils.SystemUtil;

import java.io.File;

/**
 * Description: 常量类
 * AUTHOR: Champion Dragon
 * created at 2018/2/23
 **/

public class Constant {
    /* 文件夹 */
    // 整个项目的目录
    public final static String fileRoot = SystemUtil.AppName();//app名字
    public final static File fileDir = new File(
            Environment.getExternalStorageDirectory(), fileRoot);
    public final static String license = "营业执照";
    public final static String filehead = "头像";
    public final static String temp = "缓存";


    /* 时间格式 */
    public final static String cformatDay = "yyyy年MM月dd日";
    public final static String cformatD = "M月d日";
    public final static String cformatsecond = "yyyy年MM月dd日HH时mm分ss秒";
    public final static String formatminute = "HH:mm";
    public final static String formatsecond = "yyyy-MM-dd HH:mm:ss";
    public final static String formatlogin = "yyyy/MM/dd HH:mm";//登录记录的格式
    public final static String formatnotify = "yyyy/MM/dd HH:mm:ss";//通知记录的格式
    public final static String formatbusinesstime = "yyyy/MM/dd";//通知记录的格式


    /* 数据库 */
    public final static String dbDiveceBsmk = "BSService";
    public final static int dbVersion = 1;


    /*关于“服务反馈”的信息*/
    public final static String OP_applicant = "applicant";//申请人
    public final static String OP_taskattr = "taskattribute";//任务属性
    public final static String OP_time = "time";//申请时间
    public final static String OP_businesstime = "businesstime";//出差时间
    public final static String OP_businessplace = "businessplace";//出差时间
    public final static String OP_businessperson = "businessperson";//出差人员
    public final static String OP_personpeer = "personpeer";//同行人员
    public final static String OP_customername = "customername";//客户名称
    public final static String OP_customerphone = "customerphone";//联系电话
    public final static String OP_office = "office";//办事处
    public final static String OP_officephone = "officephone";//办事处电话
    public final static String OP_cost = "cost";//预支费用
    public final static String OP_costactual = "costactual";//实际费用
    public final static String OP_busroute = "busroute";//乘车路线
    public final static String OP_maldescription = "maldescription";//故障描述
    public final static String OP_malreason = "malreason";//故障原因
    public final static String OP_maldeal = "maldeal";//故障处理
    public final static String OP_partsname = "partsname";//故障配件名称
    public final static String OP_partsmodel = "partsmodel";//故障配件型号
    public final static String OP_partsischange = "partsischange";//是否更换
    public final static String OP_partsnum = "partsnum";//配件数量
    public final static String OP_partsnote = "partsnote";//备注


}

package com.bs.afterservice.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.bs.afterservice.bean.CustserBean;
import com.bs.afterservice.bean.LoginBean;
import com.bs.afterservice.utils.Logs;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 数据库管理类
 * AUTHOR: Champion Dragon
 * created at 2018/3/10
 **/

public class DbManager {
    public static DbManager mInstance = null;
    private DbHelper mDbHelper = null;
    private Context mContext = null;

    public DbManager(Context context, String dbName, int dbVersion) {
        super();
        mContext = context;
        mDbHelper = DbHelper.getInstance(context, dbName, dbVersion);
    }

    public static DbManager getmInstance(Context context, String dbName,
                                         int dbVersion) {
        if (mInstance == null) {
            mInstance = new DbManager(context, dbName, dbVersion);
        }
        return mInstance;
    }

/* +++++++++++++++++++++++++++   关于登录记录的数据库操作  ++++++++++++++++++++++++++*/

    /**
     * 插入|更新登录数据
     */
    public boolean addOrUpdateLogin(String name, String Time) {
        ContentValues values = new ContentValues();
        values.put(DbHelper.LOGIN_NAME, name);
        String sql = "select *from " + DbHelper.TABLE_LOGIN
                + " where " + DbHelper.LOGIN_TIME + "=?";
        Cursor cursor = mDbHelper.rawQuery(sql, new String[]{Time});
        Logs.w("cursor总数：" + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                mDbHelper.update(DbHelper.TABLE_LOGIN, values,
                        DbHelper.LOGIN_TIME + "=?", new String[]{Time + ""});
                Logs.i("dm54  login Update");
            }
        } else {
            values.put(DbHelper.LOGIN_TIME, Time);
            mDbHelper.insert(DbHelper.TABLE_LOGIN, values);
            Logs.i("dm59  login Insert");
        }
        cursor.close();
        return true;
    }

    /**
     * 删除登录记录
     */
    public boolean cleanLogin(String whereClause, String[] whereArgs) {
        mDbHelper.delete(DbHelper.TABLE_LOGIN, whereClause, whereArgs);
        Logs.d("dbm70  login delete");
        return true;
    }


    /**
     * 返回登录数据的集合
     */
    public List<LoginBean> getLoginList() {
        List<LoginBean> list = new ArrayList<>();
        Cursor cursor = mDbHelper.query(DbHelper.TABLE_LOGIN, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                LoginBean loginBean = new LoginBean();
                loginBean.setName(cursor.getString(cursor.getColumnIndex(DbHelper.LOGIN_NAME)));
                loginBean.setTime(cursor.getString(cursor.getColumnIndex(DbHelper.LOGIN_TIME)));
                loginBean.set_id(cursor.getInt(cursor.getColumnIndex(DbHelper.ID)));
                list.add(loginBean);
            }
        }
        Logs.e("dbm90  login Sum " + cursor.getCount());
        cursor.close();
        return list;
    }


    /* +++++++++++++++++++++++++++   关于售后人员的数据库操作  ++++++++++++++++++++++++++*/


    /**
     * 添加 指定用户的 售后人员信息
     */
    public boolean addOrUpdateCustser(String Username, String name) {
        String sql = "select *from " + DbHelper.TABLE_CUSTSER +
                " where " + DbHelper.User + "=? and " + DbHelper.CUSTSER_NAME + "=?";
        Cursor cursor = mDbHelper.rawQuery(sql, new String[]{Username, name});
        Logs.w("cursor大小 " + cursor.getCount());

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
//                mDbHelper.update(DbHelper.TABLE_CUSTSER, values,
//                        DbHelper.CUSTSER_NAME + "=?", new String[]{name});
                Logs.i("dm110  custser Update");
            }
        } else {
            ContentValues values = new ContentValues();
            values.put(DbHelper.User, Username);
            values.put(DbHelper.CUSTSER_NAME, name);
            mDbHelper.insert(DbHelper.TABLE_CUSTSER, values);
            Logs.i("dm115  custser Insert");
        }
        cursor.close();
        return true;
    }


    /**
     * 删除 售后人员 记录
     */
    public boolean cleanCustser(String whereClause, String[] whereArgs) {
        mDbHelper.delete(DbHelper.TABLE_CUSTSER, whereClause, whereArgs);
        Logs.d("dbm127  custser delete");
        return true;
    }

    /**
     * 删除 指定用户 指定的 售后人员记录
     */
    public boolean custserClean(String username, String name) {
        mDbHelper.delete(DbHelper.TABLE_CUSTSER, DbHelper.User + "=? and " + DbHelper.CUSTSER_NAME + "=?",
                new String[]{username, name});
        Logs.i("dbm137  custserUser delete");
        return true;
    }

    /**
     * 删除 指定用户 所有的 售后人员记录
     */
    public boolean custserClean(String username) {
        String whereClause = DbHelper.User + "=" + username;
        mDbHelper.delete(DbHelper.TABLE_CUSTSER, whereClause, null);
        Logs.i("dbm137  custserUser delete");
        return true;
    }


    /*得到所有用户的售后人员信息*/
    public List<CustserBean> getAllCustser() {
        List<CustserBean> list = new ArrayList<>();
        Cursor cursor = mDbHelper.query(DbHelper.TABLE_CUSTSER, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CustserBean custserBean = new CustserBean();
                custserBean.setUsername(cursor.getString(cursor.getColumnIndex(DbHelper.User)));
                custserBean.setName(cursor.getString(cursor.getColumnIndex(DbHelper.CUSTSER_NAME)));
                custserBean.set_id(cursor.getInt(cursor.getColumnIndex(DbHelper.ID)));
                list.add(custserBean);
            }
        }
        Logs.e("dbm121  Custser Sum total " + cursor.getCount());
        cursor.close();
        return list;
    }


    /**
     * 得到当前用户的售后人员信息
     */
    public List<CustserBean> getCustser(String username) {
        List<CustserBean> list = new ArrayList<>();
        String whereClause = DbHelper.User + "=" + username;
        Cursor cursor = mDbHelper.query(DbHelper.TABLE_CUSTSER, null, whereClause, null);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                CustserBean custserBean = new CustserBean();
                custserBean.setUsername(cursor.getString(cursor.getColumnIndex(DbHelper.User)));
                custserBean.setName(cursor.getString(cursor.getColumnIndex(DbHelper.CUSTSER_NAME)));
                custserBean.set_id(cursor.getInt(cursor.getColumnIndex(DbHelper.ID)));
                list.add(custserBean);
            }
        }
        Logs.v("dbm121  Custser Sum " + cursor.getCount());
        cursor.close();

        return list;
    }


    /**
     * 删除所有表
     */
    public void cleanAll() {
        cleanLogin(null, null);
        Logs.v("dbm165  deleteAll");
    }
}

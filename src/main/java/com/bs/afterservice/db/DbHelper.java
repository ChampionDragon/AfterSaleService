package com.bs.afterservice.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bs.afterservice.utils.Logs;

/**
 * Description: 数据库的帮助类
 * AUTHOR: Champion Dragon
 * created at 2018/3/9
 **/

public class DbHelper extends SQLiteOpenHelper {
    public final static String ID = "_id";
    public final static String User = "user";
    public static DbHelper mInstance;
    private SQLiteDatabase db;
    /*设备登陆记录的数据库*/
    public final static String TABLE_LOGIN = "device";
    public final static String LOGIN_TIME = "time";
    public final static String LOGIN_NAME = "name";
    /*售后人员的数据库*/
    public final static String TABLE_CUSTSER = "custser";
    public final static String CUSTSER_NAME = "custsername";


    public DbHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
    }

    /*返回类对象*/
    public static DbHelper getInstance(Context context, String dbName, int dbVersion) {
        if (mInstance == null) {
            mInstance = new DbHelper(context, dbName, dbVersion);
        }
        return mInstance;
    }

    /*当数据库被首次创建时才会执行*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE_LOGIN
                + "(_id integer PRIMARY KEY AUTOINCREMENT," + LOGIN_NAME + " varchar,"
                + LOGIN_TIME + " varchar)";
        db.execSQL(sql);
        sql = "create table " + TABLE_CUSTSER
                + "(_id integer PRIMARY KEY AUTOINCREMENT," + User + " varchar,"
                + CUSTSER_NAME + " varchar)";
        db.execSQL(sql);
        Logs.i("数据库第一次创建");
    }

    /*当打开数据库时传入的版本号与当前的版本号不同时才会调用该方法*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == newVersion) {
            return;
        }// 版本变了重新建立数据库
        db.execSQL("drop table if exists " + TABLE_LOGIN);
        onCreate(db);
        Logs.e("dh56  数据库更新了");
    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
/*如果想添加表又不想删除掉之前的表，就这个方法里添加建表语句。
且只能添加一次,添加后立刻在这删掉填表语句不然会报错
Caused by: android.database.sqlite.SQLiteException: table device already exists*/

//        Logs.v("dh66  测试是不是每次调用数据库都调用了这个方法");
    }

    /**
     * 插入数据
     */
    public long insert(String tableName, ContentValues contentValues) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.insert(tableName, null, contentValues);
    }

    /**
     * 删除数据
     */
    public int delete(String table, String whereClause, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.delete(table, whereClause, whereArgs);
    }

    /**
     * 更新数据
     */
    public int update(String table, ContentValues contentValues,
                      String whereClause, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.update(table, contentValues, whereClause, whereArgs);
    }

    /**
     * 通过四个参数数据查询
     *
     * @param whereClause where子句，除去where关键字剩下的部分，其中可带？占位符。如没有子句，则为null。
     * @param whereArgs   用于替代whereClause参数中？占位符的参数。如不需传入参数，则为null。
     */
    public Cursor query(String table, String[] columns, String whereClause,
                        String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.query(table, columns, whereClause, whereArgs, null, null,
                null);
    }

    /**
     * 通过sql语句查询数据
     */
    public Cursor rawQuery(String sql, String[] whereArgs) {
        if (db == null) {
            db = getWritableDatabase();
        }
        return db.rawQuery(sql, whereArgs);
    }

    /**
     * 向数据库写语句
     */
    public void execSql(String sql) {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.execSQL(sql);
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (db == null) {
            db = getWritableDatabase();
        }
        db.close();
        db = null;
    }

}

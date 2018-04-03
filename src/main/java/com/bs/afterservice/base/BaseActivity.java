package com.bs.afterservice.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.db.DbManager;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.SpUtil;

/**
 * Description: 创建程序的基本类
 * AUTHOR: Champion Dragon
 * created at 2018/2/23
 **/

public class BaseActivity extends AppCompatActivity{
    protected ProgressDialog mProgressDialog;
    public AsyncTaskExecutor executor;
    public BaseApplication baseapp;
    public SpUtil spUser;
    public DbManager managerDb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*设置成透明导航栏和透明状态栏*/
        SmallUtil.setScreen(this);
        executor = AsyncTaskExecutor.getinstance();
        baseapp=BaseApplication.getInstance();
        spUser=SpUtil.getInstance(SpKey.SP_user,MODE_PRIVATE);
        managerDb=baseapp.managerDb;
    }

    public void stopProgressDialog() {
        if (mProgressDialog.isShowing() && mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }
}

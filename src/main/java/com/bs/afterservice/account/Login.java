package com.bs.afterservice.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.bs.afterservice.MainActivity;
import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.base.BaseApplication;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.utils.DialogNotileUtil;
import com.bs.afterservice.utils.NetConnectUtil;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.TimeUtil;
import com.bs.afterservice.utils.ToastUtil;

import java.util.Random;

/**
 * Description: 登录类
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/
public class Login extends BaseActivity implements View.OnClickListener {
    private static final int LOGIN = 0;
    private static final int LOGIN_FAIL = 1;
    private EditText phone, psw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean netConnect = NetConnectUtil.NetConnect(this);
        if (!netConnect) {
            DialogNotileUtil.show(this, "请先将网络打开");
        }

        initView();
    }

    private void initView() {
        phone = (EditText) findViewById(R.id.login_phone);
        psw = (EditText) findViewById(R.id.login_psw);
        findViewById(R.id.login_login).setOnClickListener(this);
        findViewById(R.id.login_register).setOnClickListener(this);
        findViewById(R.id.login_resetpwd).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                if (loginCheck()) {
                    login();
                }
                break;
            case R.id.login_resetpwd:
                SmallUtil.getActivity(Login.this, ResetPwd.class);
                finish();
                break;
            case R.id.login_register:
                SmallUtil.getActivity(Login.this, Register.class);
                finish();
                break;


        }
    }

    /* 登录的检验*/
    private void login() {
        int random = new Random().nextInt(10);
        if (random % 2 == 0) {
            SmallUtil.getActivity(this, MainActivity.class);
            spUser.putBoolean(SpKey.isLogin, true);
            spUser.putString(SpKey.UserName, phone.getText().toString());//系统保留用户名
            managerDb.addOrUpdateLogin(phone.getText().toString(), TimeUtil.long2time(System.currentTimeMillis(), Constant.formatlogin));
            finish();
        } else {
            DialogNotileUtil.show(this, "登录失败\n密码错误或账号不存在");
        }
    }

    /*登录的验证*/
    private boolean loginCheck() {
        String phoneStr = phone.getText().toString();
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        String pswStr = psw.getText().toString();
//        Logs.d(pswStr);
        boolean checkpsw = TextUtils.isEmpty(pswStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return false;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机格式不对");
            return false;
        } else if (checkpsw) {
            ToastUtil.showLong("密码不能为空");
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        BaseApplication.getInstance().exitApp();
    }


}

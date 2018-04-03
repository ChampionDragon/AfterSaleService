package com.bs.afterservice.account;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ToastUtil;

/**
 * Description: 重置密码类
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/
public class ResetPwd extends BaseActivity implements View.OnClickListener{

    private EditText phone, code, pwd;
    private Button codeGet;
    private TimeCount timeCount;
    private boolean codeCheck = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);
        initView();
        timeCount = new TimeCount(60000, 1000);
    }

    private void initView() {
        findViewById(R.id.reset_reset).setOnClickListener(this);
        findViewById(R.id.reset_login).setOnClickListener(this);
        phone = (EditText) findViewById(R.id.reset_phone);
        code = (EditText) findViewById(R.id.reset_code);
        pwd = (EditText) findViewById(R.id.reset_psw);
        codeGet = (Button) findViewById(R.id.reset_codebtn);
        codeGet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_reset:
                registerCheck();
                break;
            case R.id.reset_login:
                SmallUtil.getActivity(ResetPwd.this, Login.class);
                finish();
                break;
            case R.id.reset_codebtn:
                codeCheck();
                break;
        }
    }

    /*验证格式*/
    private void registerCheck() {
        String phoneStr = phone.getText().toString();
        String codeString = code.getText().toString();
        String pwdString = pwd.getText().toString();
//        String pwdconfirString = pwdConfirm.getText().toString();
        boolean code = TextUtils.isEmpty(codeString);
        boolean pwd = TextUtils.isEmpty(pwdString);
//        boolean pwdconfirm = TextUtils.isEmpty(pwdconfirString);
        boolean checkphone = TextUtils.isEmpty(phoneStr);
        if (checkphone) {
            ToastUtil.showLong("手机号码不能为空");
            return;
        } else if (phoneStr.length() != 11) {
            ToastUtil.showLong("手机不是11位");
            return;
        }
        if (code) {
            ToastUtil.showLong("验证码不能为空");
            return;
        } else if (codeString.length() != 6) {
            ToastUtil.showLong("验证码不是6位");
            return;
        }
        if (pwd) {
            ToastUtil.showLong("密码不能为空");
            return;
        } else if (pwdString.length() != 6) {
            ToastUtil.showLong("密码不是6位");
            return;
        }
//        if (pwdconfirm) {
//            ToastUtil.showLong("确认密码不能为空");
//            return;
//        } else if (pwdconfirString.length() != 6) {
//            ToastUtil.showLong("确认密码不是6位");
//            return;
//        } else if (!pwdconfirString.equals(pwdString)) {
//            ToastUtil.showLong("密码和确认密码不相同");
//            return;
//        }
    }


    /*验证码格式验证*/
    private void codeCheck() {
        if (codeCheck) {
            String phoneStr = phone.getText().toString();
            boolean checkphone = TextUtils.isEmpty(phoneStr);
            if (checkphone) {
                ToastUtil.showLong("手机号码不能为空");
                return;
            } else if (phoneStr.length() != 11) {
                ToastUtil.showLong("手机格式不对");
                return;
            }
//            executor.submit(codeRunnable);
            timeCount.start();
        }
    }


    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            codeCheck = false;
            codeGet.setText(millisUntilFinished / 1000 + "s后重发");
        }

        @Override
        public void onFinish() {
            codeCheck = true;
            codeGet.setText("获取验证码");
        }

    }
}

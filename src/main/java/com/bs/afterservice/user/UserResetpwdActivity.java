package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;

public class UserResetpwdActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_resetpwd);
        initView();
        baseapp.addActivity(this);
    }

    private void initView() {
        findViewById(R.id.back_userresetpwd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_userresetpwd:
                baseapp.finishActivity();
                break;
            case R.id.userresetpwd_original:
                break;
            case R.id.userresetpwd_new:
                break;


        }
    }
}

package com.bs.afterservice.user;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.account.Login;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.utils.DialogCustomUtil;
import com.bs.afterservice.utils.SmallUtil;


public class UserSetActivity extends BaseActivity implements View.OnClickListener {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.userset_out).setOnClickListener(this);
        findViewById(R.id.back_userset).setOnClickListener(this);
        findViewById(R.id.userset_safe).setOnClickListener(this);
        findViewById(R.id.userset_push).setOnClickListener(this);
        findViewById(R.id.userset_dealer).setOnClickListener(this);
        findViewById(R.id.userset_opinion).setOnClickListener(this);
        findViewById(R.id.userset_about).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userset_out:
                dialog = DialogCustomUtil.create("警告", "您确定要退出",
                        UserSetActivity.this, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                spUser.putBoolean(SpKey.isLogin,false);
                                SmallUtil.getActivity(UserSetActivity.this, Login.class);
                                baseapp.finishAllActivity();
                            }
                        });
                dialog.show();
                break;
            case R.id.userset_safe:
                SmallUtil.getActivity(UserSetActivity.this, UserSafeActivity.class);
                break;
            case R.id.userset_push:
                SmallUtil.getActivity(UserSetActivity.this, MsgPushActivity.class);
                break;
            case R.id.userset_dealer:
                SmallUtil.getActivity(UserSetActivity.this, DealerActivity.class);
                break;
            case R.id.back_userset:
                baseapp.finishActivity();
                break;
            case R.id.userset_opinion:
                SmallUtil.getActivity(UserSetActivity.this, UserOpinionActivity.class);
                break;
            case R.id.userset_about:
                SmallUtil.getActivity(UserSetActivity.this, AboutActivity.class);
                break;
        }
    }


}

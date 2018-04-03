package com.bs.afterservice.user;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.bean.UserInfo;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.ObjectSave;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.view.RoundImageView;

import java.io.File;

/**
 * Description:  经销商的用户界面
 * AUTHOR: Champion Dragon
 * created at 2018/3/15
 **/
public class UserDealerActivity extends BaseActivity implements View.OnClickListener {
    private UserInfo userInfo;
    private RoundImageView head;
    private String tag = "userdealer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_user_dealer);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initHead();
    }

    /**
     * 初始化头像
     */
    private void initHead() {
        userInfo = ObjectSave.getUserInfo();
        String headPath = userInfo.getHeadpath();
        Logs.d(headPath + "  UserActivity42 头像路径");
        if (headPath != null && !headPath.isEmpty()) {
            File filehead = new File(headPath);
            Uri uri = Uri.fromFile(filehead);
            head.setImageURI(uri);
        }
    }

    private void initView() {
        head = (RoundImageView) findViewById(R.id.userdealer_riv);
        head.setOnClickListener(this);
        findViewById(R.id.back_userdealer).setOnClickListener(this);
        findViewById(R.id.userdealer_set).setOnClickListener(this);
        findViewById(R.id.userdealer_custser).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userdealer_riv:
                SmallUtil.getActivity(UserDealerActivity.this, UserMsgActivity.class);
                break;
            case R.id.back_userdealer:
                baseapp.finishActivity();
                break;
            case R.id.userdealer_set:
                SmallUtil.getActivity(UserDealerActivity.this, UserSetActivity.class);
                break;
            case R.id.userdealer_custser:
                SmallUtil.getActivity(UserDealerActivity.this, CustSerActivity.class);
                break;
        }
    }
}


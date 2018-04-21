package com.bs.afterservice.devmgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.ToastUtil;

public class AuthorizeChoiceActivity extends BaseActivity implements View.OnClickListener {
    private String UserName;
    public static String UserStr = "username";
    private CheckBox Ctrl, Video, Msg, Mgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_authorize_choice);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            UserName = intent.getStringExtra(UserStr);
            ToastUtil.showLong("传过来的用户名：" + UserName);
        }
    }

    private void initView() {
        Ctrl = (CheckBox) findViewById(R.id.authorizechoice_ctrl);
        Video = (CheckBox) findViewById(R.id.authorizechoice_video);
        Msg = (CheckBox) findViewById(R.id.authorizechoice_msg);
        Mgr = (CheckBox) findViewById(R.id.authorizechoice_mgr);
        findViewById(R.id.authorizechoice).setOnClickListener(this);
        findViewById(R.id.authorizechoice_complete).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authorizechoice:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.authorizechoice_complete:
                Complete();
                baseapp.TemfinishAllActivity();
                break;
        }
    }

    /*完成后的操作*/
    private void Complete() {
        StringBuffer sb = new StringBuffer("用户：" + "\"" + UserName + "\"\n");
        sb.append("选择的权限有：\n");
        if (Ctrl.isChecked()) {
            sb.append("视频控制\n");
        }
        if (Video.isChecked()) {
            sb.append("本地录像查看\n");
        }
        if (Msg.isChecked()) {
            sb.append("报警消息查看\n");
        }
        if (Mgr.isChecked()) {
            sb.append("设备管理\n");
        }
        ToastUtil.showLong(sb.toString());
    }


}

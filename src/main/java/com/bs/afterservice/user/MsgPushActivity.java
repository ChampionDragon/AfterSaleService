package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.view.SwitchButton;

public class MsgPushActivity extends BaseActivity implements View.OnClickListener {
    private SwitchButton news, voice, vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_msg_push);
        initView();
        initData();
    }

    /*初始化按钮的状态*/
    private void initData() {
        if (spUser.getBoolean(SpKey.user_news)) {
            news.setOncheck(true);
        }
        if (spUser.getBoolean(SpKey.user_voice)) {
            voice.setOncheck(true);
        }
        if (spUser.getBoolean(SpKey.user_vibration)) {
            vibration.setOncheck(true);
        }
    }

    private void initView() {
        findViewById(R.id.msgpush).setOnClickListener(this);
        news = (SwitchButton) findViewById(R.id.msgpush_news);
        voice = (SwitchButton) findViewById(R.id.msgpush_vioce);
        vibration = (SwitchButton) findViewById(R.id.msgpush_vibration);
        news.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spUser.putBoolean(SpKey.user_news, true);
                } else {
                    spUser.putBoolean(SpKey.user_news, false);
                }
            }
        });
        voice.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spUser.putBoolean(SpKey.user_voice, true);
                } else {
                    spUser.putBoolean(SpKey.user_voice, false);
                }
            }
        });
        vibration.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spUser.putBoolean(SpKey.user_vibration, true);
                } else {
                    spUser.putBoolean(SpKey.user_vibration, false);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.msgpush:
                baseapp.finishActivity();
                break;
        }
    }


}

package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.view.SwitchButton;

public class UserSafeActivity extends BaseActivity implements View.OnClickListener {
    private SwitchButton Fingerprint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_safe);
        baseapp.addActivity(this);
        initView();
        if (spUser.getBoolean(SpKey.user_fingerprint)) {
            Fingerprint.setOncheck(true);
        }
    }

    private void initView() {
        findViewById(R.id.usersafe).setOnClickListener(this);
        findViewById(R.id.usersafe_reset).setOnClickListener(this);
        findViewById(R.id.usersafe_loginrecord).setOnClickListener(this);
        Fingerprint = (SwitchButton) findViewById(R.id.usersafe_fingerprint);
        Fingerprint.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton buttonView, boolean isChecked) {
                if (isChecked) {
                    spUser.putBoolean(SpKey.user_fingerprint, true);
                } else {
                    spUser.putBoolean(SpKey.user_fingerprint, false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.usersafe:
                baseapp.finishActivity();
                break;
            case R.id.usersafe_reset:
                SmallUtil.getActivity(UserSafeActivity.this,UserResetpwdActivity.class);
                break;
            case R.id.usersafe_loginrecord:
                SmallUtil.getActivity(UserSafeActivity.this,LoginrecordActivity.class);
                break;
        }
    }


}

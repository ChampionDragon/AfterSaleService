package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.SystemUtil;

public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        version = (TextView) findViewById(R.id.version);
        version.setText(SystemUtil.VersionName());//初始化显示app版本名
        findViewById(R.id.back_about).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_about:
                baseapp.finishActivity();
                break;
        }
    }
}

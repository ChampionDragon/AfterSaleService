package com.bs.afterservice.devmgr;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.SmallUtil;

/**
 * Description: 设备设置
 * AUTHOR: Champion Dragon
 * created at 2018/4/8
 **/
public class DevSetActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_set);
        baseapp.addActivity(this);
        intView();
    }

    private void intView() {
        findViewById(R.id.devset).setOnClickListener(this);
        findViewById(R.id.devset_share).setOnClickListener(this);
        findViewById(R.id.devset_log).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devset_share:
                SmallUtil.getActivity(DevSetActivity.this,DevShareActivity.class);
                break;
            case R.id.devset_log:
                SmallUtil.getActivity(DevSetActivity.this,DevLogActivity.class);
                break;
            case R.id.devset:
                baseapp.finishActivity();
                break;
        }
    }
}

package com.bs.afterservice.activity;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;

public class OpNext extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_next);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.opnext).setOnClickListener(this);
        findViewById(R.id.opnext_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opnext:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.opnext_submit:
                baseapp.TemfinishAllActivity();
                break;
        }
    }
}

package com.bs.afterservice.devmgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.utils.ToastUtil;

public class ConnMethodActivity extends BaseActivity implements View.OnClickListener {
    private CheckBox wifi, gprs, ethernet;
    private String idStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_method);
        initView();
        idStr = getIntent().getStringExtra(Constant.deviceId);
        wifi.setChecked(true);
    }

    private void initView() {
        findViewById(R.id.connmethod).setOnClickListener(this);
        findViewById(R.id.connmethod_next).setOnClickListener(this);
        wifi = (CheckBox) findViewById(R.id.connmethod_wifi);
        wifi.setOnClickListener(this);
        gprs = (CheckBox) findViewById(R.id.connmethod_gprs);
        gprs.setOnClickListener(this);
        ethernet = (CheckBox) findViewById(R.id.connmethod_ethernet);
        ethernet.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connmethod:
                finish();
                break;
            case R.id.connmethod_wifi:
                wifi.setChecked(true);
                gprs.setChecked(false);
                ethernet.setChecked(false);
                break;
            case R.id.connmethod_next:
                Next();
                break;
            case R.id.connmethod_gprs:
                wifi.setChecked(false);
                gprs.setChecked(true);
                ethernet.setChecked(false);
                break;
            case R.id.connmethod_ethernet:
                wifi.setChecked(false);
                gprs.setChecked(false);
                ethernet.setChecked(true);
                break;
        }

    }

    private void Next() {
        if (idStr == null) {
            ToastUtil.showShort("未接收到二维码");
        } else {
            if (wifi.isChecked()) {
                startActivity(new Intent(this, WifiSetActivity.class).putExtra(Constant.deviceId, idStr));
            }
        }
        finish();
    }


}

package com.bs.afterservice.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.DialogCustomUtil;

public class MalDetailActivity extends BaseActivity implements View.OnClickListener {
    private TextView phonenum;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal_detail);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.maldetail).setOnClickListener(this);
        findViewById(R.id.maldetail_call).setOnClickListener(this);
        phonenum = (TextView) findViewById(R.id.maldetail_num);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.maldetail:
                baseapp.finishActivity();
                break;
            case R.id.maldetail_call:
                call();
                break;
        }
    }

    /*让用户选择是否打这个电话*/
    private void call() {
        dialog = DialogCustomUtil.create("提示", "您确定要拨打\n" + phonenum.getText(),
                MalDetailActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        call(phonenum.getText().toString());
                    }
                });
        dialog.show();
    }

    private void call(String phone) {
        /*跳过拨号界面，直接拨打电话*/
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phone);
        intent.setData(data); startActivity(intent);

        /*只调用拨号界面，不拨出电话*/
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        Uri data = Uri.parse("tel:" + phone);
//        intent.setData(data);
//        startActivity(intent);
    }

}
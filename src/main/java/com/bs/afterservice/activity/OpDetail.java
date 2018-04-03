package com.bs.afterservice.activity;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.SmallUtil;

/**
 * Description:售后反馈的详情
 * AUTHOR: Champion Dragon
 * created at 2018/3/26
 **/
public class OpDetail extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_detail);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.opdetail).setOnClickListener(this);
        findViewById(R.id.opdetail_discuss).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opdetail:
                baseapp.finishActivity();
                break;
            case R.id.opdetail_discuss:
                SmallUtil.getActivity(OpDetail.this,OpReview.class);
                break;
        }
    }
}

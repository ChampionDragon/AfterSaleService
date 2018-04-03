package com.bs.afterservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.pickerview.TimePickerView;
import com.bs.afterservice.pickerview.other.pickerViewUtil;
import com.bs.afterservice.utils.SmallUtil;

/**
 * Description: 新增售后反馈的详情
 * AUTHOR: Champion Dragon
 * created at 2018/3/27
 **/
public class OpNew extends BaseActivity implements View.OnClickListener {
    private TextView businesstime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_new);
        baseapp.TemaddActivity(this);
        initView();
    }

    private void initView() {
        findViewById(R.id.opnew).setOnClickListener(this);
        findViewById(R.id.opnew_next).setOnClickListener(this);
        businesstime = (TextView) findViewById(R.id.opnew_businesstime);
        businesstime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opnew:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.opnew_next:
                SmallUtil.getActivity(OpNew.this, OpNext.class);
                break;
            case R.id.opnew_businesstime:
                setBusinesstime();
                break;
        }
    }


    private void setBusinesstime() {
        pickerViewUtil.alertTimerPicker(OpNew.this, TimePickerView.Type.YEAR_MONTH_DAY, Constant.formatbusinesstime,
                "请选择出差日期", 16, new pickerViewUtil.TimerPickerCallBack() {
                    @Override
                    public void onTimeSelect(String date) {
                        businesstime.setText(date);
                    }
                });
    }


}

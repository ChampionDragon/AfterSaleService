package com.bs.afterservice.pickerview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.pickerview.other.pickerViewUtil;


public class TimepickerActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        tv = (TextView) findViewById(R.id.timepicker_tv);
        findViewById(R.id.btn_ymdhm).setOnClickListener(this);
        findViewById(R.id.btn_ymdh).setOnClickListener(this);
        findViewById(R.id.btn_ymd).setOnClickListener(this);
        findViewById(R.id.btn_mdhm).setOnClickListener(this);
        findViewById(R.id.btn_hm).setOnClickListener(this);
        findViewById(R.id.btn_ym).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String format = "";
        TimePickerView.Type type = null;
        switch (v.getId()) {
            case R.id.btn_ymdhm:
                type = TimePickerView.Type.ALL;
                format = "yyyy年MM月dd HH时mm分";
                break;
            case R.id.btn_ymdh:
                type = TimePickerView.Type.YEAR_MONTH_DAY_HOUR;
                format = "yyyy-MM-dd HH";
                break;
            case R.id.btn_ymd:
                type = TimePickerView.Type.YEAR_MONTH_DAY;
                format = "yyyy-MM-dd";
                break;
            case R.id.btn_mdhm:
                type = TimePickerView.Type.MONTH_DAY_HOUR_MIN;
                format = "MM-dd HH:mm";
                break;
            case R.id.btn_hm:
                type = TimePickerView.Type.HOURS_MINS;
                format = "HH:mm";
                break;
            case R.id.btn_ym:
                type = TimePickerView.Type.YEAR_MONTH;
                format = "yyyy-MM";
                break;
        }
        pickerViewUtil.alertTimerPicker(this, type, format, new pickerViewUtil.TimerPickerCallBack() {
            @Override
            public void onTimeSelect(String date) {
                tv.setText("系统时间设为\n" + date);
            }
        });

    }
}

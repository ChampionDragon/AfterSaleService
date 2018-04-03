package com.bs.afterservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.NotifyAdapter;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.bean.DevmalBean;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotificationActivity extends BaseActivity {
    private ListView lv;
    private List<DevmalBean> list;
    private String[] a = {"设备损坏", "设备离线", "设备故障"};
    private NotifyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        baseapp.addActivity(this);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        int b = new Random().nextInt(10000000);
        for (int i = 0; i < 8; i++) {
            DevmalBean bean = new DevmalBean();
            bean.setDevice(a[new Random().nextInt(3)]);
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * b, Constant.formatnotify);
            bean.setTime(time);
            list.add(bean);
        }
        adapter = new NotifyAdapter(list, this);
        lv.setAdapter(adapter);
    }

    private void initView() {
        findViewById(R.id.notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseapp.finishActivity();
            }
        });
        lv = (ListView) findViewById(R.id.notify_lv);
    }


}

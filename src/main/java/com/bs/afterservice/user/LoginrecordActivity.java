package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.LoginrecordAdapter;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.bean.LoginBean;

import java.util.List;

public class LoginrecordActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private LoginrecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_loginrecord);
        initView();
    }

    private void initView() {
        findViewById(R.id.loginrecord).setOnClickListener(this);
        lv = (ListView) findViewById(R.id.loginrecord_lv);
        List<LoginBean> loginList = managerDb.getLoginList();
        initLv(lv, loginList);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<LoginBean> loginList) {
        adapter = (LoginrecordAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new LoginrecordAdapter(this, loginList);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginrecord:
                baseapp.finishActivity();
                break;
        }

    }
}

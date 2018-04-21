package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.CustSerAdapter;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 添加后的列表
 * AUTHOR: Champion Dragon
 * created at 2018/3/20
 **/
public class CustSerNewActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private List<String> list;
    private CustSerAdapter adapter;
    private String tag = "CustSerNewActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_cust_ser_new);
        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.custsernew_lv);
        findViewById(R.id.custsernew).setOnClickListener(this);
        findViewById(R.id.custsernew_tv).setOnClickListener(this);
        findViewById(R.id.custsernew_complete).setOnClickListener(this);
        list = new ArrayList<>();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] arrays = extras.getStringArray(CustSerAddActivity.list);
            list = Arrays.asList(arrays);
            Logs.v(tag + " 49  添加的数据 " + Arrays.toString(arrays));
            initLv(lv, list);
        }
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<String> list) {
        adapter = (CustSerAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new CustSerAdapter(this, list);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custsernew:
//                SmallUtil.getActivity(CustSerNewActivity.this, CustSerActivity.class);
                baseapp.finishActivity();
                break;
            case R.id.custsernew_tv:
                Bundle bundle = new Bundle();
                bundle.putString(CustSerAddActivity.titlestr, "添加用户");
                bundle.putString(CustSerAddActivity.activity, "CustSerNewActivity");
                bundle.putStringArray(CustSerAddActivity.list, list.toArray(new String[list.size()]));
                Logs.i(tag + " 85  " + list.size());
                SmallUtil.getActivity(CustSerNewActivity.this, CustSerAddActivity.class, bundle);
                baseapp.finishActivity();
                break;
            case R.id.custsernew_complete:
                compelete();
                break;
        }
    }

    private void compelete() {
        /*向数据库添加数据*/
        String username = spUser.getString(SpKey.UserName);
        for (String name : list) {
            managerDb.addOrUpdateCustser(username, name);
        }
//        SmallUtil.getActivity(CustSerNewActivity.this, CustSerActivity.class);
        baseapp.finishActivity();
    }


}

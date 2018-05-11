package com.bs.afterservice.devmgr;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.rvstrtwo.DataBean;
import com.bs.afterservice.rvstrtwo.RecyclerAdapter;
import com.bs.afterservice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description: 设备日志的类
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/
public class DevLogActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private List<DataBean> dataBeanList;
    private DataBean dataBean;
    private RecyclerAdapter mAdapter;
    private String[] state = {"在线", "离线", "故障"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_dev_log);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.devlog).setOnClickListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
    }


    /*模拟数据*/
    private void initData() {
        List<String> listData = new ArrayList<>();
        listData.add("10:55红外 开启");
        listData.add("12:35设备故障");
        listData.add("23:55用户XXXXXXXXXXX下线");
        listData.add("07:21用户YYYYYYYYYYY上线");
        listData.add("16:46红外 开启");
        listData.add("17:46操作XXXXX");
        listData.add("18:46操作XXXXX");
        listData.add("19:46操作XXXXX");
        listData.add("20:46操作XXXXX");
        listData.add("21:46操作XXXXX");
        listData.add("22:46操作XXXXX");
        listData.add("23:46操作XXXXX");
        listData.add("23:55用户XXXXXXXXXXX下线");


        dataBeanList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            dataBean = new DataBean();
            dataBean.setID(i + "");
            dataBean.setType(0);
            dataBean.setListdata(listData);
            dataBean.setParentLeftTxt(TimeUtil.long2time(System.currentTimeMillis() - i * Constant.Day, Constant.formatbusinesstime));
//            dataBean.setChildLeftTxt("子--" + i);
            dataBean.setChildRightTxt(state[new Random().nextInt(3)]);
            dataBean.setChildBean(dataBean);
            dataBeanList.add(dataBean);
        }
        setData();
    }


    private void setData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this, dataBeanList);
        mRecyclerView.setAdapter(mAdapter);
        //滚动监听
        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devlog:
                baseapp.finishActivity();
                break;
        }
    }
}

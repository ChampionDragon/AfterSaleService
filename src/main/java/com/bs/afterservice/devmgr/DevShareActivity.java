package com.bs.afterservice.devmgr;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bs.afterservice.Interface.AllCheckListener;
import com.bs.afterservice.R;
import com.bs.afterservice.adapter.CustSerAdapter;
import com.bs.afterservice.adapter.CustSerCheckAdapter;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.bean.CustserBean;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.user.CustSerNewActivity;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 设备共享
 * AUTHOR: Champion Dragon
 * created at 2018/4/11
 **/
public class DevShareActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private TextView num, sum;//当前共享人数
    private int numSize, sumSize;
    private List<String> list;
    private List<CustserBean> listCustser;
    private CustSerAdapter adapter;
    public boolean mIsFromItem = false;//监听来源
    private CustSerCheckAdapter checkAdapter;//带判断的适配器
    private boolean isDel;//删除图标
    private CheckBox mMainCkb;//全选按钮
    private Button delComplete;//删除键


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_share);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        mMainCkb = (CheckBox) findViewById(R.id.devshare_cb);
        mMainCkb.setOnCheckedChangeListener(checkChange);
        delComplete = (Button) findViewById(R.id.devshare_delcomplete);
        delComplete.setOnClickListener(this);
        findViewById(R.id.devshare).setOnClickListener(this);
        findViewById(R.id.devshare_del).setOnClickListener(this);
        findViewById(R.id.devshare_add).setOnClickListener(this);
        num = (TextView) findViewById(R.id.devshare_num);
        sum = (TextView) findViewById(R.id.devshare_sum);
        lv = (ListView) findViewById(R.id.devshare_lv);
        sumSize = Integer.valueOf(sum.getText().toString());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devshare:
                baseapp.finishActivity();
                break;
            case R.id.devshare_del:
                isDel();
                break;
            case R.id.devshare_add:
                if (numSize < sumSize) {
                    SmallUtil.getActivity(DevShareActivity.this, AuthorizeActivity.class);
                } else {
                    ToastUtil.showLong("设备共享已经达到最大值,\n无法再添加了.");
                }
                break;
            case R.id.devshare_delcomplete:
                delComplete();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    //初始化数据
    private void initData() {
                  /*向数据库读取数据*/
        String username = spUser.getString(SpKey.UserName);
        List<CustserBean> custser = managerDb.getCustser(username);
        list = new ArrayList<>();
        listCustser = new ArrayList<>();
        for (CustserBean custserBean : custser) {
            list.add(custserBean.getName());
            custserBean.setIscheck(false);
            listCustser.add(custserBean);
        }

        numSize = list.size();
        num.setText(numSize + "");

        /*初始化两个适配器*/
        adapter = new CustSerAdapter(this, list);
        checkAdapter = new CustSerCheckAdapter(listCustser, this, allCheckListener);
        if (isDel) {
            lv.setAdapter(checkAdapter);
        } else {
            lv.setAdapter(adapter);
        }
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<String> list) {
        lv.setAdapter(adapter);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }


    /*初始化带check的listView*/
    private void initlvCheck(ListView lv, List<CustserBean> list) {
        lv.setAdapter(checkAdapter);
        checkAdapter.setData(list);
        checkAdapter.notifyDataSetChanged();
    }


    /*全选的回调监听*/
    AllCheckListener allCheckListener = new AllCheckListener() {
        @Override
        public void onCheckedChanged(boolean b) {
            Logs.v("110  " + b + "  " + mMainCkb.isChecked());
            //根据不同的情况对maincheckbox做处理
            if (!b && !mMainCkb.isChecked()) {
                return;
            } else if (!b && mMainCkb.isChecked()) {
                mIsFromItem = true;
                mMainCkb.setChecked(false);
            } else if (b && !mMainCkb.isChecked()) {//
                mIsFromItem = true;
                mMainCkb.setChecked(true);
            } else {
                return;
            }
        }
    };

    /*mMainCkb的选择监听*/
    CompoundButton.OnCheckedChangeListener checkChange = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            //当监听来源为点击item改变maincbk状态时不在监听改变，防止死循环
            Logs.d("mIsFromItem状态：" + mIsFromItem);
            if (mIsFromItem) {
                mIsFromItem = false;
                return;
            }

            //改变数据
            for (CustserBean model : listCustser) {
                model.setIscheck(isChecked);
            }
            //刷新listview
            checkAdapter.notifyDataSetChanged();
        }
    };


    /*删除后的处理*/
    private void delComplete() {
        String username = spUser.getString(SpKey.UserName);
        for (CustserBean custserBean : listCustser) {
            Logs.d("164      " + custserBean.ischeck());
            if (custserBean.ischeck()) {
                managerDb.custserClean(username, custserBean.getName());
            }
        }
        mMainCkb.setChecked(false);
        initData();
    }

    /*判断是否点击了删除按钮*/
    private void isDel() {
        if (isDel) {
            delComplete.setVisibility(View.GONE);
            mMainCkb.setVisibility(View.GONE);
            isDel = false;
            initLv(lv, list);
        } else {
            delComplete.setVisibility(View.VISIBLE);
            mMainCkb.setVisibility(View.VISIBLE);
            isDel = true;
            initlvCheck(lv, listCustser);
        }
    }


}

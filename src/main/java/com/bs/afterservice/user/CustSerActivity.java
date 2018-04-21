package com.bs.afterservice.user;

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
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:售后人员管理
 * AUTHOR: Champion Dragon
 * created at 2018/4/8
 **/
public class CustSerActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private TextView sum;
    private List<String> list;
    private List<CustserBean> listCustser;
    private CustSerAdapter adapter;
    public boolean mIsFromItem = false;//监听来源
    private CustSerCheckAdapter checkAdapter;//带判断的适配器
    private boolean isDel;
    private CheckBox mMainCkb;
    private Button delComplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_cust_ser);
        initView();
    }

    private void initView() {
        mMainCkb = (CheckBox) findViewById(R.id.ckb_main);
        mMainCkb.setOnCheckedChangeListener(checkChange);
        delComplete = (Button) findViewById(R.id.custser_delcomplete);
        delComplete.setOnClickListener(this);
        findViewById(R.id.custser).setOnClickListener(this);
        findViewById(R.id.custser_del).setOnClickListener(this);
        findViewById(R.id.custser_add).setOnClickListener(this);
        sum = (TextView) findViewById(R.id.custser_sum);
        lv = (ListView) findViewById(R.id.custser_lv);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    /**
     * 初始化数据
     */
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
        sum.setText(list.size() + "");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custser:
                baseapp.finishActivity();
                break;
            case R.id.custser_del:
                isDel();
                break;
            case R.id.custser_add:
                SmallUtil.getActivity(CustSerActivity.this, CustSerNewActivity.class);
                break;
            case R.id.custser_delcomplete:
                delComplete();
                break;
        }
    }

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

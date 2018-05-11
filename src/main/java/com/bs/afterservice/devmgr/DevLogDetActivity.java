package com.bs.afterservice.devmgr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.rvstrtwo.ChildViewHolder;
import com.bs.afterservice.rvstrtwo.DataBean;
import com.bs.afterservice.rvstrtwo.StrLargeAdapter;

import java.io.Serializable;

/**
 * Description: 日志子控件的详情
 * AUTHOR: Champion Dragon
 * created at 2018/5/10
 **/
public class DevLogDetActivity extends BaseActivity {
    private TextView date, text;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_log_det);
        intView();
        getData();
    }

    /*获取数据*/
    private void getData() {
        Intent intent = getIntent();
        if(intent!=null){
            Serializable serializable = intent.getSerializableExtra(ChildViewHolder.listData);
            if(serializable!=null){
                DataBean dataBean = (DataBean) serializable;
                setData(dataBean);
            }
        }
    }

    private void setData(DataBean dataBean) {
        date.setText(dataBean.getParentLeftTxt());
        text.setText(dataBean.getChildRightTxt());
        lv.setAdapter(new StrLargeAdapter(this,dataBean.getListdata()));
    }

    private void intView() {
        date = (TextView) findViewById(R.id.logdet_date);
        text = (TextView) findViewById(R.id.logdet_text);
        lv = (ListView) findViewById(R.id.logdet_lv);
        findViewById(R.id.logdet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

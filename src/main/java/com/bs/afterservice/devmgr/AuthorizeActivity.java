package com.bs.afterservice.devmgr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.user.CustSerAddActivity;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ToastUtil;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 授权管理
 * AUTHOR: Champion Dragon
 * created at 2018/4/11
 **/
public class AuthorizeActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private List<String> listStr;
    private Myadapter adapter;
    private String tag = "AuthorizeActivity";
    private int selectPosition = -1;//用于记录用户选择的变量
    private String choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.TemaddActivity(this);
        setContentView(R.layout.activity_authorize);
        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.authorize_lv);
        findViewById(R.id.authorize_tv).setOnClickListener(this);
        findViewById(R.id.authorize).setOnClickListener(this);
        findViewById(R.id.authorize_next).setOnClickListener(this);
        listStr = new ArrayList<>();
        lv.setOnItemClickListener(itemlistener);
    }

    /*item监听*/// 屏蔽子控件focus
    AdapterView.OnItemClickListener itemlistener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //获取选中的参数
            selectPosition = position;
            adapter.notifyDataSetChanged();
            choice = listStr.get(position);
            ToastUtil.showLong("你选择的用户是： " + choice);
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String[] arrays = extras.getStringArray(CustSerAddActivity.list);
            if (arrays.length > 0) {
                selectPosition = 0;
                listStr = Arrays.asList(arrays);
                choice = listStr.get(selectPosition);
                Logs.v(tag + " 49  添加的数据 " + Arrays.toString(arrays));
                initLv(lv, listStr);
            }
        }
    }


    /*初始化listView*/
    private void initLv(ListView lv, List<String> list) {
        adapter = (Myadapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new Myadapter(this, list);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.authorize:
                baseapp.TemfinishActivity(this);
                break;
            case R.id.authorize_tv:
                Bundle bundle = new Bundle();
                bundle.putString(CustSerAddActivity.titlestr, "添加售后人员");
                bundle.putString(CustSerAddActivity.activity, "AuthorizeActivity");
                bundle.putStringArray(CustSerAddActivity.list, listStr.toArray(new String[listStr.size()]));
                Logs.v(tag + " 110 " + listStr.size());
                SmallUtil.getActivity(AuthorizeActivity.this, CustSerAddActivity.class, bundle);
                baseapp.TemfinishActivity(this);
                break;
            case R.id.authorize_next:
                Intent a = new Intent(AuthorizeActivity.this, AuthorizeChoiceActivity.class);
                a.putExtra(AuthorizeChoiceActivity.UserStr, choice);
                startActivity(a);
                break;
        }
    }


    /*自定义授权的适配器*/
    private class Myadapter extends BaseAdapter {
        private Context context;
        private List<String> brandsList;

        public Myadapter(Context context, List<String> brandsList) {
            this.context = context;
            this.brandsList = brandsList;
        }

        @Override
        public int getCount() {
            return listStr.size();
        }

        @Override
        public Object getItem(int position) {
            return listStr.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(
                        R.layout.item_authorizecheck, null);
            }
            TextView name = ViewHolderUtil.get(convertView, R.id.custser_name);
            name.setText(brandsList.get(position));
            CheckBox checkBox = ViewHolderUtil.get(convertView, R.id.custser_check);
            if (position == selectPosition) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }
            return convertView;
        }

        public void setData(List<String> scanResults) {
            brandsList = scanResults;
        }
    }


}

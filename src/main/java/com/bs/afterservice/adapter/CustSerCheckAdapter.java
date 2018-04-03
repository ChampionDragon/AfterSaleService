package com.bs.afterservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bs.afterservice.Interface.AllCheckListener;
import com.bs.afterservice.R;
import com.bs.afterservice.bean.CustserBean;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description: 有复选框售后人员的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/3/22
 **/

public class CustSerCheckAdapter extends BaseAdapter {
    private List<CustserBean> list;
    private Context context;
    private AllCheckListener allCheckListener;

    public CustSerCheckAdapter(List<CustserBean> list, Context context, AllCheckListener allCheckListener) {
        this.list = list;
        this.context = context;
        this.allCheckListener = allCheckListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_custsercheck, null);
        }
        TextView name = ViewHolderUtil.get(convertView, R.id.custser_name);
        final CheckBox checkBox = ViewHolderUtil.get(convertView, R.id.custser_check);
        CustserBean custserBean = list.get(position);
        checkBox.setChecked(custserBean.ischeck());
        name.setText(custserBean.getName());
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    checkBox.setChecked(false);
                    list.get(position).setIscheck(false);
                    Logs.w(list.get(position).ischeck() + "  是否设置勾选68");
                } else {
                    checkBox.setChecked(true);
                    list.get(position).setIscheck(true);
                    Logs.w(list.get(position).ischeck() + "  是否设置勾选72");
                }
                //监听每个item，若所有checkbox都为选中状态则更改main的全选checkbox状态
                for (CustserBean custserBean1 : list) {
                    if (!custserBean1.ischeck()) {
                        allCheckListener.onCheckedChanged(false);
                        return;
                    }
                }
                allCheckListener.onCheckedChanged(true);
            }
        };
        name.setOnClickListener(listener);
//        convertView.setOnClickListener(listener);
//        checkBox.setOnClickListener(listener);
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<CustserBean> scanResults) {
        list = scanResults;
    }


}

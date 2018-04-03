package com.bs.afterservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description: 售后人员的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/3/16
 **/

public class CustSerAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public CustSerAdapter(Context context, List<String> list) {
        this.list = list;
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_custser, null);
        }
        TextView name = ViewHolderUtil.get(convertView, R.id.custser_name);
        String s = list.get(position);
        name.setText(s);
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<String> scanResults) {
        list = scanResults;
    }


}

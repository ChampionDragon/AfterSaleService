package com.bs.afterservice.rvstrtwo;

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
 * Description: Recyclerview的子控件listview适配器
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/

public class StringAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public StringAdapter(Context context, List<String> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.recycleview_item_child_lv_item, null);
        }
        TextView name = ViewHolderUtil.get(convertView, R.id.child_lv_tv);
        String s = list.get(position);
        name.setText(s);
        return convertView;
    }
}

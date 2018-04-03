package com.bs.afterservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.bean.DevmalBean;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description:通知的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/3/23
 **/

public class NotifyAdapter extends BaseAdapter {
    private List<DevmalBean> list;
    private Context context;

    public NotifyAdapter(List<DevmalBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notify, null);
        }
        TextView tv = ViewHolderUtil.get(convertView, R.id.notify_tv);
        TextView time = ViewHolderUtil.get(convertView, R.id.notify_time);
        tv.setText(list.get(position).getDevice());
        time.setText(list.get(position).getTime());
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<DevmalBean> scanResults) {
        list = scanResults;
    }


}

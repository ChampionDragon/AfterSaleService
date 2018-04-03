package com.bs.afterservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.activity.MalDetailActivity;
import com.bs.afterservice.bean.DeclareBean;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description:故障申报的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/4/1
 **/

public class DeclareAdapter extends BaseAdapter {
    private List<DeclareBean> list;
    private Context context;

    public DeclareAdapter(List<DeclareBean> list, Context context) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_declare, null);
        }
        TextView time = ViewHolderUtil.get(convertView, R.id.declare_time);
        TextView reason = ViewHolderUtil.get(convertView, R.id.declare_reason);
        DeclareBean bean = list.get(position);
        time.setText(bean.getTime());
        reason.setText(bean.getReason());
        ViewHolderUtil.get(convertView, R.id.declare_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallUtil.getActivity((Activity) context, MalDetailActivity.class);
            }
        });
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<DeclareBean> scanResults) {
        list = scanResults;
    }


}

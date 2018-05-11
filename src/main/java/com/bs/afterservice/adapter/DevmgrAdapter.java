package com.bs.afterservice.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.bean.DevmgrBean;
import com.bs.afterservice.devmgr.DevLogActivity;
import com.bs.afterservice.devmgr.DevSetActivity;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description: 设备管理的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/4/8
 **/
public class DevmgrAdapter extends BaseAdapter {
    private List<DevmgrBean> list;
    private Context context;

    public DevmgrAdapter(List<DevmgrBean> list, Context context) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_devmgr, null);
        }
        TextView tv = ViewHolderUtil.get(convertView, R.id.devmgr_tv);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.devmgr_iv);
        DevmgrBean devmgrBean = list.get(position);
        switch (devmgrBean.getState()) {
            case "on":
                iv.setImageResource(R.mipmap.dotgreen);
                break;
            case "off":
                iv.setImageResource(R.mipmap.dotorange);
                break;
            case "mal":
                iv.setImageResource(R.mipmap.dotred);
                break;
        }
        tv.setText(devmgrBean.getName());

        ViewHolderUtil.get(convertView, R.id.dev_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallUtil.getActivity((Activity) context, DevSetActivity.class);
            }
        });
        ViewHolderUtil.get(convertView, R.id.dev_log).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallUtil.getActivity((Activity) context, DevLogActivity.class);
            }
        });
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<DevmgrBean> scanResults) {
        list = scanResults;
    }


}

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
import com.bs.afterservice.activity.OpDetail;
import com.bs.afterservice.bean.AftSerOpBean;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description:售后服务反馈的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/3/24
 **/

public class AftSerOpAdapter extends BaseAdapter {
    private List<AftSerOpBean> list;
    private Context context;
    private boolean isShow;


    public AftSerOpAdapter(List<AftSerOpBean> list, Context context, boolean isShow) {
        this.list = list;
        this.context = context;
        this.isShow = isShow;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sale_check, null);
        }
        TextView time = ViewHolderUtil.get(convertView, R.id.sale_time);
        TextView userName = ViewHolderUtil.get(convertView, R.id.sale_name);
        ImageView iv = ViewHolderUtil.get(convertView, R.id.sale_iv);


        ViewHolderUtil.get(convertView, R.id.sale_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmallUtil.getActivity((Activity) context, OpDetail.class);
            }
        });

        if (!isShow) {
            iv.setVisibility(View.GONE);
        }
        AftSerOpBean aftSerOpBean = list.get(position);
        time.setText(aftSerOpBean.getTime());
        userName.setText(aftSerOpBean.getUserName());
        return convertView;
    }


    /**
     * 添加刷新的数据
     */
    public void setData(List<AftSerOpBean> scanResults) {
        list = scanResults;
    }

    public void setIsShow(boolean show) {
        isShow = show;
    }

}

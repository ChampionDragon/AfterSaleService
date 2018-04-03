package com.bs.afterservice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.bean.LoginBean;
import com.bs.afterservice.utils.ViewHolderUtil;

import java.util.List;

/**
 * Description: 历史登录的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/3/14
 **/

public class LoginrecordAdapter extends BaseAdapter {
    private Context context;
    public List<LoginBean> list;

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
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_loginrecord, null);
        }
        TextView time = ViewHolderUtil.get(convertView, R.id.login_time);
        TextView name = ViewHolderUtil.get(convertView, R.id.login_name);
        LoginBean loginBean = list.get(position);
        time.setText(loginBean.getTime());
        name.setText(loginBean.getName());
        return convertView;
    }


    public LoginrecordAdapter(Context context, List<LoginBean> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * 添加刷新的数据
     */
    public void setData(List<LoginBean> scanResults) {
        list = scanResults;
    }


}

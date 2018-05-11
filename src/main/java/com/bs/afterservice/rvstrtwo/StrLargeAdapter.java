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
 * Description: 日志子控件的详情的适配器
 * AUTHOR: Champion Dragon
 * created at 2018/5/11
 **/
public class StrLargeAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public StrLargeAdapter(Context context, List<String> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_string, null);
        }
        TextView name = ViewHolderUtil.get(convertView, R.id.string_tv);
        String s = list.get(position);
//        name.setTextColor(ActivityCompat.getColor(context,R.color.blueSky));
        name.setText(s);
        return convertView;
    }
}

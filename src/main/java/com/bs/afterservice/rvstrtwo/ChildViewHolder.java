package com.bs.afterservice.rvstrtwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.devmgr.DevLogDetActivity;
import com.bs.afterservice.view.MyListView;


/**
 * Description: 子布局ViewHolder
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/

public class ChildViewHolder extends BaseViewHolder {

    public static String listData = "listData";
    private Context mContext;
    private View view;
    //    private TextView childLeftText;
    private TextView childRightText;
    private MyListView lv;

    public ChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos) {
//        childLeftText = (TextView) view.findViewById(R.id.child_left_text);
//        childLeftText.setText(dataBean.getChildLeftTxt());
        childRightText = (TextView) view.findViewById(R.id.child_right_text);
        childRightText.setText(dataBean.getChildRightTxt());
        lv = (MyListView) view.findViewById(R.id.child_lv);
        lv.setAdapter(new StringAdapter(mContext, dataBean.getListdata().subList(0, 5)));
        view.findViewById(R.id.child_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DevLogDetActivity.class);
                intent.putExtra(listData, dataBean);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Activity a = (Activity) mContext;
                a.startActivity(intent);
            }
        });
    }


}

package com.bs.afterservice.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Description: 自定义listView(防止listview作为子控件时item显示不出来）
 * AUTHOR: Champion Dragon
 * created at 2018/4/25
 **/
public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //重新测量空间（这里是高度）
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}

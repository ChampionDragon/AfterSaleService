package com.bs.afterservice.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.view.CircularRing;

/**
 * Description:小滚轮加载Dialog
 * AUTHOR: Champion Dragon
 * created at 2018/4/20
 **/

public class DialogLoading {
    CircularRing mLoadingView;
    Dialog mLoadingDialog;
    TextView loadingText;
    public DialogLoading(Context context) {
        this(context, "正在加载");
    }
    public DialogLoading(Context context, String msg) {
        // 首先得到整个View
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog_view, null);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        // 获取整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        // 页面中的LoadingView
        mLoadingView = (CircularRing) view.findViewById(R.id.lv_circularring);
        // 页面中显示文本
        loadingText= (TextView) view.findViewById(R.id.loading_text);
        // 显示文本
        loadingText.setText(msg);
        // 创建自定义样式的Dialog
        mLoadingDialog = new Dialog(context, R.style.dialog);
        // 设置返回键无效
        mLoadingDialog.setCancelable(false);
        mLoadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                dm.widthPixels * 2/5,
                LinearLayout.LayoutParams.MATCH_PARENT));
    }

    public void setTv(String s){
        loadingText.setText(s);
    }
    public void show() {
        if (mLoadingDialog != null) {
            mLoadingDialog.show();
            mLoadingView.startAnim();
        }
    }

    public void close() {
        if (mLoadingDialog != null) {
            mLoadingView.stopAnim();
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}

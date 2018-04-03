package com.bs.afterservice.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.afterservice.R;

/**
 * Description: 普通消息Dialog
 * AUTHOR: Champion Dragon
 * created at 2018/2/24
 **/

public class DialogNotileUtil {
    public static Dialog CreatDialog(Context context, CharSequence s,
                                     View.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();// 屏幕宽度
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_notile, null);
        TextView tv = (TextView) view.findViewById(R.id.notile_tv);
        tv.setText(s);
        Button bt = (Button) view.findViewById(R.id.notile_bt);
        if (null == listener) {
            bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else {
            bt.setOnClickListener(listener);
        }
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 4 / 5, LinearLayout.LayoutParams.MATCH_PARENT));
        dialog.setCancelable(false);//设置返回键无法取消dialog

         /*设置成系统级别的弹框,可以在service弹框而不受acivity的局限性*/
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        return dialog;
    }

    public static void show(Context context, CharSequence s) {
        Dialog creatDialog = CreatDialog(context, s, null);
        creatDialog.show();
    }

    public static void show(Context context, int strId) {
        String s = context.getString(strId);
        show(context, s);
    }

    public static void show(Context context, CharSequence s,
                            View.OnClickListener listener) {
        Dialog creatDialog = CreatDialog(context, s, listener);
        creatDialog.show();
    }

    public static void show(Context context, int strId,
                            View.OnClickListener listener) {
        String s = context.getString(strId);
        show(context, s, listener);
    }
}

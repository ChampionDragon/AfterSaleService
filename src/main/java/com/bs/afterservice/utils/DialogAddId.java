package com.bs.afterservice.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bs.afterservice.R;

/**
 * Description: 添加设备的自定义Dialog
 * AUTHOR: Champion Dragon
 * created at 2018/4/17
 **/

public class DialogAddId {
    private Dialog iddialog;
    private EditText id;
    private ImageView iv;

    public DialogAddId(final Context context, final putIdListener listener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_id, null);
        id = (EditText) view.findViewById(R.id.addid_et);
        iv = (ImageView) view.findViewById(R.id.addid_iv);
        id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Logs.d("变化前:" + s + "   " + s.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Logs.w("变化中:" + s + "   " + s.length());
                if (s.length() == 12) {
                    Toast.makeText(context, "设备ID的长度不能超过12位", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    iv.setVisibility(View.VISIBLE);
                } else {
                    iv.setVisibility(View.GONE);
                }
//                Logs.e("变化后:" + s + "   " + s.length());
            }
        });
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id.setText("");
            }
        });
        view.findViewById(R.id.addid_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iddialog.dismiss();
            }
        });
        view.findViewById(R.id.addid_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.putId(id.getText().toString());
                iddialog.dismiss();
            }
        });

        DisplayMetrics dm = context.getResources().getDisplayMetrics();// 屏幕宽度
        iddialog = new Dialog(context, R.style.dialog);
        // 设置返回键无效
        iddialog.setCancelable(false);
        iddialog.getWindow().setGravity(Gravity.CENTER_VERTICAL);
        iddialog.setContentView(view, new LinearLayout.LayoutParams(
                dm.widthPixels * 7 / 8,
                LinearLayout.LayoutParams.MATCH_PARENT));
        iddialog.show();
    }

    public interface putIdListener {
        public void putId(String id);
    }
}

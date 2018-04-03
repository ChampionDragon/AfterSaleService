package com.bs.afterservice.user;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.ToastUtil;

public class UserOpinionActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv;
    private LinearLayout ll;
    private TextView remaining, tv;
    private EditText et;
    private Button submit;
    private boolean isback;
    private int MAX = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_opinion);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        iv = (ImageView) findViewById(R.id.useropinion);
        iv.setOnClickListener(this);
        ll = (LinearLayout) findViewById(R.id.useropinion_ll);
        et = (EditText) findViewById(R.id.useropinion_et);
        remaining = (TextView) findViewById(R.id.useropinion_remaining);
        submit = (Button) findViewById(R.id.useropinion_submit);
        submit.setOnClickListener(this);
        et.addTextChangedListener(new TextWatcher() {
            private int selectionStart, selectionEnd;
            private CharSequence temp;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                Logs.e(s.toString().length() + "    变化前长度");
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Logs.d(s.toString().length() + "    变化中长度");
                remaining.setText("你还可以输入" + (MAX - s.toString().length()) + "个字");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Logs.w(s.toString().length() + "    变化后的长度");
                selectionStart = et.getSelectionStart();
                selectionEnd = et.getSelectionEnd();
                if (temp.length() > MAX) {
                    ToastUtil.showLong("只能输入九个字");
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionEnd;
                    et.setText(s);
                    et.setSelection(tempSelection);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.useropinion_submit:
                if (et.getText().toString().length() == 0) {
                    ToastUtil.showLong("请您先填写内容再提交");
                } else {
                    isback();
                }
                break;
            case R.id.useropinion:
                baseapp.finishActivity();
                break;
        }
    }

    /*判断按钮功能*/
    private void isback() {
        if (isback) {
            baseapp.finishActivity();
        } else {
            submit.setText("返回");
            iv.setVisibility(View.GONE);
            ll.setVisibility(View.GONE);
            remaining.setVisibility(View.GONE);
            isback = true;
            submit.setBackgroundResource(R.drawable.btn_blue);
        }
    }


}

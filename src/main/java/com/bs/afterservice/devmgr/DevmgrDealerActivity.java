package com.bs.afterservice.devmgr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.DevmgrAdapter;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.bean.DevmgrBean;
import com.bs.afterservice.utils.DialogNotileUtil;
import com.bs.afterservice.zxing.ScanIdActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:经销商的设备管理
 * AUTHOR: Champion Dragon
 * created at 2018/4/8
 **/
public class DevmgrDealerActivity extends BaseActivity implements View.OnClickListener {
    private ListView lv;
    private String[] state = {"on", "off", "mal"};
    private List<DevmgrBean> list;
    private DevmgrAdapter adapter;
    private final static int SCANNIN_GREQUEST_CODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devmgr_dealer);
        baseapp.addActivity(this);
        initView();
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        for (int i = 0; i < new Random().nextInt(10); i++) {
            DevmgrBean bean = new DevmgrBean();
            bean.setState(state[new Random().nextInt(3)]);
            bean.setName("设备" + i);
            list.add(bean);
        }
        initLv(lv, list);
    }

    private void initLv(ListView lv, List<DevmgrBean> list) {
        adapter = (DevmgrAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new DevmgrAdapter(list, this);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.devmgrdealer_lv);
        findViewById(R.id.devmgrdealer).setOnClickListener(this);
        findViewById(R.id.devmgrdealer_add).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.devmgrdealer:
                baseapp.finishActivity();
                break;
            case R.id.devmgrdealer_add:
                Intent intent = new Intent();
                intent.setClass(DevmgrDealerActivity.this, ScanIdActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    String error = null;
                    String noId = null;
                    Bundle bundle = data.getExtras();
                    String Backstage = data.getStringExtra("Backstage");
                    if (Backstage != null) {
                        DialogNotileUtil.show(DevmgrDealerActivity.this, "未连接网络或访问后台失败");
                    } else if (bundle != null) {
                        error = bundle.getString("error");
                        noId = bundle.getString("noid");
                        if (noId != null) {
                            noId(noId);
                        } else if (error != null) {
                            errorStr(error);
                        }
                    }
                }
                break;
        }
    }

    /*提示用户输入的字符串不符合要求*/
    private void noId(String noId) {
        SpannableString spannableString = new SpannableString(noId + "\n不是设备ID号");
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5f);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(ContextCompat.getColor(this, R.color.red));//设置颜色
        spannableString.setSpan(relativeSizeSpan, 0, noId.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(colorSpan, 0, noId.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        DialogNotileUtil.show(DevmgrDealerActivity.this, spannableString);
    }

    /*设备不错在的弹框*/
    private void errorStr(String error) {
        SpannableString spannableString = new SpannableString("配置失败\n\n设备号" + error + "不存在");
        RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(1.5f);//设置字体大小
        RelativeSizeSpan relativeSizeSpan1 = new RelativeSizeSpan(1.3f);//设置字体大小
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.red));//设置颜色
        ForegroundColorSpan blackSpan = new ForegroundColorSpan(getResources().getColor(R.color.black));//设置颜色
        spannableString.setSpan(relativeSizeSpan1, 9, error.length() + 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(colorSpan, 9, error.length() + 9, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(relativeSizeSpan, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        spannableString.setSpan(blackSpan, 0, 4, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        DialogNotileUtil.show(DevmgrDealerActivity.this, spannableString);
    }


}

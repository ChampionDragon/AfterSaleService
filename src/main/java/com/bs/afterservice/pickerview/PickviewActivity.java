package com.bs.afterservice.pickerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.pickerview.other.DataModel;
import com.bs.afterservice.pickerview.other.ProvinceBean;
import com.bs.afterservice.pickerview.other.TypeBean;
import com.bs.afterservice.pickerview.other.pickerViewUtil;

import java.util.ArrayList;

/**
 * Description: 滚轮选择器
 * AUTHOR: Champion Dragon
 * created at 2017/8/3
 **/
public class PickviewActivity extends BaseActivity {
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private ArrayList<TypeBean> mList = new ArrayList<>();
    private TextView tvTime, tvOptions;
    private TextView tv_single_option;

    OptionsPickerView pvOptions;
    View vMasker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickview);
        vMasker = findViewById(R.id.vMasker);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvOptions = (TextView) findViewById(R.id.tvOptions);
        tv_single_option = (TextView) findViewById(R.id.tv_single_option);

/*++++++++++++++++++++++++++++++++ =============时间选择=============== ++++++++++++++++++++++++++++++*/
        tvTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PickviewActivity.this, TimepickerActivity.class);
                startActivity(intent);
            }
        });

/*++++++++++++++++++++++++++++++   ==============单项选择器==============  +++++++++++++++++++++++++++++++++*/
        for (int i = 0; i <= 10; i++) {
            mList.add(new TypeBean(i, "item" + i));
        }

        tv_single_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickerViewUtil.alertBottomWheelOption(PickviewActivity.this, "自定义标题", mList, new pickerViewUtil.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int postion) {
                        Toast.makeText(PickviewActivity.this, mList.get(postion).getName(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

/*++++++++++++++++++++++++++++++  ===============多项选择器======================  ++++++++++++++++++++++*/
        tvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });

    }


    private void showOptions() {
        // 初始化三个列表数据
        DataModel.initData(options1Items, options2Items, options3Items);
        /*设置标题0-3个*/
        String[] labels = {"省"};
        new pickerViewUtil().optionPicker(this, "请选择城市", onOptionsSelectListener,
                options1Items, options2Items, options3Items, labels);
    }


    /**
     * 设置三级别选择监听
     */
    OptionsPickerView.OnOptionsSelectListener onOptionsSelectListener = new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int option2, int options3) {
            //返回的分别是三个级别的选中位置
            String tx = options1Items.get(options1).getPickerViewText()
                    + options2Items.get(options1).get(option2)
                    + options3Items.get(options1).get(option2).get(options3);
            tvOptions.setText(tx);
            vMasker.setVisibility(View.GONE);
        }
    };


}

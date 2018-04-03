package com.bs.afterservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.AftSerOpAdapter;
import com.bs.afterservice.bean.AftSerOpBean;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:售后服务反馈的碎片
 * AUTHOR: Champion Dragon
 * created at 2018/3/24
 **/

public class AftSerOpFragment extends Fragment {
    private View view;
    private String tag = "AftSerOpFragment";
    private ListView lv;
    private boolean isShow;
    private List<AftSerOpBean> list;
    private AftSerOpAdapter adapter;
    private String[] names = {"张三", "李四", "王五", "赵六"};

    public AftSerOpFragment(boolean isShow) {
        this.isShow = isShow;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_aftserop, container, false);
        initView(view);
        return view;
    }


    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.aftserop_lv);
        initData();
    }

    private void initData() {
        list = new ArrayList<>();
        int b = new Random().nextInt(100000000);
        for (int i = 0; i < 8; i++) {
            AftSerOpBean bean = new AftSerOpBean();
            bean.setUserName(names[new Random().nextInt(4)]);
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * b, Constant.formatnotify);
            bean.setTime(time);
            list.add(bean);
        }
        initLv(lv, list);
    }

    /*初始化listView*/
    private void initLv(ListView lv, List<AftSerOpBean> loginList) {
        adapter = (AftSerOpAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new AftSerOpAdapter(list, getActivity(), isShow);
            lv.setAdapter(adapter);
        } else {
            adapter.setData(loginList);
            adapter.notifyDataSetChanged();
        }
    }


}

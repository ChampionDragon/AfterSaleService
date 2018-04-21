package com.bs.afterservice.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bs.afterservice.R;
import com.bs.afterservice.adapter.DeclareAdapter;
import com.bs.afterservice.bean.DeclareBean;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Description:故障申报的碎片
 * AUTHOR: Champion Dragon
 * created at 2018/4/1
 **/

public class DeclareFragment extends Fragment {
    private View view;
    private String tag = "DeclareFragment";
    private ListView lv;
    private int num;
    private List<DeclareBean> list;
    private DeclareAdapter adapter;
    private String[] names = {"设备故障", "设备离线",};

    public DeclareFragment(int num) {
        this.num = num;
    }

    public DeclareFragment() {
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
        for (int i = 0; i < num; i++) {
            DeclareBean bean = new DeclareBean();
            bean.setReason(names[new Random().nextInt(2)]);
            String time = TimeUtil.long2time(System.currentTimeMillis() - i * b, Constant.formatnotify);
            bean.setTime(time);
            list.add(bean);
        }
        initLv(lv, list);
    }

    private void initLv(ListView lv, List<DeclareBean> list) {
        adapter = (DeclareAdapter) lv.getAdapter();
        if (adapter == null) {
            adapter = new DeclareAdapter(list, getActivity());
            lv.setAdapter(adapter);
        } else {
            adapter.setData(list);
            adapter.notifyDataSetChanged();
        }
    }


}

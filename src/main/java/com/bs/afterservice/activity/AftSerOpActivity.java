package com.bs.afterservice.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.fragment.AftSerOpFragment;
import com.bs.afterservice.utils.SmallUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 售后服务反馈的类
 * AUTHOR: Champion Dragon
 * created at 2018/4/1
 **/
public class AftSerOpActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs = {"未审批", "已审批"};
    public static String approval = "approval";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aft_ser_op);
        baseapp.addActivity(this);
        initView();
        initTab();
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        /*如果tabLayout和viewpager绑定的,以下这种方式无法显示title.
        这个原因是因为tablayout与viewpager建立关联关系的时候，已经把tab全部remove了
        要在FragmentPagerAdapter中的getPageTitle设置*/
//        tabLayout.addTab(tabLayout.newTab().setText("未审批"));
//        tabLayout.addTab(tabLayout.newTab().setText("已审批"));
        fragments.add(new AftSerOpFragment(false));
        fragments.add(new AftSerOpFragment(true));

        vp.setAdapter(new TabAdapter(getSupportFragmentManager()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            vp.setCurrentItem(extras.getInt(approval));
        }
        tabLayout.addOnTabSelectedListener(tabSelect);
        tabLayout.setupWithViewPager(vp);
    }

    private void initView() {
        findViewById(R.id.aftserop).setOnClickListener(this);
        findViewById(R.id.aftserop_add).setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.aftserop_tab);
        vp = (ViewPager) findViewById(R.id.aftserop_vp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.aftserop:
                baseapp.finishActivity();
                break;
            case R.id.aftserop_add:
                SmallUtil.getActivity(AftSerOpActivity.this, OpNew.class);
                break;
        }
    }


    /*TabLayout改变状态的监听器*/
    TabLayout.OnTabSelectedListener tabSelect = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vp.setCurrentItem(tab.getPosition());
//            Logs.v("滑动选择：" + tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

    };


    /* Fragment适配器*/
    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //显示标签上的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}

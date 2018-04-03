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
import com.bs.afterservice.fragment.DeclareFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: 故障申报的类
 * AUTHOR: Champion Dragon
 * created at 2018/4/1
 **/
public class DeclareActivity extends BaseActivity implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager vp;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs = {"已处理", "未处理"};
    public static String declare = "declare";
    public static String undeclare = "undeclare";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declare);
        baseapp.addActivity(this);
        initView();
        initTab();
    }

    private void initTab() {
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fragments.add(new DeclareFragment(extras.getInt(declare)));
            fragments.add(new DeclareFragment(extras.getInt(undeclare)));
        }
        vp.setAdapter(new TabAdapter(getSupportFragmentManager()));
        tabLayout.addOnTabSelectedListener(tabSelect);
        tabLayout.setupWithViewPager(vp);
    }

    private void initView() {
        findViewById(R.id.declare).setOnClickListener(this);
        tabLayout = (TabLayout) findViewById(R.id.declare_tab);
        vp = (ViewPager) findViewById(R.id.declare_vp);
    }


    /*TabLayout改变状态的监听器*/
    TabLayout.OnTabSelectedListener tabSelect = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            vp.setCurrentItem(tab.getPosition());
//            Logs.e("选择tab " + tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.declare:
                baseapp.finishActivity();
                break;
        }
    }


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

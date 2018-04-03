package com.bs.afterservice;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bs.afterservice.activity.AftSerOpActivity;
import com.bs.afterservice.activity.DeclareActivity;
import com.bs.afterservice.activity.NotificationActivity;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.base.BaseApplication;
import com.bs.afterservice.bean.CustserBean;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.user.UserDealerActivity;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.view.RingView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private RingView devRing, declareRing;
    private TextView devsum, devMalfunction, devOnline, devOutline,
            declareSum, declareDeal, declareUndeal;
    private int declare, Undeclare;//设置已申报和未申报的数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        baseapp.addActivity(this);
        initView();
        setData();

        /*测试读取数据库数据*/
//        managerDb.addOrUpdateLogin(spUser.getString(SpKey.UserName),"2018/03/19 11:19");
//        List<LoginBean> loginList = managerDb.getLoginList();
//        for (LoginBean loginBean : loginList) {
//            Logs.v(loginBean.get_id() + "  账户：" + loginBean.getName() + "时间   " + loginBean.getTime());
//        }
    }


    @Override
    protected void onResume() {
        super.onResume();
//        test();
    }

    private void test() {
        String username = spUser.getString(SpKey.UserName);
        Logs.d("用户名： " + username);

//        managerDb.custserClean(username);
//        managerDb.addOrUpdateCustser(username, "34");
//        managerDb.addOrUpdateCustser(username, "eqwq");
//        managerDb.addOrUpdateCustser(username, "1dj");
//        managerDb.addOrUpdateCustser("aaa", "eqwq");

        List<CustserBean> allCustser = managerDb.getAllCustser();
        Logs.e("++++++++++++++++   全部数据   +++++++++++++++++++++++");
        for (CustserBean CustserBean : allCustser) {
            Logs.i(CustserBean.get_id() + " 用户名  " + CustserBean.getUsername() + "   售后人员   " + CustserBean.getName());
        }
        allCustser = managerDb.getCustser(username);
        Logs.v("++++++++++++++++   指定用户的数据   +++++++++++++++++++++++");
        for (CustserBean CustserBean : allCustser) {
            Logs.w(CustserBean.get_id() + " 用户名  " + CustserBean.getUsername() + "   售后人员   " + CustserBean.getName());
        }
    }


    private void initView() {
        findViewById(R.id.mainUser).setOnClickListener(this);
        findViewById(R.id.mainNotification).setOnClickListener(this);
        findViewById(R.id.main_aftser).setOnClickListener(this);
        findViewById(R.id.main_approvalnot).setOnClickListener(this);
        findViewById(R.id.main_approval).setOnClickListener(this);
        devRing = (RingView) findViewById(R.id.main_ringdev);
        declareRing = (RingView) findViewById(R.id.main_ringdeclare);
        declareRing.setOnClickListener(this);

        declareSum = (TextView) findViewById(R.id.main_declaresum);
        declareSum.setText("11");
        declareDeal = (TextView) findViewById(R.id.declare_deal);
        declareUndeal = (TextView) findViewById(R.id.declare_undeal);

        devsum = (TextView) findViewById(R.id.main_devsum);
        devsum.setText("13");
        devMalfunction = (TextView) findViewById(R.id.devmalfunction);
        devOnline = (TextView) findViewById(R.id.devonline);
        devOutline = (TextView) findViewById(R.id.devoutline);
    }


    /*初始化统计图的数据*/
    private void setData() {
        Undeclare = new Random().nextInt(11);
        declare = 11 - Undeclare;
        float aa = (float) (Undeclare * 1.0 / 11 * 100);
        float bb = (float) (declare * 1.0 / 11 * 100);
        declareUndeal.setText("" + declare);
        declareDeal.setText(String.valueOf(Undeclare));
        List<Float> rateDeclare = new ArrayList<>();
        rateDeclare.add(Float.parseFloat(new DecimalFormat(".##").format(bb)));
        rateDeclare.add(Float.parseFloat(new DecimalFormat(".##").format(aa)));

        List colorDeclare = new ArrayList<>();
        colorDeclare.add(R.color.red);
        colorDeclare.add(R.color.green);

        declareRing.setShow(colorDeclare, rateDeclare);

        // 添加的是颜色
        List colorList = new ArrayList<>();
        colorList.add(R.color.red);
        colorList.add(R.color.orange);
        colorList.add(R.color.green);

        int c = new Random().nextInt(6);
        int d = new Random().nextInt(6);
        int e = 13 - c - d;
        float cc = (float) (c * 1.0 / 13 * 100);
        float dd = (float) (d * 1.0 / 13 * 100);
        float ee = (float) (e * 1.0 / 13 * 100);
        devMalfunction.setText(String.valueOf(c));
        devOutline.setText(String.valueOf(d));
        devOnline.setText(String.valueOf(e));

        //  添加的是百分比
        List rateList = new ArrayList<>();
        rateList.add(Float.parseFloat(new DecimalFormat(".##").format(cc)));
        rateList.add(Float.parseFloat(new DecimalFormat(".##").format(dd)));
        rateList.add(Float.parseFloat(new DecimalFormat(".##").format(ee)));

        devRing.setShow(colorList, rateList);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainUser:
                SmallUtil.getActivity(MainActivity.this, UserDealerActivity.class);
                break;
            case R.id.mainNotification:
                SmallUtil.getActivity(MainActivity.this, NotificationActivity.class);
                break;
            case R.id.main_ringdeclare:
                Bundle a = new Bundle();
                a.putInt(DeclareActivity.declare, declare);
                a.putInt(DeclareActivity.undeclare, Undeclare);
                SmallUtil.getActivity(MainActivity.this, DeclareActivity.class, a);
                break;
            case R.id.main_aftser:
                SmallUtil.getActivity(MainActivity.this, AftSerOpActivity.class);
                break;
            case R.id.main_approvalnot:
                Bundle bundle = new Bundle();
                bundle.putInt(AftSerOpActivity.approval, 0);
                SmallUtil.getActivity(MainActivity.this, AftSerOpActivity.class, bundle);
                break;
            case R.id.main_approval:
                Bundle b = new Bundle();
                b.putInt(AftSerOpActivity.approval, 1);
                SmallUtil.getActivity(MainActivity.this, AftSerOpActivity.class, b);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        BaseApplication.getInstance().exitApp();
    }
}

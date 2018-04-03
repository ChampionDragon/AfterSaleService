package com.bs.afterservice.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.ToastUtil;

public class OpReview extends BaseActivity implements View.OnClickListener {
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_op_review);
        baseapp.addActivity(this);
        initView();
    }

    private void initView() {
        ratingBar = (RatingBar) findViewById(R.id.opreview_ratingbar);
        ratingBar.setOnRatingBarChangeListener(listener);
        findViewById(R.id.opreview).setOnClickListener(this);
        findViewById(R.id.opreview_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.opreview:
                baseapp.finishActivity();
                break;
            case R.id.opreview_submit:
                baseapp.finishActivity();
                break;
        }
    }


    /*RatingBar的监听*/
    RatingBar.OnRatingBarChangeListener listener = new RatingBar.OnRatingBarChangeListener() {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            ToastUtil.showShort("评分为： " + rating);
        }
    };


}

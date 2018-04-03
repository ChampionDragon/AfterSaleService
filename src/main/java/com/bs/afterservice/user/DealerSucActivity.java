package com.bs.afterservice.user;

import android.os.Bundle;
import android.view.View;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.utils.SmallUtil;

public class DealerSucActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_suc);
        findViewById(R.id.dealersuc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.dealersuc:
                        SmallUtil.getActivity(DealerSucActivity.this,UserSetActivity.class);
                        finish();
                        break;
                }
            }
        });
    }
}

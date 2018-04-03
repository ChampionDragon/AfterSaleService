package com.bs.afterservice.guide;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.bs.afterservice.MainActivity;
import com.bs.afterservice.R;
import com.bs.afterservice.account.Login;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.SpKey;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.SystemUtil;

/**
 * Description: APP的欢迎界面
 * AUTHOR: Champion Dragon
 * created at 2018/3/13
 **/
public class Welcome extends BaseActivity {
    public int DELAYTIME = 333;
    public static final int MAIN = 0;
    public static final int LOGIN = 1;
    public static final int GUIDE = 2;
    boolean isFisrt;
    boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_welcome);
        super.onCreate(savedInstanceState);
        isFisrt = spUser.getBoolean(SpKey.isFirst, true);
        int curVer = SystemUtil.VersionCode();
        int preVer = spUser.getInt(SpKey.preVer);
        isLogin = spUser.getBoolean(SpKey.isLogin);
//        Logs.e("welcome 32 " + isLogin);
        //isFisrt || curVer > preVer     !isLogin
        if (isFisrt || curVer > preVer) {
            handler.sendEmptyMessageDelayed(GUIDE, DELAYTIME);
            spUser.putInt(SpKey.preVer, curVer);
            spUser.putBoolean(SpKey.isFirst, false);
        } else if (!isLogin) {
            handler.sendEmptyMessageDelayed(LOGIN, DELAYTIME);
        } else {
            handler.sendEmptyMessageDelayed(MAIN, DELAYTIME);
        }

    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MAIN:
                    SmallUtil.getActivity(Welcome.this, MainActivity.class);
                    finish();
                    break;
                case LOGIN:
                    SmallUtil.getActivity(Welcome.this, Login.class);
                    finish();
                    break;
                case GUIDE:
                    SmallUtil.getActivity(Welcome.this, Login.class);
                    finish();
                    break;
            }
        }
    };
}

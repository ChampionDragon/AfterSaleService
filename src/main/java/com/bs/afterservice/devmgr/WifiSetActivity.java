package com.bs.afterservice.devmgr;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.socket.TestProtocol;
import com.bs.afterservice.socket.UdpUtil;
import com.bs.afterservice.utils.DialogLoading;
import com.bs.afterservice.utils.DialogNotileUtil;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ToastUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.CRC32;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class WifiSetActivity extends BaseActivity implements View.OnClickListener {
    private TextView name;
    private EditText pwd;
    private WifiManager mWifiManager;
    private WifiInfo mWifiInfo;
    private String wifiname, deviceID, wifipsw;
    //    private WifiManager.MulticastLock multicastLock;
    private boolean IsOutline;
    private boolean IsOnline;
    private boolean IsToast;
    private static String tag = "WifiSetActivity";
    private xmitterTask xmitter;
    private DialogLoading dialog;
    private int ONLINE = 1;
    private int OUTLINE = 2;
    private int ERROR = 144;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_set);
        initView();
        initWIFI();
//        getIntent().getStringExtra(Constant.deviceId)
        deviceID = "";
    }

    private void initView() {
        name = (TextView) findViewById(R.id.wifiset_ssid);
        pwd = (EditText) findViewById(R.id.wifiset_pwd);
        findViewById(R.id.wifiset).setOnClickListener(this);
        findViewById(R.id.wifiset_set).setOnClickListener(this);
    }

    private void initWIFI() {
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        mWifiInfo = mWifiManager.getConnectionInfo();
        wifiname = mWifiInfo.getSSID();
        wifiname = wifiname.replace("\"", "");//去掉双引号
        name.setText(wifiname);
        /*读取上次输入的密码*/
        wifipsw = spUser.getString(wifiname);
        pwd.setText(wifipsw);
    }


    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 42) {
                executor.submit(IdRunnable);
            } else if (msg.what == 43) {
                dialog.close();
                dialog = new DialogLoading(WifiSetActivity.this, "正在查询是否配置成功");
                dialog.show();

            } else if (msg.what == 44) {
                postDelayed(delayRunnable, 4444);
            } else if (msg.what == ONLINE) {
                /*将设备添加到本地数据库*/
                if (!IsToast) {
                    spUser.putString(wifiname, wifipsw);

                    String idStr = msg.getData().getString("id");
                    dialog.close();//关闭Dialog
                 /*   ++++++++++++++++++++++++++++ 在这里讲设备添加到数据库中 ++++++++++++++++++++++++++++*/
                    ToastUtil.showLong("设备WIFI配置成功.");
                    IsToast = true;
                    finish();
                }
            } else if (msg.what == OUTLINE && IsOutline && !IsOnline) {
                Logs.e(tag + " 248" + IsOnline + "  " + IsOutline);
                dialog.close();//关闭Dialog
                DialogNotileUtil.show(WifiSetActivity.this, "设备WIFI配置失败.");
                IsOutline = false;
            } else if (msg.what == ERROR) {
                SetError();
            }
            super.handleMessage(msg);
        }
    };


    /**
     * 延迟三秒发送查询命令
     */
    Runnable delayRunnable = new Runnable() {
        @Override
        public void run() {
            IsOutline = true;
            executor.submit(IdRunnable);
        }
    };


    /*检测ID是否在线从而判断WIFI是否配置成功*/
    Runnable IdRunnable = new Runnable() {
        @Override
        public void run() {

            /*通过查询设备ID是否有返回值*/
            byte[] packet = TestProtocol.getByCmd("1", "", TestProtocol.cmdID);
            String serverSend = UdpUtil.IpSend(packet);
            Logs.e(tag + " 289  " + serverSend);
            if (serverSend.indexOf("NO RESPONSE") >= 0) {
                handler.sendEmptyMessage(OUTLINE);
            } else {
                /*解析返回的参数*/
                String id = SmallUtil.getPartString(serverSend, "ID:", 10);
                IsOnline = true;
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                Message msg = handler.obtainMessage(ONLINE);
                msg.setData(bundle);
                handler.sendMessage(msg);

            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wifiset:
                finish();
                break;
            case R.id.wifiset_set:
                wifiConnect();
                break;
        }
    }


    /*验证输入的ID号的WIFI密码是否符合要求*/
    private boolean validate(String pass, String key) {
        if (pass.length() < 8 || pass.length() > 63) {
            DialogNotileUtil.show(this, "密码要大于8位小于63位");
            return false;
        }
//        if (key.length() != 10) {
//            DialogNotileUtil.show(this, "ID号应该为10位");
//            return false;
//        }
        return true;
    }


    /**
     * 一键配置WIFI
     */
    private void wifiConnect() {
        wifipsw = pwd.getText().toString();
        String pass = wifipsw;
        String key = deviceID;

        if (!validate(pass, key)) return;

//        allowMulticast();

        try {
            dialog = new DialogLoading(this, "正在配置WIFI");
            dialog.show();

            xmitter = new xmitterTask();
            xmitter.handler = handler;
            CRC32 crc32 = new CRC32();
            crc32.reset();
            crc32.update(pass.getBytes());
            xmitter.passCRC = (int)
                    crc32.getValue() & 0xffffffff;
            Logs.v(tag + " 168  " + Integer.toHexString(xmitter.passCRC));

            if (key.length() != 0) {
                if (pass.length() % 16 == 0) {
                    xmitter.passLen = pass.length();
                } else {
                    xmitter.passLen = (16 -
                            (pass.length() % 16)) + pass.length();
                }
                byte[] plainPass = new byte[xmitter.passLen];

                for (int i = 0; i < pass.length(); i++)
                    plainPass[i] =
                            pass.getBytes()[i];

                xmitter.passphrase = myEncrypt(key.getBytes(), plainPass);
            } else {
                xmitter.passphrase = pass.getBytes();
                xmitter.passLen =
                        pass.length();
            }
            xmitter.mac = new char[6];
            String[] macParts = mWifiInfo.getBSSID().split(":");

            Logs.e(tag + " 173 " + mWifiInfo.getBSSID());
            for (int i = 0; i < 6; i++)
                xmitter.mac[i] = (char) Integer.parseInt(macParts[i], 16);
            xmitter.resetStateMachine();
            xmitter.execute("");
        } catch (Error err) {
            Logs.e(tag + err.toString());
        }
    }


    private class xmitterTask extends AsyncTask<String, Void, String> {
        byte[] passphrase;
        char[] mac;
        int passLen;
        int passCRC;
        Handler handler;

        private int state, substate;

        public void resetStateMachine() {
            state = 0;
            substate = 0;
        }

        private void xmitRaw(int u, int m, int l) {
            MulticastSocket ms;
            InetAddress sessAddr;
            DatagramPacket dp;

            byte[] data = new byte[2];
            data = "a".getBytes();

            u = u & 0x7f; /* multicast's uppermost byte has only 7 chr */

            try {
                sessAddr = InetAddress.getByName("239." + u + "." + m + "." + l);
                ms = new MulticastSocket(1234);
                ms.joinGroup(sessAddr);
                ms.setTimeToLive(4);
                dp = new DatagramPacket(data, data.length, sessAddr, 5500);
                ms.send(dp);
                ms.close();
            } catch (UnknownHostException e) {
                Log.e("lcb", "326        Exiting 5");
            } catch (IOException e) {
                Log.d("lcb", 329 + "    " + e);
            }
        }

        private void xmitState0(int substate) {
            int i, j, k;

            k = mac[2 * substate];
            j = mac[2 * substate + 1];
            i = substate;

            xmitRaw(i, j, k);
        }

        private void xmitState1() {
            int u = (0x01 << 5) | (0x0);
            xmitRaw(u, passLen, passLen);
        }

        private void xmitState2(int substate, int len) {
            int u = substate | (0x2 << 5);

/*++++++++++++++++++  防止出现异常  +++++++++++++++++++++*/
            if (passphrase == null) {
                handler.sendEmptyMessage(ERROR);
                return;
            }


            int l = (0xff & passphrase[2 * substate]);
            int m;
            if (len == 2)
                m = (0xff & passphrase[2 * substate + 1]);
            else
                m = 0;
            xmitRaw(u, m, l);
        }

        private void xmitState3(int substate) {
            int i, j, k;

            k = (int) (passCRC >> ((2 * substate + 0) * 8)) & 0xff;
            j = (int) (passCRC >> ((2 * substate + 1) * 8)) & 0xff;
            i = substate | (0x3 << 5);

            xmitRaw(i, j, k);
        }

        private void stateMachine() {
            switch (state) {
                case 0:
                    if (substate == 3) {
                        state = 1;
                        substate = 0;
                    } else {
                        xmitState0(substate);
                        substate++;
                    }
                    break;
                case 1:
                    xmitState1();
                    if (passLen != 0) {
                        state = 2;
                    } else {
                        state = 3;
                    }
                    substate = 0;
                    break;
                case 2:
                    xmitState2(substate, 2);
                    substate++;
                    if (passLen % 2 == 1) {
                        if (substate * 2 == passLen - 1) {
                            xmitState2(substate, 1);
                            state = 3;
                            substate = 0;
                        }
                    } else {
                        if (substate * 2 == passLen) {
                            state = 3;
                            substate = 0;
                        }
                    }
                    break;
                case 3:
                    xmitState3(substate);
                    substate++;
                    if (substate == 2) {
                        substate = 0;
                        state = 0;
                    }
                    break;
                default:
                    Log.e("lcb", "471   I shouldn't be here");
            }
        }

        protected String doInBackground(String... params) {
            WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            WifiManager.MulticastLock mcastLock = wm.createMulticastLock("mcastlock");
            mcastLock.acquire();
            for (int i = 0; i < passLen; i++) {
            }

            int i = 0;

            while (true) {
                if (state == 0 && substate == 0)
                    i++;
                //每个十秒报告下时间  i % 10 == 0   && CheckID != null
                if (i == 70) {
                    Message msg = handler.obtainMessage();
                    msg.what = 43;
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                }
               /* 从80秒开始每隔十秒发送查询是否配置成功*/
                if (i >= 80 && i % 10 == 0 && i < 131) {
                    Logs.e(tag + " 457+++++++++++++++++++++++++++++++++++++++++++++++");
                    Message msg = handler.obtainMessage();
                    msg.what = 42;
                    handler.sendMessage(msg);
                }

				/*在尝试连接130次过后让用户重新发送*/
                if (i >= 131)
                    break;

                if (isCancelled())
                    break;

                stateMachine();

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Log.d("lcb", 453 + "      " + e);
                    break;
                }
            }

            mcastLock.release();

            if (i >= 131) {
                Logs.d(tag + "484-------------------------------------------------");
                Message msg = handler.obtainMessage();
                msg.what = 44;
                handler.sendMessage(msg);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }


    public static byte[] myEncrypt(byte[] key, byte[] plainText) {

        byte[] iv = new byte[16];
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

        for (int i = 0; i < 16; i++)
            iv[i] = 0;

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher;
        byte[] encrypted = null;
        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
            encrypted = cipher.doFinal(plainText);
        } catch (NoSuchAlgorithmException e) {
            Log.d("lcb", 501 + "    " + e);
        } catch (NoSuchPaddingException e) {
            Log.d("lcb", 502 + "    " + e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            Log.d("lcb", 505 + "    " + e);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            Log.d("lcb", 508 + "    " + e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            Log.d("lcb", 511 + "    " + e);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            Log.d("lcb", 514 + "    " + e);
        }
        return encrypted;
    }


    /**
     * 如果配置出现问题取消加载的dialog
     */
    private void SetError() {
        if (dialog != null) {
            dialog.close();
        }
        ToastUtil.showShort("配置出现问题");
    }


}

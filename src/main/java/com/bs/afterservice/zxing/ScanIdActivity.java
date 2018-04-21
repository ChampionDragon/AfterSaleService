package com.bs.afterservice.zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bs.afterservice.HttpByGet;
import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.constant.Constant;
import com.bs.afterservice.devmgr.ConnMethodActivity;
import com.bs.afterservice.utils.DialogAddId;
import com.bs.afterservice.utils.Logs;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Vector;

public class ScanIdActivity extends BaseActivity implements Callback {
    private CaptureActivityHandlerMy handler;
    private MyViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimerMy inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    //亮灯
    private CheckBox checkBox;
    private boolean isLight;
    private DialogAddId idDialog;
    private static final int CHECKID = 20;
    private static final int CHECKID_FAIL = 21;
    private static final int CHECKID_ERROR = 22;
    private String tag = "ScanIdActivity";
    private String idStr;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        // ViewUtil.addTopView(getApplicationContext(), this,
        // R.string.scan_card);
        CameraManagerMy.init(getApplication());
        viewfinderView = (MyViewfinderView) findViewById(R.id.viewfinder_view);

        /*有关"闪光灯"*/
        checkBox = (CheckBox) findViewById(R.id.cb_light);
        checkBox.setOnClickListener(lightListener);

        /*手动添加id操作*/
        findViewById(R.id.sacanid_tv).setOnClickListener(tvListener);

        findViewById(R.id.back_mipca).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ScanIdActivity.this.finish();

            }
        });
        hasSurface = false;
        inactivityTimer = new InactivityTimerMy(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManagerMy.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();

        if (resultString.equals("")) {
            Toast.makeText(ScanIdActivity.this, "二维码无法识别", Toast.LENGTH_SHORT).show();
            finish();
        } else if (resultString.length() == 10) {
            idStr = resultString;
            checkID(resultString);
        } else {
            bundle.putString("noid", resultString);
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManagerMy.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandlerMy(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public MyViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * 当嘟嘟声结束后，倒回排队。
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    /*亮灯的监听*/
    OnClickListener lightListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!isLight) {
                CameraManagerMy.get().flashControlHandler();
                checkBox.setChecked(true);
                isLight = true;
            } else {
                CameraManagerMy.get().flashControlHandler();
                checkBox.setChecked(false);
                isLight = false;
            }
        }
    };


    /*手动添加ID的监听以及相关的操作*/
    OnClickListener tvListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            idDialog = new DialogAddId(ScanIdActivity.this, new DialogAddId.putIdListener() {
                @Override
                public void putId(String id) {
                    if (id.equals("")) {
                        Toast.makeText(ScanIdActivity.this, "输入的设备ID号不能为空 ", Toast.LENGTH_SHORT).show();
                    } else if (id.length() == 10) {
                        idStr = id;
                        checkID(id);
                    } else {
                        Toast.makeText(ScanIdActivity.this, "输入的不是设备ID号", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    };

    /*向后台核实这个ID是否存在     BE0067BE49    */
    private void checkID(final String id) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String url = Constant.checkId + HttpByGet.get("id", id);
                String result = HttpByGet.executeHttpGet(url);
                if (result.equals(HttpByGet.error)) {
                    ScanIdActivity.this.setResult(RESULT_OK, new Intent().putExtra("Backstage", "Backstage"));
                    finish();
                } else {
                    result = result.replace("(", "").replace(")", "");
                    Response(result);
                }
            }
        });
    }


    /*解析返回的参数*/
    private void Response(String response) {
        Logs.d(tag + "301  " + response);
        String ret = "";
        try {
            JSONObject jo = new JSONObject(response);
            ret = jo.getString("ret");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (ret.equals("fail")) {
            mHandler.sendEmptyMessage(CHECKID_FAIL);
        }
        if (ret.equals("ok")) {
            mHandler.sendEmptyMessage(CHECKID);
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CHECKID:
                    startActivity(new Intent(ScanIdActivity.this, ConnMethodActivity.class).putExtra(Constant.deviceId, idStr));
                    finish();
                    break;
                case CHECKID_FAIL:
                    Bundle bundle = new Bundle();
                    Intent resultIntent = new Intent();
                    bundle.putString("error", idStr);
                    resultIntent.putExtras(bundle);
                    ScanIdActivity.this.setResult(RESULT_OK, resultIntent);
                    finish();
                    break;
            }


        }
    };


}

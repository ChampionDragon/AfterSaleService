package com.bs.afterservice.user;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bs.afterservice.R;
import com.bs.afterservice.base.BaseActivity;
import com.bs.afterservice.devmgr.AuthorizeActivity;
import com.bs.afterservice.utils.Logs;
import com.bs.afterservice.utils.SmallUtil;
import com.bs.afterservice.utils.ToastUtil;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 售后人员添加
 * AUTHOR: Champion Dragon
 * created at 2018/3/20
 **/
public class CustSerAddActivity extends BaseActivity implements View.OnClickListener {
    private EditText et;
    public static String activity = "activity";
    public static String titlestr = "title";
    public static String list = "list";
    private String[] arrays;
    private String tag = "CustSerAddActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseapp.addActivity(this);
        setContentView(R.layout.activity_cust_ser_add);
        initData();
        intView();
    }

    private void initData() {
        TextView title = (TextView) findViewById(R.id.custseradd_title);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title.setText(extras.getString(titlestr));
            activity = extras.getString(activity);
            arrays = extras.getStringArray(list);
        } else {
            arrays = new String[]{};
        }
//        Logs.d(arrays.length + "初始化数组大小   " + tag + " 50");

    }

    private void intView() {
        findViewById(R.id.custseradd).setOnClickListener(this);
        et = (EditText) findViewById(R.id.custseradd_et);
        findViewById(R.id.custseradd_btn).setOnClickListener(this);
        findViewById(R.id.custseradd_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custseradd:
                finishAct();
                break;
            case R.id.custseradd_tv:
                addNumber();
                break;
            case R.id.custseradd_btn:
                checkEt();
                break;
        }
    }

    /*跳转到系统通讯录里获取号码*/
    private void addNumber() {
        startActivityForResult(new Intent(
                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 1);
    }

    /*验证输入的格式是否正确*/
    private void checkEt() {
        String msg = et.getText().toString();
        if (checkEmail(msg)) {
            backAct();
        } else if (checkMobileNumber(msg)) {
            backAct();
        } else {
            ToastUtil.showLong("输入的电话\"号码/邮箱\"格式不对");
        }
    }

    /*返回操作*/
    private void backAct() {
        //判断是否添加这个号码，防止和添加已经存在的电话

        boolean isSame = false;
        String data = et.getText().toString();
//        Logs.e(tag + " 102  arrays大小 " + arrays.length);

        for (String s : arrays) {
            Logs.e(s.equals(data) + " 号码是否相同");
            if (s.equals(data)) {
                isSame = true;
            }
        }

        if (!isSame) {
            arrays = Arrays.copyOf(arrays, arrays.length + 1);
            arrays[arrays.length - 1] = data;
        }

//        Logs.i(tag + " 115  arrays大小 " + arrays.length);

        finishAct();
    }

    /*结束这个类*/
    private void finishAct() {
        if (activity.equals("CustSerNewActivity")) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(list, arrays);
            SmallUtil.getActivity(this, CustSerNewActivity.class, bundle);
            baseapp.finishActivity();
        } else if (activity.equals("AuthorizeActivity")) {
            Bundle bundle = new Bundle();
            bundle.putStringArray(list, arrays);
            SmallUtil.getActivity(this, AuthorizeActivity.class, bundle);
            baseapp.finishActivity();
        } else {
            baseapp.finishActivity();
        }
    }


    /* 验证邮箱*/
    public boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /*验证电话号码
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     */
    public boolean checkMobileNumber(String mobileNumber) {
        boolean flag = false;
        try {
            Pattern regex = Pattern.compile("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0-9]))\\d{8}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            ContentResolver reContentResolverol = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = reContentResolverol.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone.moveToNext()) {
                String usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                Logs.i("用户名：" + username + "   电话号码：" + usernumber + "  id:  " + contactId);
                et.setText(usernumber.replace(" ", ""));
            }
        }
    }


    private String getPhoneNumber(Intent intent) {
        Cursor cursor = null;
        Cursor phone = null;
        try {
            String[] projections = {ContactsContract.Contacts._ID, ContactsContract.Contacts.HAS_PHONE_NUMBER};
            cursor = getContentResolver().query(intent.getData(), projections, null, null, null);
            if ((cursor == null) || (!cursor.moveToFirst())) {
                return null;
            }
            int _id = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID);
            String id = cursor.getString(_id);
            int has_phone_number = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER);
            int hasPhoneNumber = cursor.getInt(has_phone_number);
            String phoneNumber = null;
            if (hasPhoneNumber > 0) {
                phone = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + id, null, null);
                while (phone.moveToNext()) {
                    int index = phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = phone.getString(index);
                    phoneNumber = number;
                }
            }
            return phoneNumber;
        } catch (Exception e) {

        } finally {
            if (cursor != null) cursor.close();
            if (phone != null) phone.close();
        }
        return null;
    }


}

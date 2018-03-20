package com.example.zlp.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zlp.BuildConfig;
import com.example.zlp.R;
import com.example.zlp.adapter.PhoneAdater;
import com.example.zlp.modle.PhoneNumber;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Permission;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GetPhoneNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv;
    private Button bt;
    private List<PhoneNumber> list;
    private boolean isFirst = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_phone_number);

        lv = (ListView) findViewById(R.id.lv);
        bt = (Button) findViewById(R.id.bt_get_phone_number);

        bt.setOnClickListener(this);


    }


    private List<PhoneNumber> getPhoneNum(Context context) {
        List<PhoneNumber> phones = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, "sort_key");

        while (cursor.moveToNext()) {
            PhoneNumber number = new PhoneNumber();
            String phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            if (TextUtils.isEmpty(phoneNum)) continue;
            String phoneName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            number.setPhontName(phoneName);
            number.setPhoneNum(phoneNum);
            phones.add(number);
        }
        cursor.close();


        return phones;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_get_phone_number:

                if (Build.VERSION.SDK_INT >= 23) {

                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 1000);
                    } else {
                        Log.d("YiLog","哈哈。。。。。。。。。。。");
                        list = getPhoneNum(this);
                        setDataToAdapter();
                    }
                } else {
                    list = getPhoneNum(this);
                    if (isFirst == false && list.isEmpty()) {
                        gotoMiuiPermission();
                    }
                    if (!list.isEmpty()) {
                        setDataToAdapter();
                    }
                    if (list.isEmpty()) {
                        isFirst = false;
                    }
                }
                break;
        }
    }

    private void setDataToAdapter() {
        JSONArray array = new JSONArray();
        try {
            for (int i = 0; i < list.size(); i++) {
                JSONObject object = new JSONObject();
                object.put("phoneNumber", list.get(i).getPhoneNum());
                object.put("userName", list.get(i).getPhontName());
                array.put(object);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("YiLog", array.toString());
        PhoneAdater adater = new PhoneAdater(this, list);
        lv.setAdapter(adater);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                list = getPhoneNum(this);
                setDataToAdapter();
            } else {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)) {
                    AlertDialog dialog = new AlertDialog.Builder(this).setMessage("该功能需要赋予访问通讯录的权限，不开启将无法正常工作！").setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = getAppDetailSettingIntent();
                            startActivity(intent);
                            dialog.dismiss();
                            return;
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
                    dialog.show();
                    return;
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }


    /**
     * 跳转到miui的权限管理页面
     */
    private void gotoMiuiPermission() {
        Intent i = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        i.setComponent(componentName);
        i.putExtra("extra_pkgname", getPackageName());
        try {
            startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
            gotoMeizuPermission();
        }
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private void gotoMeizuPermission() {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoHuaweiPermission();
        }
    }


    /**
     * 华为的权限管理页面
     */
    private void gotoHuaweiPermission() {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            startActivity(getAppDetailSettingIntent());
        }

    }
}



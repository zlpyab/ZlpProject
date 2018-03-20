package com.example.zlp.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.zlp.R;
import com.example.zlp.utils.PermissionUtils;

import java.util.Arrays;

public class PermissionActivity extends Activity implements View.OnClickListener {

    // 相机权限、多个权限
    private final String PERMISSION_CAMERA = Manifest.permission.READ_CONTACTS;
    private final String[] PERMISSIONS = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION};


    // 打开相机请求Code，多个权限请求Code
    private final int REQUEST_CODE_CAMERA = 1,REQUEST_CODE_PERMISSIONS=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findViewById(R.id.bt_one).setOnClickListener(this);
        findViewById(R.id.bt_two).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_one:
                requestPermission();
                break;
            case R.id.bt_two:
                requestMorePermissions1();
                break;
        }
    }

    //请求读写、联系人权限
    private void requestMorePermission() {
        PermissionUtils.checkMorePermissions(this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                 toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                 showExplainDialog(permission, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         PermissionUtils.requestMorePermissions(PermissionActivity.this,PERMISSIONS,REQUEST_CODE_PERMISSIONS);
                     }
                 });
            }
            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(PermissionActivity.this,PERMISSIONS,REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    // 自定义申请多个权限
    private void requestMorePermissions1(){
        PermissionUtils.checkMorePermissions(this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestMorePermissions(PermissionActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestMorePermissions(PermissionActivity.this, PERMISSIONS, REQUEST_CODE_PERMISSIONS);
            }
        });
    }

    //请求相机权限
    private void requestPermission() {
        PermissionUtils.checkPermission(this, PERMISSION_CAMERA, new PermissionUtils.PermissionCheckCallBack() {
            @Override
            public void onHasPermission() {
                toCamera();
            }

            @Override
            public void onUserHasAlreadyTurnedDown(String... permission) {
                showExplainDialog(permission, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.requestPermission(PermissionActivity.this,PERMISSION_CAMERA,REQUEST_CODE_CAMERA);
                    }
                });
            }

            @Override
            public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                PermissionUtils.requestPermission(PermissionActivity.this,PERMISSION_CAMERA,REQUEST_CODE_CAMERA);
            }
        });

    }

    private void toCamera() {
        Intent intent = new Intent();
        intent.setAction("android.media.action.STILL_IMAGE_CAMERA");
        startActivity(intent);
    }

    /**
     * 解释权限的dialog
     *
     */
    private void showExplainDialog(String[] permission, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(this)
                .setTitle("申请权限")
                .setMessage("我们需要" + Arrays.toString(permission)+"权限")
                .setPositiveButton("确定", onClickListener)
                .show();
    }

    /**
     * 显示前往应用设置Dialog
     *
     */
    private void showToAppSettingDialog() {
        new AlertDialog.Builder(this)
                .setTitle("需要权限")
                .setMessage("我们需要相关权限，才能实现功能，点击前往，将转到应用的设置界面，请开启应用的相关权限。")
                .setPositiveButton("前往", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PermissionUtils.toAppSetting(PermissionActivity.this);
                    }
                })
                .setNegativeButton("取消", null).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                PermissionUtils.onRequestPermissionResult(this, PERMISSION_CAMERA, grantResults, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                         toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                         showToAppSettingDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        showToAppSettingDialog();
                    }
                });
                break;
            case REQUEST_CODE_PERMISSIONS:
                PermissionUtils.onRequestMorePermissionsResult(PermissionActivity.this, PERMISSIONS, new PermissionUtils.PermissionCheckCallBack() {
                    @Override
                    public void onHasPermission() {
                        toCamera();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDown(String... permission) {
                        showToAppSettingDialog();
                    }

                    @Override
                    public void onUserHasAlreadyTurnedDownAndDontAsk(String... permission) {
                        showToAppSettingDialog();
                    }
                });

                break;
        }

    }


}

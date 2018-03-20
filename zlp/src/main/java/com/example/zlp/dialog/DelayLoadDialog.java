package com.example.zlp.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zlp.R;


/**
 * Created by Administrator on 2016/11/26 0026.
 * 延迟加载体验框
 */
public class DelayLoadDialog extends ProgressDialog {

    private ProgressBar loadPb;
    private TextView loadTv;
//    private String message = "加载中...";
    private String message = "";

    public DelayLoadDialog(Context context) {
        this(context, R.style.MyDialogStyle);
    }

    public DelayLoadDialog(Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_dialog_deley_load);
        setCanceledOnTouchOutside(false);//按对话框以外的地方不起作用。按返回键还起作用
//        setCancelable(false);//按对话框以外的地方不起作用。按返回键也不起作用
        loadPb = (ProgressBar) findViewById(R.id.pb_load);
        loadTv = (TextView) findViewById(R.id.tv_load_dialog);
        loadTv.setText(message);
    }

    /** 设置加载内容 */
    public void setMessage(String message){
        this.message = message;
    }

    /** 设置加载内容 */
    public void setMessage(int resourcesId){
        this.message = getContext().getResources().getString(resourcesId);
    }

}

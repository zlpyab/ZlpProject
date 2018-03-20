package com.example.zlp.wight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.zlp.R;

/**
 * Created by Administrator on 2016/11/3.
 */
public class CustomDialot extends Dialog {

    private Context context;
    private String title;
    private String leftBt;
    private String rightBt;

    public void setOnclickCustomDialogListener(OnclickCustomDialogListener onclickCustomDialogListener) {
        this.onclickCustomDialogListener = onclickCustomDialogListener;
    }

    OnclickCustomDialogListener onclickCustomDialogListener;

    public CustomDialot(Context context,String title,String leftBt,String rightBt) {
        super(context, R.style.Custom_Dialog_Dim);
        this.context = context;
        this.title = title;
        this.leftBt = leftBt;
        this.rightBt = rightBt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_dialot_layout,null);
        setContentView(view);

        TextView tv = (TextView) view.findViewById(R.id.tv_title);
        Button btLeft = (Button) view.findViewById(R.id.bt_left);
        Button btRight = (Button) view.findViewById(R.id.bt_right);

        tv.setText(title);
        btLeft.setText(leftBt);
        btRight.setText(rightBt);

        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickCustomDialogListener != null){
                    onclickCustomDialogListener.doLeftBt();
                }
            }
        });
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclickCustomDialogListener != null){
                    onclickCustomDialogListener.doRightBt();
                }
            }
        });

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        params.width = (int) (d.widthPixels*0.8);
        dialogWindow.setAttributes(params);
    }

    public interface OnclickCustomDialogListener{
        public  void doLeftBt();
        public  void doRightBt();
    }
}

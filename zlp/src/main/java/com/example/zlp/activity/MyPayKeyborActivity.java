package com.example.zlp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.zlp.R;
import com.example.zlp.wight.PopEnterPassword;

public class MyPayKeyborActivity extends Activity implements View.OnClickListener {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pay_keybor);

        initView();
    }

    private void initView() {
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.showAtLocation(this.findViewById(R.id.layoutContent),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
        popEnterPassword.setOnFinishPswListener(new PopEnterPassword.OnFinishPswListener() {
            @Override
            public void onFinishPsw(String psw) {
                Toast.makeText(MyPayKeyborActivity.this,psw,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

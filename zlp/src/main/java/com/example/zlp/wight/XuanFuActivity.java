package com.example.zlp.wight;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zlp.R;
import com.example.zlp.WindowService;

public class XuanFuActivity extends Activity implements View.OnClickListener {

    private Button btStart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_fu);
        initView();
    }

    private void initView() {
        btStart = (Button) findViewById(R.id.bt_start);
        btStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_start:
                startService(new Intent(this, WindowService.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        stopService(new Intent(this,WindowService.class));
    }
}

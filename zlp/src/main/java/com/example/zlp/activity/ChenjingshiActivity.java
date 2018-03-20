package com.example.zlp.activity;

import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.zlp.R;
import com.example.zlp.utils.StatusBarCompat;

import org.zackratos.ultimatebar.UltimateBar;

import java.lang.reflect.Method;

public class ChenjingshiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chenjingshi);

        UltimateBar ultimateBar = new UltimateBar(this);
        //ultimateBar.setColorBar(ContextCompat.getColor(this, R.color.color_title));  //设置状态栏颜色

        ultimateBar.setImmersionBar();

    }
}

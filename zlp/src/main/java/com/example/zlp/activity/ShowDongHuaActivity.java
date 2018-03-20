package com.example.zlp.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zlp.R;

public class ShowDongHuaActivity extends Activity {

    private Button btStart;
    private ImageView iv;
    private TranslateAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_dong_hua);

        btStart = (Button) findViewById(R.id.bt_pingyi);
        iv = (ImageView) findViewById(R.id.iv_pic);

        animation = new TranslateAnimation(0, 500, 0, 500);
        animation.setDuration(1000);
        animation.setRepeatCount(2);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setFillAfter(true);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // iv.startAnimation(animation);
                TranslateAnimation animation = new TranslateAnimation(0, -8, 0, 0);
                animation.setInterpolator(new OvershootInterpolator());
                animation.setDuration(100);
                animation.setRepeatCount(3);
                animation.setRepeatMode(Animation.REVERSE);
                iv.startAnimation(animation);
//                ObjectAnimator
//                        .ofFloat(iv,"translationX",0,500)
//                        .setDuration(2000)
//                        .start();
//                ObjectAnimator
//                        .ofFloat(iv,"translationY",0,500)
//                        .setDuration(2000)
//                        .start();
            }
        });
    }
}

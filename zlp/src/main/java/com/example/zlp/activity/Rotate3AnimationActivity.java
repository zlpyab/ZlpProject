package com.example.zlp.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.zlp.R;
import com.example.zlp.wight.Rotate3dAnimation;

public class Rotate3AnimationActivity extends Activity {

    private ImageView imageView;
    int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_animation);

        imageView = (ImageView) findViewById(R.id.iv_img);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float centerX = imageView.getWidth()/2;
                float centerY = imageView.getHeight()/2;
                if (i %2 == 0){
                    Rotate3dAnimation animation = new Rotate3dAnimation(Rotate3AnimationActivity.this,0,180,centerX,centerY,0f,false);
                    animation.setDuration(2000);
                    animation.setFillAfter(false);//保持旋转后效果
                    animation.setInterpolator(new LinearInterpolator());//设置插值器

                    imageView.startAnimation(animation);
                }else {
                    Rotate3dAnimation animation = new Rotate3dAnimation(Rotate3AnimationActivity.this,-180,0,centerX,centerY,0f,false);
                    animation.setDuration(2000);
                    animation.setFillAfter(false);//保持旋转后效果
                    animation.setInterpolator(new LinearInterpolator());//设置插值器

                    imageView.startAnimation(animation);
                }
                i++;

            }
        });
    }
}

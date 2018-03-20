package com.example.zlp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.zlp.R;
import com.example.zlp.wight.MyViewPager;

public class MyViewPagerActivity extends AppCompatActivity {

    private MyViewPager myViewPager;
    private int[] images = new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_view_pager);

        myViewPager = (MyViewPager) findViewById(R.id.viewPager);

        for (int i=0;i<images.length ;i++){
            ImageView view = new ImageView(this);
            view.setBackgroundResource(images[i]);
            myViewPager.addView(view);
        }

        View view = LayoutInflater.from(this).inflate(R.layout.list_item,null);
        myViewPager.addView(view,1);

    }
}

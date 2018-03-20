package com.example.zlp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.zlp.R;

public class GuideActivity extends Activity implements GestureDetector.OnGestureListener {

    private int position = 0;
    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position=  getIntent().getIntExtra("position",0);
        switch (position){
            case 0:
                setContentView(R.layout.activity_guide);
                break;
            case 1:
                setContentView(R.layout.activity_guide2);
                break;
            case 2:
                setContentView(R.layout.activity_guide3);
                break;
            case 3:
                setContentView(R.layout.activity_guide4);
                break;

        }

        initView();

    }

    private void initView() {

        detector = new GestureDetector(this);
        if (position == 0){
            Button btNext = (Button) findViewById(R.id.bt_next);
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     position = 1;
                    Intent intent = new Intent(GuideActivity.this,GuideActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                }
            });
        }else if (position == 1){
            Button btNext = (Button) findViewById(R.id.bt_next);
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = 2;
                    Intent intent = new Intent(GuideActivity.this,GuideActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                }
            });
        }else if (position == 2){
            Button btNext = (Button) findViewById(R.id.bt_next);
            btNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = 3;
                    Intent intent = new Intent(GuideActivity.this,GuideActivity.class);
                    intent.putExtra("position",position);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);
                }
            });
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.detector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("YiLog",e.getX()+"");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 120){
            if (position < 3){
                position++;
                Intent intent = new Intent(this,GuideActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_right,R.anim.slide_out_to_left);

            }
        }else if (e1.getX() - e2.getX() < -120){
            if (position > 0){
                position--;
                Intent intent = new Intent(this,GuideActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,R.anim.slide_out_to_right);
            }
        }
        return true;
    }
}

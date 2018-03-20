package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2016/11/14.
 * 水波浪
 */
public class MyRingWave extends View {
    private final static int MIN_DX = 10;
    private ArrayList<Wave> mWaveList = new ArrayList<>();
    private int[] mColors = new int[]{Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN};

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            flushData();
            invalidate();

            if (!mWaveList.isEmpty()) {
                mHandler.sendEmptyMessageDelayed(0, 50);
            }
        }
    };


    public MyRingWave(Context context) {
        super(context);
    }

    public MyRingWave(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRingWave(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void flushData() {
        ArrayList<Wave> removeWave = new ArrayList<>();
        for (Wave wave : mWaveList) {
            wave.radius += 3;
            wave.paint.setStrokeWidth(wave.radius /3);
            if (wave.paint.getAlpha() <= 0) {
                removeWave.add(wave);
                continue;
            }
            int alpha = wave.paint.getAlpha();

            alpha -= 5;
            if (alpha < 0) {
                alpha = 0;
            }
            wave.paint.setAlpha(alpha);
        }
        mWaveList.removeAll(removeWave);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Wave wave : mWaveList) {
            canvas.drawCircle(wave.cx, wave.cy, wave.radius, wave.paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.d("hh",event.getX()+"");
                addPoint((int) event.getX(), (int) event.getY());
                break;
        }
        return true;
    }

    private void addPoint(int x, int y) {
        if (mWaveList.isEmpty()) {
            addWave(x, y);
            mHandler.sendEmptyMessage(0);
        } else {
            Wave wave = mWaveList.get(mWaveList.size() - 1);//得到集合中最后一个Wave
            if (Math.abs(x - wave.cx) > MIN_DX || Math.abs(y - wave.cy) > MIN_DX){//设置圆环之间的间距
                addWave(x, y);
            }


        }

    }

    private void addWave(int x, int y) {
        Wave wave = new Wave();
        wave.cx = x;
        wave.cy = y;

        Paint paint = new Paint();
        paint.setStrokeWidth(wave.radius / 3);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAlpha(255);

        //随机颜色
//        Random random = new Random();
//        random.nextInt(4);//0-3的随机数
        int color = (int)(Math.random() * 4);
        paint.setColor(mColors[color]);
        wave.paint = paint;

        mWaveList.add(wave);
    }

    class Wave {
        public int cx;
        public int cy;
        public int radius;
        public Paint paint;
    }
}

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

/**
 * Created by Administrator on 2016/11/11.
 * 自定义圆弧
 */
public class RoundAc extends View {

    private Paint mPaint;
    private int x;
    private int y;
    private int mRadius = 100;

    public RoundAc(Context context) {
        super(context, null);
        init();
    }

    public RoundAc(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        init();

    }

    public RoundAc(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //半径变大，宽度变大，颜色变浅
            mRadius+=5;
            mPaint.setStrokeWidth(mRadius/3);
            int alpha = mPaint.getAlpha();
            alpha-=5;
            if (alpha < 0){
                alpha = 0;
            }
            mPaint.setAlpha(alpha);
            invalidate();
            if (alpha > 0){
                mHandler.sendEmptyMessageDelayed(0,40);
            }

        }
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x,y,mRadius,mPaint);
    }


    private void init() {
        mRadius = 0;

        mPaint = new Paint();
        mPaint.setStrokeWidth(mRadius/3);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);//设置空心
        mPaint.setAlpha(255);//设置透明度
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = (int)event.getX();
                y = (int) event.getY();

                init();

                mHandler.sendEmptyMessage(0);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}

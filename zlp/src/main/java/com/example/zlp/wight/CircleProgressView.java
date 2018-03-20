package com.example.zlp.wight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by zlp on 2017/5/12.
 */
public class CircleProgressView extends View {

    private int mWidth, mHeight, mRadius;
    private Paint mPaint;
    private float degree;
    private ValueAnimator degreeAnimator;

    public CircleProgressView(Context context) {
        super(context);
        init();
    }


    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        }else {
            width = widthSize *1/2;
        }
        if (heightMode == MeasureSpec.EXACTLY){
            height = heightSize;
        }else {
            height = widthSize * 1/2;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mWidth/10;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(degree);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i=0;i<12;i++){
            if (i == 0|| i==1||i==2){
                mPaint.setColor(Color.GRAY);
            }else {
                mPaint.setColor(Color.LTGRAY);
            }
            RectF rectf = new RectF(-mRadius/4,-mRadius*7/2, mRadius/4,-mRadius*2);
            canvas.drawRoundRect(rectf,mRadius/8,mRadius/8,mPaint);
            canvas.rotate(30);
            canvas.save();
        }
        canvas.restore();
    }

    public void startAnimation(){
        degree = 0;
        degreeAnimator = ValueAnimator.ofFloat(0,360);
        degreeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degree = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        degreeAnimator.setRepeatCount(ValueAnimator.INFINITE);
        degreeAnimator.setDuration(700);
        degreeAnimator.setInterpolator(new LinearInterpolator());
        degreeAnimator.start();
    }

    public void endAnimation(){
        degreeAnimator.end();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus){
            endAnimation();
        }
    }

}

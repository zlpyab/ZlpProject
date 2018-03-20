package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zlp.R;

/**
 * Created by zlp on 2017/5/4.
 */
public class BitmapShaderView extends View {

    private Paint mFillPaint,mStrokPaint;// 填充笔和描边笔

    private float posX,posY;//触摸点坐标

    private BitmapShader shader;

    public BitmapShaderView(Context context) {
        super(context);
    }

    public BitmapShaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }



    public BitmapShaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init() {
        mStrokPaint = new Paint();
        mStrokPaint.setStyle(Paint.Style.STROKE);
        mStrokPaint.setColor(Color.BLACK);
        mStrokPaint.setStrokeWidth(5);


        mFillPaint = new Paint();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_poly);
        shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        mFillPaint.setShader(shader);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                posX = event.getX();
                posY = event.getY();
                invalidate();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.DKGRAY);
        canvas.drawCircle(posX,posY,300,mStrokPaint);
        canvas.drawCircle(posX,posY,300,mFillPaint);
    }
}

package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.zlp.R;


/**
 * Created by zlp on 2017/5/3.
 * 设置多边形
 */
public class SetPolyToPoly extends View {

    private int testPoint = 4;
    private int triggerRadius = 200; //触发半径为180

    private Bitmap mBitmap;//要绘制的图片
    private Matrix mPolyMatrix;         // 测试setPolyToPoly用的Matrix

    private float[] src = new float[8];
    private float[] dst = new float[8];

    private Paint pointPaint;
    private int width;
    private int height;

    public SetPolyToPoly(Context context) {
        this(context, null);
    }

    public SetPolyToPoly(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initBitmapAndMatrix();
    }


    public SetPolyToPoly(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void initBitmapAndMatrix() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_poly);

        float[] temp = {0, 0,      //左上
                mBitmap.getWidth(), 0,//右上
                mBitmap.getWidth(), mBitmap.getHeight(),//右下
                0, mBitmap.getHeight()//左下
        };

        src = temp.clone();
        dst = temp.clone();

        pointPaint = new Paint();
        pointPaint.setAntiAlias(true);
        pointPaint.setStrokeWidth(50);
        pointPaint.setColor(0xffd19165);
        pointPaint.setStrokeCap(Paint.Cap.ROUND);

        mPolyMatrix = new Matrix();
        mPolyMatrix.setPolyToPoly(src,0,src,0,4);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                float temX =  event.getX();
                float temy =  event.getY();

                for (int i= 0;i<testPoint *2;i+=2){
                    if (Math.abs(temX - dst[i]) <= triggerRadius && Math.abs(temy - dst[i+1]) <= triggerRadius){
                        dst[i] = temX - 100;
                        dst[i+1] = temy - 100;
                        break;
                    }
                }
                resetPolyMatrix(testPoint);
                invalidate();
                break;
        }
        return true;
    }

    private void resetPolyMatrix(int testPoint) {
         mPolyMatrix.reset();
         mPolyMatrix.setPolyToPoly(src,0,dst,0,testPoint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(200,200);
        canvas.drawBitmap(mBitmap,mPolyMatrix,null);

        float[] dst = new float[8];
        mPolyMatrix.mapPoints(dst,src);

        for (int i=0;i<testPoint *2;i+=2){
            canvas.drawPoint(dst[i],dst[i+1],pointPaint);
        }

    }

    public void setTestPoint(int testPoint){
        this.testPoint = testPoint > 4|| testPoint < 0 ? 4 : testPoint;
        dst = src.clone();
        resetPolyMatrix(this.testPoint);
        invalidate();
    }
}

package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zlp on 2017/4/13.
 */
public class MyCustomView extends View {

    private Paint mPaint;
    private int count = 6;
    private int centerX;
    private int centerY;
    private float raduis;
    private float angle = (float) (Math.PI * 2 / count);


    private int mWidth, mHeight;

    private int[] mColors = {Color.GREEN, Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};

    public MyCustomView(Context context) {
        super(context);
        init();
    }


    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        centerX = mWidth / 2;
        centerY = mHeight / 2;
        raduis = Math.min(mWidth, mHeight) /2* 0.9f;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //弧形
//        RectF rect = new RectF(100,100,600,600);
//        mPaint.setColor(Color.GRAY);
//        canvas.drawRect(rect,mPaint);
//
//        mPaint.setColor(Color.BLUE);
//        canvas.drawArc(rect,0,270,true,mPaint);


        //平移加缩放
//        canvas.translate(mWidth/2,mHeight/2);
//        RectF rectF = new RectF(-400,-400,400,400);
//
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(10);
//        for (int i = 0;i<20;i++){
//            canvas.scale(0.9f,0.9f);
//            canvas.drawRect(rectF,mPaint);
//        }

//        //平移加旋转
//        canvas.translate(mWidth/2,mHeight/2);
//        mPaint.setStrokeWidth(5);
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.STROKE);
//        canvas.drawCircle(0,0,400,mPaint);    //画两个圆
//        canvas.drawCircle(0,0,380,mPaint);
//
//        for (int i=0;i<360;i+=10){
//            canvas.drawLine(0,400,0,380,mPaint);
//            canvas.rotate(10);
//        }

        //蜘蛛网
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(5);
//        mPaint.setAntiAlias(true);
//
//        drawPolygon(canvas);
//        drawLines(canvas);

        //setLastPosition
//        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStrokeWidth(8);
//        mPaint.setAntiAlias(true);
//
//        canvas.translate(mWidth/2,mHeight/2);
//        Path path = new Path();
//        path.addRect(-200,-200,200,200, Path.Direction.CCW);
//        path.setLastPoint(-300,300);
//
//        mPaint.setColor(Color.BLUE);
//        canvas.drawPath(path,mPaint);
//
//        canvas.rotate(180);
//        mPaint.setColor(Color.RED);
//        canvas.drawPath(path,mPaint);

        //八卦阴阳鱼
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(0,0,200,mPaint);
        mPaint.setStyle(Paint.Style.FILL);

        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        Path path4 = new Path();

        path1.addCircle(0,0,200, Path.Direction.CW);
        path2.addRect(0,-200,200,200, Path.Direction.CW);
        path3.addCircle(0,-100,100, Path.Direction.CW);
        path4.addCircle(0,100,100, Path.Direction.CCW);

        path1.op(path2, Path.Op.DIFFERENCE);//差
        path1.op(path3, Path.Op.UNION);//并
        path1.op(path4, Path.Op.DIFFERENCE);

        canvas.drawPath(path1,mPaint);

        canvas.drawCircle(0,100,20,mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(0,-100,20,mPaint);


    }



    //绘制正多边形
    private void drawPolygon(Canvas canvas) {
        Path mPath = new Path();
        float r = raduis / (count - 1);//蜘蛛丝之间的距离
        for (int i = 1; i < count; i++) {//中心不用绘制
            float curR = r * i;//当前半径
            mPath.reset();
            for (int j = 0; j < count; j++) {
                if (j == 0) {

                    mPath.moveTo(centerX+curR, centerY);

                } else { //根据半径，计算出蜘蛛丝上每个点的坐标
                    float x = (float) (centerX + curR * Math.cos(angle * j));
                    float y = (float) (centerY + curR * Math.sin(angle * j));
                    mPath.lineTo(x, y);
                }
            }
             mPath.close();
            canvas.drawPath(mPath, mPaint);
        }

    }

    //绘制线条
    private void drawLines(Canvas canvas) {

         Path path = new Path();
        for (int i=0;i<count;i++){
            path.reset();
            path.moveTo(centerX,centerY);
            float x = (float) (centerX+raduis*Math.cos(angle*i));
            float y = (float) (centerY+raduis*Math.sin(angle*i));

            path.lineTo(x,y);

            canvas.drawPath(path,mPaint);

        }
    }
}

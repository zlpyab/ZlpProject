package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.zlp.modle.PieData;

import java.util.ArrayList;

/**
 * Created by zlp on 2017/4/13.
 */
public class CustomBinTuView extends View {

    private Paint mPaint = new Paint();
    private int mWidth,mHeight;
    private float mStartAngle = 0;
    private ArrayList<PieData> mData;

    // 颜色表(注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};


    public CustomBinTuView(Context context) {
        super(context);
    }

    public CustomBinTuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    public CustomBinTuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null == mData){
            return;
        }

        float currentStarAngle = mStartAngle;
        canvas.translate(mWidth/2,mHeight/2);
        float r = (float) (Math.min(mWidth,mHeight)/2 );
        RectF rectF = new RectF(-r,-r,r,r);

        for (int i = 0;i<mData.size();i++){
            PieData pieData = mData.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF,currentStarAngle,pieData.getAngle(),true,mPaint);
            currentStarAngle += pieData.getAngle();
        }
    }

    public void setmStartAngle(float mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();
    }

    public void setmData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();
    }

    private void initData(ArrayList<PieData> mData) {

         if ( null == mData || mData.size() == 0){
             return;
         }

         int sumValue = 0;

        for (int i = 0;i<mData.size();i++){
            PieData pieData = mData.get(i);
            sumValue += pieData.getValue();   //累计数值
            int j = i % mColors.length;
            pieData.setColor(mColors[j]);     //设置颜色
        }

        int sumAngle = 0;
        for (int i =0;i<mData.size();i++){
            PieData pieData = mData.get(i);

            float percent = pieData.getValue()/sumValue; //计算百分比
            float angle = percent * 360;   //计算角度

            pieData.setAngle(angle);
            pieData.setPercentage(percent);

            sumAngle += angle;
        }
    }
}

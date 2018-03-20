package com.example.zlp.wight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import com.example.zlp.R;


/**
 * Created by zlp on 2016/8/3 11:05.
 * 自定义罗盘
 */
public class CompassView extends View {

    private float bearing;//方向属性

    private Paint marketPaint;
    private Paint textPaint;
    private Paint circlePaint;
    private String northString;
    private String eastString;
    private String southString;
    private String westString;
    private int textHeight;

    public float getBearing() {
        return bearing;
    }

    //添加可访问性支持
    public void setBearing(float bearing) {
        this.bearing = bearing;
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }


    public CompassView(Context context) {
        super(context);
        init();
    }

    public CompassView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);

        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(getResources().getColor(R.color.background_color));
        circlePaint.setStrokeWidth(1);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        northString = getResources().getString(R.string.cardinal_north);
        southString = getResources().getString(R.string.cardinal_south);
        eastString = getResources().getString(R.string.cardinal_east);
        westString = getResources().getString(R.string.cardinal_west);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getResources().getColor(R.color.text_color));
        textPaint.setTextSize(20);

        textHeight = (int) textPaint.measureText("yY");

        marketPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marketPaint.setColor(getResources().getColor(R.color.marker_color));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = measure(widthMeasureSpec);
        int measureHeight = measure(heightMeasureSpec);

        int d = Math.min(measureWidth, measureHeight);
        setMeasuredDimension(d, d);
    }

    private int measure(int widthMeasureSpec) {
        int result = 0;

        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.UNSPECIFIED) {
            //如果没有指定界限，则返回默认的大小200
            result = 200;
        } else {
            //由于希望填充可用的空间，所以总是返回整个可用的边界
            result = specSize;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int measureHeight = getMeasuredHeight();
        int measureWidth = getMeasuredWidth();

        //找到控件中心，并将最小边的长度作为罗盘半径作为罗盘的半径存储起来
        int px = measureWidth / 2;
        int py = measureHeight / 2;

        int radius = Math.min(px, py);

        canvas.drawCircle(px, py, radius, circlePaint);

        //旋转视图，这样上方就面对当前的方向
        canvas.save();
        canvas.rotate(-bearing, px, py);

        //绘制罗盘标记，每15°画一个标记，每45°画一个方向的缩写
        int textWidth = (int) textPaint.measureText("W");
        int cardinalX = px - textWidth / 2;
        int cardinalY = py - radius + textHeight;

        for (int i = 0; i < 24; i++) {
            //绘制一个标记
            canvas.drawLine(px, py - radius, px, py - radius + 10, marketPaint);
            canvas.save();
            canvas.translate(0, textHeight);//翻转

            //绘制基本方向
            if (i % 6 == 0) {
                String dirString = "";
                switch (i) {
                    case (0): {
                       dirString = northString;
                        int arrowY = 2*textHeight;
                        canvas.drawLine(px,arrowY,px-5,3*textHeight,marketPaint);
                        canvas.drawLine(px,arrowY,px+5,3*textHeight,marketPaint);
                    }
                    break;
                    case (6):dirString = eastString;
                        break;
                    case (12):dirString = southString;
                        break;
                    case (18):dirString = westString;
                        break;
                }
                canvas.drawText(dirString,cardinalX,cardinalY,textPaint);
            }else if (i % 3==0) {
                //每45°绘制文本
                String angle = String.valueOf(i*15);
                float angleTextWidth = textPaint.measureText(angle);

                int angleTextX = (int) (px - angleTextWidth/2);
                int angleTextY = py - radius + textHeight;
                canvas.drawText(angle,angleTextX,angleTextY,textPaint);
            }
            canvas.restore();

            canvas.rotate(15,px,py);//旋转
        }
        canvas.restore();

    }

    //重写该方法，将当前方向用作可访问性事件使用的内容值
    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        super.dispatchPopulateAccessibilityEvent(event);
            String bearingStr = String.valueOf(bearing);        if (isShown()){

                if (bearingStr.length()>AccessibilityEvent.MAX_TEXT_LENGTH)
                bearingStr = bearingStr.substring(0,AccessibilityEvent.MAX_TEXT_LENGTH);

            event.getText().add(bearingStr);
            return true;
        }else
            return false;

    }
}

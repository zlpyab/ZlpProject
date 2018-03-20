package com.example.zlp.wight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.zlp.R;

import java.math.BigDecimal;

/**
 * 自定义弧形SeekBar ViewGroup
 * Created by Jaceli on 2016-04-28.
 */
public class ArcSeekBarParent extends FrameLayout implements SeekBarBallView.OnSmoothScrollListener {

    private PointF pointF1;                     // 起始点
    private PointF pointF2;                     // 控制点
    private PointF pointF3;                     // 终止点
    private PointF circleCenter;                // 球的坐标
    private int top;
    private int right;
    private int bottom;
    private int left;
    private float currentX;                     // 当前x坐标，用于控制圆球位置
    private final static float LEVEL = 101f;      // 设置档次
    private int currentLevel = 0;               // 当前档次

    private OnProgressChangedListener listener; // 档次改变的监听
    private Context context;

    private SeekBarBallView ball;               // 球
    private SeekBarArcView arc;                 // 弧
    private int ballSize;                       // 球的大小
    private String ballColor;                   // 球的颜色
    private int arcWidth;                       // 弧的宽度
    private String arcColor;                    // 弧的颜色
    private Paint mPaint;
    private int minMoney = 500;
    private int maxMoney = 5000;
    private int mTextSize = 30;

    public ArcSeekBarParent(Context context) {
        super(context);
        init();
    }

    public ArcSeekBarParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBarParent);
        ballSize = array.getDimensionPixelSize(R.styleable.ArcSeekBarParent_ballSize, 90);
        arcWidth = array.getDimensionPixelSize(R.styleable.ArcSeekBarParent_arcWidth, 10);
        ballColor = array.getString(R.styleable.ArcSeekBarParent_ballColor);
        arcColor = array.getString(R.styleable.ArcSeekBarParent_arcColor);
        array.recycle();
        init();
    }

    private void init() {
        pointF1 = new PointF();
        pointF2 = new PointF();
        pointF3 = new PointF();
        circleCenter = new PointF();

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setTextSize(mTextSize);

        // 添加弧线View
        if (arcColor == null) {
            arcColor = "#000000";      // 当没有设置颜色时候，默认使用黑色
        }
        arc = new SeekBarArcView(context, arcColor, arcWidth);
        arc.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(arc);

        // 添加球View
        if (ballColor == null) {
            ballColor = "#FFFFFF";      // 当没有设置颜色时候，默认使用白色
        }
        ball = new SeekBarBallView(context, ballColor, ballSize);
        ball.setListener(this);
        addView(ball);
    }

    private void drawMinMoneyText(Canvas canvas,int money) {
            float textWidth = mPaint.measureText("" + money);
            canvas.drawText("" + money, 150 - textWidth / 2, bottom/2+60 + mTextSize / 2, mPaint);
    }
    private void drawMaxMoneyText(Canvas canvas,int money) {
        float textWidth = mPaint.measureText("" + money);
        canvas.drawText("" + money, right-150 - textWidth / 2, bottom/2+60 + mTextSize / 2, mPaint);
    }
    public void setListener(OnProgressChangedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        pointF1.set(0+150, bottom/2);
        pointF2.set((right - left) / 2, bottom/2+100);
        pointF3.set(right-150, bottom /2);

        currentX = (right - left) / LEVEL;
        Log.d("YiLog","right:"+right+",left:"+left);
        changeBallLayout(right-left);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawMinMoneyText(canvas,minMoney);
        drawMaxMoneyText(canvas,maxMoney);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float downX = event.getX();
                float downY = event.getY();
                float distance = (downX - circleCenter.x) * (downX - circleCenter.x) + (downY - circleCenter.y) * (downY - circleCenter.y);
                // 计算到圆球中心的距离，考虑20的误差
                return !(distance - (ball.getMeasuredWidth() / 2 + 20) * (ball.getMeasuredWidth() / 2 + 20) > 0);
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                currentX = moveX; // 通过x坐标改变圆球的位置
                Log.d("YiLog","currentx:"+currentX);
                changeBallLayout(currentX);
                currentLevel = getLevel(moveX);
                if (listener != null) {
                    listener.OnProgressChanged(currentLevel);
                }
                break;
            default:
                // 当手指移出或者离开View时，圆球平滑滑到最近的档次
              //  ball.smoothScrollLevel((int) currentX, (int) ((right - left) / LEVEL * currentLevel - currentX));
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 改变球的位置
     *
     * @param currentX 横坐标
     */
    private void changeBallLayout(float currentX) {
        float t = (currentX / (right - left));
        float x = (1 - t) * (1 - t) * pointF1.x + 2 * (t) * (1 - t) * pointF2.x + t * t * pointF3.x;
        float y = (1 - t) * (1 - t) * pointF1.y + 2 * (t) * (1 - t) * pointF2.y + t * t * pointF3.y;
        circleCenter.set(x, y);
        ball.layout((int) (circleCenter.x - ball.getMeasuredWidth() / 2), (int) (circleCenter.y - ball.getMeasuredWidth() / 2), (int) (circleCenter.x + ball.getMeasuredWidth() / 2), (int) (circleCenter.y + ball.getMeasuredWidth() / 2));
    }

    /**
     * 计算档次
     *
     * @param x 横坐标
     * @return 档次
     */
    private int getLevel(float x) {
        float ratio = (x / (right - left)) * LEVEL;
        // 计算距离哪个档次最近
        int result = new BigDecimal(ratio).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
        if (result < 1) {
            result = 1;
        } else if (result > (LEVEL - 1)) {
            result = (int) (LEVEL - 1);
        }
        return result;
    }

    @Override
    public void onSmoothScroll(int currentX) {
        changeBallLayout(currentX);
    }

    /**
     * 滑动接口
     */
    public interface OnProgressChangedListener {
        void OnProgressChanged(int level);
    }

    public void setMinMoney(int minMoney) {
        this.minMoney = minMoney;
    }

    public void setMaxMoney(int maxMoney) {
        this.maxMoney = maxMoney;
    }
}

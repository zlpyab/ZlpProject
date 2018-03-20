package com.example.zlp.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/11/16.
 * 自定义ViewPager
 */
public class MyViewPager extends ViewGroup {

    private GestureDetector mDetector;//手势控制器
    private Scroller mScroller;//滑动器

    public MyViewPager(Context context) {
        super(context);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                scrollBy((int) distanceX, 0);//相对移动

                return super.onScroll(e1, e2, distanceX, distanceY);
            }
        });

        mScroller = new Scroller(getContext());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            getChildAt(i).measure(widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).layout(0 + i * getWidth(), 0, (i + 1) * getWidth(), getHeight());
        }
    }

    int startX;
    int startY;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mDetector.onTouchEvent(ev);
                startX = (int)ev.getX();
                startY = (int)ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int)ev.getX();
                int endY = (int)ev.getY();

                int dx = startX - endX;
                int dy = startY - endY;
                if (Math.abs(dx) > Math.abs(dy)){
                    return true;
                }
                break;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP://判断当前滑动是否超过屏幕一半,定位当前是第几个位置,跳转下一位置

                int scrollX = getScrollX();//获取当前滑动位置

                int pos = (scrollX + getWidth() / 2) / getWidth();

                if (pos > getChildCount() - 1) {
                    pos = getChildCount() - 1;
                }

                //滑到当前位置
                //scrollTo(pos * getWidth(), 0);

                //平滑到当前位置

                int distance = pos * getWidth() - getScrollX();//滑动的距离: 目标位置减去当前位置
                //参4 滑动的时间,为距离,距离越长时间越长,此方法不能马上实现效果，会不断回调computeScroll()方法，需在此方法中手动去滑动
                mScroller.startScroll(getScrollX(), 0, distance, 0, Math.abs(distance));
                invalidate();
                break;
        }
        return true;
    }

    //mScroller会不断调用此方法
    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()){//判断滑动回调有没有结束
            int curentX  = mScroller.getCurrX();
            scrollTo(curentX,0);//移动到确定位置
            invalidate();
        }

    }
}

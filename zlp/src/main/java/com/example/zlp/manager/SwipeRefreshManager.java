package com.example.zlp.manager;

import android.app.Activity;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.bumptech.glide.Glide;

/**
 * 控制SwipeRefreshLayout的上拉，下拉加载
 * Created by zlp on 2017/5/12.
 */
public class SwipeRefreshManager {

    private static final String TAG = "SwipeRefreshManager";
    /**
     * 滑动到最下面时的上拉操作
     */
    private int mTouchSlop;

    public enum MODE {
        DOWN,
        PULL,
        BOTH,
        NONE
    }

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private OnToutchListener onToutchListener;
    private OnScrollListenerOfRv onScrollListenerOfRv;
    private int mLastVisiblePosition;

    private int downY = 0;
    private int lastY = 0;

    public SwipeRefreshManager(SwipeRefreshLayout swipeRefreshLayout) {
        mTouchSlop = ViewConfiguration.get(swipeRefreshLayout.getContext()).getScaledTouchSlop();
        this.mSwipeRefreshLayout = swipeRefreshLayout;

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mSwipeRefreshLayout.setProgressViewOffset(true, 20, 150);
        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        // 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }
    public void setOnRefreshListener( MODE mode, RecyclerView view, OnRefreshListener listener){
         setOnRefreshListener(mode,view,0,listener);
    }

    /**
     * recyclerview
     *
     * @param mode
     * @param view
     * @param type     0 : linearlayout  1: GirdLayout
     * @param listener
     */
    public void setOnRefreshListener(final MODE mode, final RecyclerView view, final int type, final OnRefreshListener listener) {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if ((mode == MODE.BOTH || mode == MODE.DOWN) && listener != null) {
                    listener.onDownRefresh();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (onToutchListener != null) {
                    onToutchListener.onTotch(v, event);
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downY = (int) event.getRawY();
                        Log.d(TAG, "Toutch down donwY:" + downY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (downY == 0) {
                            downY = (int) event.getRawY();
                            Log.d(TAG, "Toutch move donwY:" + downY);
                        } else {
                            lastY = (int) event.getRawY();
                            Log.d(TAG, "Toutch move lastY:" + lastY);
                        }
                        break;
                }
                return false;
            }
        });
        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                if (onScrollListenerOfRv != null){
                    onScrollListenerOfRv.onScrollStateChanged(recyclerView,newState);
                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (view.getContext() == null || ((Activity) view.getContext()).isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) view.getContext()).isDestroyed())) {
                       Log.d(TAG,"Activity is destroy");
                    } else {
                        Glide.with(view.getContext()).resumeRequests();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                switch (type){
                    case 0:
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) view.getLayoutManager();
                        mLastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();
                        break;
                    case 1:
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) view.getLayoutManager();
                        mLastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();
                        break;
                    default:
                        mLastVisiblePosition = ((LinearLayoutManager)view.getLayoutManager()).findLastVisibleItemPosition();
                        break;
                }
                if (mLastVisiblePosition + 1 == recyclerView.getAdapter().getItemCount()) {
                    if ((mode == MODE.BOTH || mode == MODE.PULL) && listener != null && !mSwipeRefreshLayout.isRefreshing() && isPullUp()){
                        listener.onPullRefresh();
                    }
                }
                if (onScrollListenerOfRv != null) {
                    onScrollListenerOfRv.onScrolled(recyclerView, dx, dy);
                }
                if (view.getContext() == null || ((Activity) view.getContext()).isFinishing() || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && ((Activity) view.getContext()).isDestroyed())) {
                   Log.d(TAG,"activity is destroy");
                } else {
                    if (Math.abs(dy) > 30) {
                        Glide.with(view.getContext()).pauseRequests();
                    } else {
                        Glide.with(view.getContext()).resumeRequests();
                    }
                }
            }
        });

    }

    /**
     * 是否是上拉操作
     * @return
     */
    private boolean isPullUp() {
        Log.d(TAG,"pullUp downY:"+downY);
        Log.d(TAG,"pullUp lastY:"+lastY);
        Log.d(TAG,"pullUp mToutchSlop:"+mTouchSlop);
        if ((downY - lastY) >= mTouchSlop) {
            downY = 0;
            return true;
        } else {
            downY = 0;
            return false;
        }
    }

    public interface OnToutchListener {
        public boolean onTotch(View view, MotionEvent motionEvent);
    }

    public void setOnScrollListenerOfRv(OnScrollListenerOfRv onScrollListenerOfRv) {
        this.onScrollListenerOfRv = onScrollListenerOfRv;
    }

    public interface OnScrollListenerOfRv{
        public void onScrollStateChanged(RecyclerView recyclerView,int newState);
        public void onScrolled(RecyclerView recyclerView,int dx,int dy);
    }
    public interface OnRefreshListener {
        public void onDownRefresh(); //下拉刷新

        public void onPullRefresh(); //上拉加载
    }
}

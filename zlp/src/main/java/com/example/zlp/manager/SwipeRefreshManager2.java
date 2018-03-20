package com.example.zlp.manager;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zlp on 2017/8/4.
 */

public class SwipeRefreshManager2 {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int mLastVisiblePosition;


    public SwipeRefreshManager2(SwipeRefreshLayout swipeRefreshLayout) {
        mSwipeRefreshLayout = swipeRefreshLayout;

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

    public void setOnRefreshListener(RecyclerView recyclerView, final OnRefreshListener listener){

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  if (listener != null){
                      listener.onDownRefresh();
                  }else {
                      mSwipeRefreshLayout.setRefreshing(false);
                  }
            }
        });


        final LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && mLastVisiblePosition+1 == recyclerView.getAdapter().getItemCount()){
                    if (listener != null){
                        listener.onPullRefresh();
                    }
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mLastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            }
        });

    }


    private OnRefreshListener mOnRefreshListener;
    public interface OnRefreshListener{
        public void onDownRefresh(); //下拉刷新

        public void onPullRefresh(); //上拉加载
    }




}

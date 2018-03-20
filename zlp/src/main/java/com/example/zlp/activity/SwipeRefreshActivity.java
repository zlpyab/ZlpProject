package com.example.zlp.activity;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zlp.R;
import com.example.zlp.adapter.SwipeRefreshAdapter;
import com.example.zlp.manager.SwipeRefreshManager2;
import com.example.zlp.modle.ListData;
import com.example.zlp.wight.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class SwipeRefreshActivity extends Activity implements SwipeRefreshManager2.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private SwipeRefreshAdapter mAdapter;
    private RecyclerView recyclerView;

    private List<ListData> list = new ArrayList<>();
    private boolean isLoading;
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_refresh);

        initView();
    }

    private void initView() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperrefreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeRefreshManager2 manager2 = new SwipeRefreshManager2(swipeRefreshLayout);
        manager2.setOnRefreshListener(recyclerView,this);

        mAdapter = new SwipeRefreshAdapter(list,this, R.layout.item_recyclerview);
        recyclerView.setAdapter(mAdapter);

        getData();
    }

    private void getData() {
        for (int i = 1; i < 11; i++) {
            list.add(new ListData("title" + i, 0));
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onDownRefresh() {
        //模拟网络请求
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);


    }

    /**
     * 上拉加载
     */
    @Override
    public void onPullRefresh() {

        boolean isRefreshing = swipeRefreshLayout.isRefreshing();
        if (isRefreshing){
            mAdapter.notifyItemRemoved(mAdapter.getItemCount());
            return;
        }
        if (!isLoading){
            isLoading = true;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getData();
                    mAdapter.notifyDataSetChanged();
                    mAdapter.changeMoreStatus(SwipeRefreshAdapter.PULL_MORE_STATUS);
                    mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    isLoading = false;
                }
            },2000);
        }
        mAdapter.changeMoreStatus(SwipeRefreshAdapter.LOAD_MORE_STATUS);

    }
}
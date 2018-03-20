package com.example.zlp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;

import com.example.zlp.R;
import com.example.zlp.adapter.MultyItemAdapter;
import com.example.zlp.adapter.RecyclerAdapter;
import com.example.zlp.modle.ListData;
import com.example.zlp.wight.MyItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddHeaderRecyclerViewActivity extends Activity  {

    private RecyclerView recyclerView;
    private MultyItemAdapter mAdapter;

    private View header;
    private View footer;
    private List<ListData> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new MyItemDecoration());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        header = LayoutInflater.from(this).inflate(R.layout.item_header1,null);
        footer = LayoutInflater.from(this).inflate(R.layout.item_footer1,null);


        getData();
        mAdapter = new MultyItemAdapter(this,mList,R.layout.item_type1,R.layout.item_type2);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setHeaderView(header);
        mAdapter.setFooterView(footer);


    }

    private void getData(){
        mList = new ArrayList<>();
        ListData data1 = new ListData("item1_1",1);
        ListData data2 = new ListData("item2_1",2);
        ListData data3 = new ListData("item1_2",1);
        ListData data4 = new ListData("item1_3",1);

        ListData data5 = new ListData("item2_2",2);
        ListData data6 = new ListData("item2_3",2);
        ListData data7 = new ListData("item1_4",1);
        ListData data8 = new ListData("item2_4",2);
        ListData data9 = new ListData("item1_5",1);
        ListData data10 = new ListData("item2_5",2);

        mList.add(data1);
        mList.add(data2);
        mList.add(data3);
        mList.add(data4);
        mList.add(data5);
        mList.add(data6);
        mList.add(data7);
        mList.add(data8);
        mList.add(data9);
        mList.add(data10);

    }


}

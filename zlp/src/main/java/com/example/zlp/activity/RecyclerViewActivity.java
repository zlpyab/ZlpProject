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
import android.widget.LinearLayout;

import com.example.zlp.R;
import com.example.zlp.adapter.HeaderAndFooterWrapper;
import com.example.zlp.adapter.RecyclerAdapter;
import com.example.zlp.wight.DividerItemDecoration;
import com.example.zlp.wight.LqcRefreshLoadView;
import com.example.zlp.wight.MyItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecyclerViewActivity extends Activity  {

    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<String> list = new ArrayList<>();

    ItemTouchHelper itemTouchHelper;


    private View header1;
    private View footer1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
       // recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.addItemDecoration(new MyItemDecoration());
        for (int i = 0; i < 20; i++) {
            list.add("哈哈" + i);
        }
        adapter = new RecyclerAdapter(this, list, R.layout.item_recyclerview);


//        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelper());
//        itemTouchHelper.attachToRecyclerView(recyclerView);
//
//        header1 = LayoutInflater.from(this).inflate(R.layout.item_header1,null);
//        footer1 = LayoutInflater.from(this).inflate(R.layout.item_footer1,null);

        recyclerView.setAdapter(adapter);
    }



    class MyItemTouchHelper extends ItemTouchHelper.Callback {


        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags;
            int swipFlags;
            if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                dragFlags = ItemTouchHelper.DOWN |
                        ItemTouchHelper.UP |
                        ItemTouchHelper.LEFT |
                        ItemTouchHelper.RIGHT;
                swipFlags = 0;
            } else {
                dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
                swipFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            }
            return makeMovementFlags(dragFlags, swipFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            int formPostion = viewHolder.getAdapterPosition();
            int toPositon = target.getAdapterPosition();

            if (formPostion < toPositon) {
                for (int i = formPostion; i < toPositon; i++) {
                    Collections.swap(list, i, i + 1);
                }
            } else {
                for (int i = formPostion; i > toPositon; i--) {
                    Collections.swap(list, i, i - 1);
                }
            }
            adapter.notifyItemMoved(formPostion, toPositon);
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.notifyItemRemoved(position);
                list.remove(position);
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);

            }
            super.onSelectedChanged(viewHolder, actionState);

        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(0);

        }

    }

}

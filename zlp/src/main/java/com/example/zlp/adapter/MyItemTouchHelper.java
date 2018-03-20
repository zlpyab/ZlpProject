package com.example.zlp.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by zlp on 2017/2/28.
 */
public class MyItemTouchHelper extends ItemTouchHelper.Callback {


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
            swipFlags = 0;
        }
        return makeMovementFlags(dragFlags, swipFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
       int formPostion = viewHolder.getAdapterPosition();
        int toPositon = target.getAdapterPosition();

        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}

package com.example.zlp.adapter;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.zlp.R;
import com.example.zlp.wight.CircleProgressView;

import java.util.List;

/**
 * Created by zlp on 2017/5/15.
 */
public class RefreshAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {

    private int ITEM_TYPE = 0;
    private int LOAD_MORE_TYPE = 1;
    private List<T> mData;
    private int mLayoutID;
    private RecyclerView recyclerView;

    public RefreshAdapter(List<T> mData,int mLayoutID,RecyclerView recyclerView) {
        this.mData = mData;
        this.mLayoutID = mLayoutID;
        this.recyclerView = recyclerView;

        inistenerRecyclerview();
    }

    private void inistenerRecyclerview() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()){
            return LOAD_MORE_TYPE;
        }else {
            return ITEM_TYPE;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder;
        if (viewType == LOAD_MORE_TYPE){
            holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.load_more,parent,false));
        }else {
            holder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(mLayoutID,parent,false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == LOAD_MORE_TYPE){
            CircleProgressView circleProgressView = (CircleProgressView) holder.getView(R.id.circleProgressView);
            circleProgressView.startAnimation();
        }else{
            if (mLayoutID == R.layout.item_type1){

            }
        }
    }

    @Override
    public int getItemCount() {
        return mData.size()+1;
    }



}

package com.example.zlp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.zlp.R;
import com.example.zlp.modle.ListData;
import com.example.zlp.wight.CircleProgressView;

import java.util.List;

/**
 * Created by zlp on 2017/8/4.
 */

public class SwipeRefreshAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    public static final int PULL_MORE_STATUS = 0;
    public static final int LOAD_MORE_STATUS = 1;
    public static final int LOAD_COMPLETE_STATUS= 2;
    private List<ListData> mListDatas;
    private Context mContext;
    private int mLayout;
    private int changeMoreStatus;

    public SwipeRefreshAdapter(List<ListData> listDatas, Context context, int mLayout) {
        mListDatas = listDatas;
        mContext = context;
        this.mLayout = mLayout;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder;
        if (viewType == TYPE_FOOTER) {
            myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.load_more,parent,false));
        } else {
            myViewHolder = new MyViewHolder(LayoutInflater.from(mContext).inflate(mLayout,parent,false));
        }
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

         if (getItemViewType(position) == TYPE_ITEM){
             if (mLayout == R.layout.item_recyclerview){
                 ListData data = mListDatas.get(position);
                 holder.setText(R.id.tv,data.getTitile());
             }

         }else if (getItemViewType(position )== TYPE_FOOTER){
             CircleProgressView progressView = (CircleProgressView) holder.getView(R.id.circleProgressView);
             switch (changeMoreStatus){
                 case PULL_MORE_STATUS:
                     progressView.startAnimation();
                     holder.setText(R.id.tv_load_msg,"上拉加载更多...");
                     break;
                 case LOAD_MORE_STATUS:
                     progressView.startAnimation();
                     holder.setText(R.id.tv_load_msg,"正在加载更多数据...");
                     break;
                 case LOAD_COMPLETE_STATUS:
                     progressView.endAnimation();
                   //  holder.setText(R.id.tv_load_msg,"没有更多数据了...");
                     break;
             }

         }

    }


    @Override
    public int getItemCount() {
        return mListDatas.size() == 0 ? 0 : mListDatas.size() + 1;
    }


    public void changeMoreStatus(int status){
        this.changeMoreStatus = status;
        notifyDataSetChanged();
    }
}

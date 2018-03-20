package com.example.zlp.adapter;

import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zlp on 2017/4/17.
 */
public class HeaderAndFooterAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEADER = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_NORMAL = 3;
    private Context context;
    private List<String> list;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFootViews = new SparseArrayCompat<>();


    private RecyclerView.Adapter mInnerAdapter;

    public HeaderAndFooterAdater(Context context, RecyclerView.Adapter mInnerAdapter) {
        this.context = context;
        this.mInnerAdapter = mInnerAdapter;
    }

    private int getHeaderCount(){
        return mHeaderViews.size();
    }

    private int getFooterCount(){
        return mFootViews.size();
    }

    private boolean isHeader(int position){
        return position < getHeaderCount();
    }

    private boolean isFooter(int position){
        return position >= getHeaderCount()+getRealItemCount();
    }

    private int getRealItemCount() {
        return mInnerAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)){
            return mHeaderViews.keyAt(position);
        }else if (isFooter(position)){
            return mFootViews.keyAt(position - getHeaderCount() - getRealItemCount());
        }
        return mInnerAdapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mHeaderViews.get(viewType) != null){
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

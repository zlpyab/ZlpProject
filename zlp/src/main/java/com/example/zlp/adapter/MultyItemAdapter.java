package com.example.zlp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zlp.R;
import com.example.zlp.modle.ListData;

import java.util.List;

/**
 * Created by zlp on 2017/4/17.
 */
public class MultyItemAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context mContext;
    private List<ListData> mList;

    private final int TYPE_HEADER = 1;
    private final int TYPE_ITEM1 = 2;
   // private final int TYPE_ITEM2 = 3;
    private final int Type_FOOTER = 4;
    private final int Type_FOOTER_LOAD_MORE = 5;

    private View mHeaderView;
    private View mFooterView;
    private LayoutInflater mInflater;
    private int mItemLayout1;
    private int mItemLayout2;



    public MultyItemAdapter(Context mContext, List<ListData> mList,int itemLayout1,int itemLayout2) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
        mItemLayout1 = itemLayout1;
        mItemLayout2 = itemLayout2;
    }

    public void setHeaderView(View headerView){
        mHeaderView = headerView;
        notifyItemInserted(0);

    }

    public void setFooterView(View footerView){
        mFooterView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }
    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0){
            return TYPE_HEADER;
        } else if (mFooterView != null && position == getItemCount() - 1){  //普通底部
            return Type_FOOTER;
        } else if (position +1 == getItemCount()){ //加载更多底部
             return Type_FOOTER_LOAD_MORE;
        } else {
            return TYPE_ITEM1;
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder;
        if (mHeaderView != null && viewType == TYPE_HEADER){
            myViewHolder = new MyViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == Type_FOOTER){
            myViewHolder = new MyViewHolder(mFooterView);
        }else if (viewType == Type_FOOTER_LOAD_MORE){
            myViewHolder = new MyViewHolder(mInflater.inflate(R.layout.load_more,parent,false));
        } else {
            myViewHolder = new MyViewHolder(mInflater.inflate(mItemLayout1,parent,false));
        }
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
          if (getItemViewType(position) == TYPE_HEADER) return;
          if (getItemViewType(position) == Type_FOOTER) return;
          if (getItemViewType(position) == Type_FOOTER_LOAD_MORE ) return;

          if (mHeaderView != null) -- position;

          if (mItemLayout1 == R.layout.item_type1){
              ListData data = mList.get(position);
              holder.setText(R.id.tv,data.getTitile());
          }
    }

    @Override
    public int getItemCount() {
        if (mHeaderView != null && mFooterView == null){
            return mList.size()+1;
        }
        if (mHeaderView == null && mFooterView != null){
            return mList.size()+1;
        }
        if (mHeaderView != null && mFooterView != null){
            return mList.size()+2;
        }
        return mList.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_HEADER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(MyViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (mHeaderView != null && holder.getLayoutPosition() == 0) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }

        if (mHeaderView != null && holder.getLayoutPosition() == 0) {
            Object tag = mHeaderView.getTag();
            if (tag != null && tag instanceof Boolean) {
                if (mList.size() == 0) {
                    mHeaderView.post(new Runnable() {
                        @Override
                        public void run() {
                            resetLayoutParams(mHeaderView, ((View) mHeaderView.getParent()).getHeight() - 10);
                        }
                    });
                } else {
                    resetLayoutParams(mHeaderView, 0);
                }

            } else {
                resetLayoutParams(holder.itemView, -1);
            }
        }


        if (holder.getItemViewType() >= 0) {
            resetLayoutParams(holder.itemView, -1);
        }
    }


    private void resetLayoutParams(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        if (height >= 0) {
            lp.height = height;
        }
        view.setLayoutParams(lp);
    }
}

package com.example.zlp.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zlp.R;

public class RecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private static final int TYPE_HEADER = -100;
    private static final int TYPE_BODY = TYPE_HEADER + 1;
    private static final int TYPE_FOOTER = TYPE_HEADER + 2;

    private Context mContext;
    private List mList;
    private int mLayoutID;
    private LayoutInflater mInflater;
    private AdapterListener mListener;

    private View mHeaderView;
    private View mFooterView;

    public RecyclerAdapter(Context context, List list, int layoutID) {
        init(context, list, layoutID);
    }

    public RecyclerAdapter(Context context, List list, int layoutID, AdapterListener listener) {
        init(context, list, layoutID);
        mListener = listener;
    }

    private synchronized void init(Context context, List list, int layoutID) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mList = list;
        mLayoutID = layoutID;
    }


    //=========================header and footer===========================================
    public void setHeaderView(View headerView) {
        if (mHeaderView == null) {
            mHeaderView = headerView;
            notifyItemInserted(0);
        } else {
            mHeaderView = headerView;
            notifyItemChanged(0);
        }
    }

    public void setFooterView(View footerView) {
        if (mFooterView == null) {
            mFooterView = footerView;
            addData(mList.size(), null);
            // notifyItemInserted(mList.size()-1);
        }
    }
    public void setEmptyView(View emptyView) {
        if (mHeaderView == null) {
            emptyView.setTag(true);
            setHeaderView(emptyView);
        }
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

        if (mFooterView != null && holder.getLayoutPosition() == mList.size()) {
            resetLayoutParams(holder.itemView, -1);
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


    @Override
    public int getItemViewType(int position) {
        if (mHeaderView != null && position == 0) {
            return TYPE_HEADER;
        } else if (mFooterView != null && position == mList.size()) {
            return TYPE_FOOTER;
        } else if (position < mList.size() && mList.get(position) instanceof View) {
            return position;
        }
        return TYPE_BODY;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder;
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            myViewHolder = new MyViewHolder(mHeaderView);
        } else if (mFooterView != null && viewType == TYPE_FOOTER) {
            myViewHolder = new MyViewHolder(mFooterView);
        } else if (viewType >= 0) {
            myViewHolder = new MyViewHolder((View) mList.get(viewType));
        } else {
            myViewHolder = new MyViewHolder(mInflater.inflate(mLayoutID, parent, false));
        }
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (getItemViewType(position) != TYPE_BODY) return;
        if (mHeaderView != null) --position;

        if (mLayoutID == R.layout.item_recyclerview){
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.width =((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/3;
//            params.height = ((Activity) mContext).getWindowManager().getDefaultDisplay().getWidth()/3;
//            TextView textView = (TextView) holder.getView(R.id.tv);
//            textView.setLayoutParams(params);
            holder.setText(R.id.tv,mList.get(position).toString());
        }
        // 如果设置了回调，则设置点击事件
        setCallBack(holder);
        if (mListener != null) mListener.onEnd(position);
    }


    private void setCallBack(final MyViewHolder holder) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    if (mHeaderView != null) --position;
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }

    public void addData(int position, Object object) {
        mList.add(position, object);
        notifyItemInserted(position);
    }

    //==============================================================================
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    private OnLongClickListener onLongClickListener;

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    public interface OnLongClickListener {
        public void onLongClicK(int position);
    }
}
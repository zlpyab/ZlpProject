package com.example.zlp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zlp on 2017/5/15.
 * 万能adapter
 */
public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH> {


    private List<T> mDatas;

    public QuickAdapter(List<T> mDatas) {
        this.mDatas = mDatas;
    }

    public abstract int getLayoutID(int viewType);

    public abstract void conver(VH holder, T data, int position);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return VH.getVH(parent, getLayoutID(viewType));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        conver(holder, (T) mDatas, position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;
        private View mConvertView;

        public VH(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
            mConvertView = itemView;
        }

        public static VH getVH(ViewGroup parent, int layoutID) {
            View convertview = LayoutInflater.from(parent.getContext()).inflate(layoutID, parent, false);
            return new VH(convertview);
        }

        public <T extends View> T getView(int id) {
            View view = mViews.get(id);
            if (view == null) {
                view = mConvertView.findViewById(id);
                mViews.put(id, view);
            }
            return (T) view;
        }

        public void setText(int id, String text) {
            TextView tv = getView(id);
            tv.setText(text);
        }
    }
}

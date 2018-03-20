package com.example.zlp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public MyViewHolder(View view) {
        super(view);
    }

    public View getView(int id){
        return this.itemView.findViewById(id);
    }

    public void setText(int id,String text){
        View view=getView(id);
        if(view instanceof TextView){
            ((TextView)view).setText(text);
        }
    }

    public void setText(int id,Spanned spanned){
        View view=getView(id);
        if(view instanceof TextView){
            ((TextView)view).setText(spanned);
        }
    }

}

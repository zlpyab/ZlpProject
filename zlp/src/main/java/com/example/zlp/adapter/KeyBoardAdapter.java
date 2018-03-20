package com.example.zlp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zlp.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by zlp on 2017/7/6.
 */

public class KeyBoardAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Map<String,String>> mList;

    public KeyBoardAdapter(Context context, ArrayList<Map<String, String>> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        KeyBoarViewHolder holder = null;
        if (convertView == null){
            holder = new KeyBoarViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.grid_item_virtual_keyboard,null);
            holder.tvKey = (TextView) convertView.findViewById(R.id.btn_keys);
            holder.mDelete = (RelativeLayout) convertView.findViewById(R.id.imgDelete);

            convertView.setTag(holder);
        }else {
            holder = (KeyBoarViewHolder) convertView.getTag();
        }

        if (i == 9){
            holder.mDelete.setVisibility(View.INVISIBLE);
            holder.tvKey.setVisibility(View.VISIBLE);
            holder.tvKey.setText(mList.get(i).get("name"));
            holder.tvKey.setBackgroundColor(Color.parseColor("#e0e0e0"));
        }else if (i == 11){
            holder.mDelete.setVisibility(View.VISIBLE);
            holder.tvKey.setVisibility(View.INVISIBLE);
            holder.tvKey.setBackgroundResource(R.mipmap.keyboard_delete_img);
        }else {
            holder.mDelete.setVisibility(View.INVISIBLE);
            holder.tvKey.setVisibility(View.VISIBLE);
            holder.tvKey.setText(mList.get(i).get("name"));
        }
        return convertView;
    }

   class KeyBoarViewHolder{
       TextView tvKey;
       RelativeLayout mDelete;
   }
}

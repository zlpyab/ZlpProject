package com.example.zlp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zlp.R;
import com.example.zlp.modle.PhoneNumber;

import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class PhoneAdater extends BaseAdapter {

    private Context context;
    private List<PhoneNumber> list;

    public PhoneAdater(Context context, List<PhoneNumber> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.phone_num_item,null);
            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvPhone = (TextView) convertView.findViewById(R.id.tv_phone);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(list.get(position).getPhontName());
        holder.tvPhone.setText(list.get(position).getPhoneNum());
        return convertView;
    }

    class ViewHolder{
        TextView tvName;
        TextView tvPhone;
    }
}

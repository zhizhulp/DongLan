package com.cn.danceland.myapplication.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Ranking;

import java.util.List;

public class RkTypeAdapter extends BaseAdapter {
    private List<Ranking> rkTypes;
    private Context context;

    public RkTypeAdapter(List<Ranking> rkTypes, Context context) {
        this.rkTypes = rkTypes;
        this.context = context;
    }


    @Override
    public int getCount() {
        return rkTypes == null ? 0 : rkTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return rkTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        View inflate = LayoutInflater.from(context).inflate(R.layout.shop_pop_item, null);
        TextView tv_item = inflate.findViewById(R.id.tv_item);
        tv_item.setText(rkTypes.get(position).getRankName());
        return inflate;
    }
}

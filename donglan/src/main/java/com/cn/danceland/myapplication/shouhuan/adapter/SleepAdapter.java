package com.cn.danceland.myapplication.shouhuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.HeartRate;
import com.cn.danceland.myapplication.shouhuan.bean.DeviceBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手环睡眠
 * Created by yxx on 2018/7/19.
 */
public class SleepAdapter extends BaseAdapter {
    private List<SleepBean> sleepBeans;
    private Context context;
    private DeviceBean deviceBean;

    public SleepAdapter(Context context, List<SleepBean> sleepBeans) {
        super();
        this.sleepBeans = sleepBeans;
        this.context = context;
    }

    public void setData(ArrayList<SleepBean> sleepBeans) {
        this.sleepBeans = sleepBeans;
    }

    public void clear() {
        sleepBeans.clear();//先清除这个
    }

//    public void sort() {
//        Log.d("zgy", "sort: ");
//
//        for (int i = 0; i < heartrateBeens.size(); i++) {
//            for (int j = i + 1; j < heartrateBeens.size(); j++) {
//                if (heartrateBeens.get(i).getRssi() < heartrateBeens.get(j).getRssi()) {
//                    heartrateBeens.set(i, heartrateBeens.get(j));
//                    heartrateBeens.set(j, deviceBean);
//                }
//            }
//        }
//
//    }

    @Override
    public int getCount() {
        return sleepBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return sleepBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listitem_wearfit_sleep, null);
            viewHolder = new ViewHolder();
            viewHolder.period_time_tv = (TextView) view.findViewById(R.id.period_time_tv);
            viewHolder.time_tv = (TextView) view.findViewById(R.id.time_tv);
            viewHolder.sleep_state_tv = (TextView) view.findViewById(R.id.sleep_state_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.period_time_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(i).getStartTime())).toString()
                + context.getResources().getString(R.string.waves_text)
                + new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(i).getEndTime())).toString());
        viewHolder.time_tv.setText(sleepBeans.get(i).getContinuoustime() + "");
        switch (sleepBeans.get(i).getState()) {
            case -1:
                viewHolder.sleep_state_tv.setText(context.getResources().getString(R.string.awake_text));
                viewHolder.sleep_state_tv.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                break;
            case 1:
                viewHolder.sleep_state_tv.setText(context.getResources().getString(R.string.shallow_sleep_text));
                viewHolder.sleep_state_tv.setTextColor(context.getResources().getColor(R.color.shallow_sleep_bg));
                break;
            case 2:
                viewHolder.sleep_state_tv.setText(context.getResources().getString(R.string.deep_sleep_text));
                viewHolder.sleep_state_tv.setTextColor(context.getResources().getColor(R.color.deep_sleep_bg));
                break;
        }
        return view;
    }

    static class ViewHolder {
        TextView period_time_tv;
        TextView time_tv;
        TextView sleep_state_tv;
    }
}

package com.cn.danceland.myapplication.shouhuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.shouhuan.bean.StepBean;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 手环睡眠
 * Created by yxx on 2018/7/19.
 */
public class StepAdapter extends BaseAdapter {
    private List<StepBean> stepBeans;
    private Context context;
    private int stepLength;

    public StepAdapter() {
        super();
    }

    public StepAdapter(Context context, List<StepBean> stepBeans, int stepLength) {
        super();
        this.stepBeans = stepBeans;
        this.context = context;
        this.stepLength = stepLength;
    }

    public void setData(ArrayList<StepBean> stepBeans) {
        this.stepBeans = stepBeans;
    }

    public void clear() {
        stepBeans.clear();//先清除这个
    }

    @Override
    public int getCount() {
        return stepBeans.size();
    }

    @Override
    public Object getItem(int i) {
        return stepBeans.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listitem_wearfit_step, null);
            viewHolder = new ViewHolder();
            viewHolder.step_tv = (TextView) view.findViewById(R.id.step_tv);
            viewHolder.kcal_tv = (TextView) view.findViewById(R.id.kcal_tv);
            viewHolder.km_english_tv = (TextView) view.findViewById(R.id.km_english_tv);
            viewHolder.time_tv = (TextView) view.findViewById(R.id.time_tv);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        float kmf = (float) stepLength * (float)stepBeans.get(i).getStep()/ (float) 100000.00;//100步长  1000km
        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
        String km=fnum.format(kmf );
        viewHolder.time_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(stepBeans.get(i).getStartTime())).toString()
                + context.getResources().getString(R.string.waves_text)
                + new SimpleDateFormat("HH:mm").format(new Date(stepBeans.get(i).getEndTime())).toString());
        viewHolder.step_tv.setText((stepBeans.get(i).getStep()) + "");
        viewHolder.kcal_tv.setText(stepBeans.get(i).getCal() + "");
        viewHolder.km_english_tv.setText(km);
        return view;
    }

    static class ViewHolder {
        TextView step_tv;//步
        TextView kcal_tv;//KCAL
        TextView km_english_tv;//KM
        TextView time_tv;//时间
    }
}

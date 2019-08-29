package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 闹钟-自定义
 * Created by ${yxx} on 2018/8/14.
 */
public class WearFitClockWeekdayActivity extends Activity {
    private Context context;
    private TextView title;
    private ListView listView;
    private ImageView donglan_back;
    private WeekAdapter weekAdapter;
    private List<String> weekdayList;
    private String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    private String lastSelete = "";
    private List<String> lastList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclock);
        context = this;
        initView();

    }

    private void initData() {
        lastSelete = getIntent().getStringExtra("weekdays");
        if (lastSelete != null && lastSelete.length() != 0) {
            String[] weekdaysA = lastSelete.split("&");
            if (weekdaysA != null)
                for (int i = 0; i < weekdaysA.length; i++) {
                    int week = Integer.valueOf(weekdaysA[i].toString());
                    lastList.add(week + "");
                }
        }
        weekAdapter.notifyDataSetChanged();
    }

    private void initView() {
        title = findViewById(R.id.donglan_title);
        title.setText("自定义");
        donglan_back = findViewById(R.id.donglan_back);
        listView = findViewById(R.id.lv_clock);
        weekdayList = new ArrayList<>();
        weekAdapter = new WeekAdapter();
        listView.setAdapter(weekAdapter);
        donglan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("");

                for (int i = 0; i < weekdayList.size(); i++) {
                    stringBuffer.append(weekdayList.get(i) + "&");
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
                LogUtil.i("选中了" + stringBuffer.toString());
                Intent intent = new Intent();
                intent.putExtra("weekdays", stringBuffer.toString());
                setResult(WearFitClockSettingActivity.MSG_CLOCK_WEEKDAY_DATA, intent);
                finish();
            }
        });
        initData();
    }

    private class WeekAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return weekDays.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View inflate = LayoutInflater.from(context).inflate(R.layout.item_clock_week, null);
            TextView tv_time = inflate.findViewById(R.id.tv_time);
            tv_time.setText(weekDays[i]);
            CheckBox btn_check = inflate.findViewById(R.id.btn_check);
            btn_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if (b) {
                        weekdayList.add(i + "");
                    } else {
                        weekdayList.remove(i + "");
                    }
                }
            });
//            if (lastSelete != null && lastSelete.length() > 0) {
//                for (int y = 0; y < lastList.size(); y++) {
//                    LogUtil.i("weekdayList.get(y)"+lastList.get(y));
//                    if (lastList.get(y).equals(i + "")) {
//                        btn_check.setChecked(true);
//                    }
//                }
//            }
            return inflate;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        Intent intent = new Intent();
        intent.putExtra("weekdays", stringBuffer.toString());
        setResult(WearFitClockSettingActivity.MSG_CLOCK_WEEKDAY_DATA, intent);
        finish();
        return super.onKeyDown(keyCode, event);
    }
}

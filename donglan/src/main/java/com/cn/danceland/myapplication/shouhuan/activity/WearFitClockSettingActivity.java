package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.ClockBean;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by feng on 2018/6/27.
 */
public class WearFitClockSettingActivity extends Activity implements View.OnClickListener {
    public static final int MSG_CLOCK_WEEKDAY_DATA = 0;
    private Context context;
    private LoopView lp_hour, lp_minute;
    private String shour = "00", sminute = "00";
    private ArrayList<String> hourList;
    private ArrayList<String> minuteList;
    private Button btn_save;
    private LinearLayout ll_yici;
    private LinearLayout ll_meitian;
    private LinearLayout ll_gongzuori;
    private LinearLayout ll_zidingyi;
    private CheckBox btn_yici;
    private CheckBox btn_meitian;
    private CheckBox btn_gongzuori;
    private CheckBox btn_zidingyi;
    private CommandManager commandManager;
    private int clock_id = -1;
    private String hour;
    private String minute;
    private String repeatStr = "只提醒一次";//重复 只提醒一次  每天 周一至周五  自定义

    StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clocksetting);
        context = this;
        gson = new Gson();
        commandManager = CommandManager.getInstance(getApplicationContext());
        hour = getIntent().getStringExtra("hour");
        minute = getIntent().getStringExtra("minute");
        clock_id = getIntent().getIntExtra("clock_id", -1);
        LogUtil.i("hour" + hour);
        LogUtil.i("minute" + minute);
        initView();
    }

    private void initView() {
        btn_save = findViewById(R.id.btn_save);
        lp_hour = findViewById(R.id.lp_hour);
        lp_minute = findViewById(R.id.lp_minute);
        ll_yici = findViewById(R.id.ll_yici);
        ll_meitian = findViewById(R.id.ll_meitian);
        ll_gongzuori = findViewById(R.id.ll_gongzuori);
        ll_zidingyi = findViewById(R.id.ll_zidingyi);
        btn_yici = findViewById(R.id.btn_yici);
        btn_meitian = findViewById(R.id.btn_meitian);
        btn_gongzuori = findViewById(R.id.btn_gongzuori);
        btn_zidingyi = findViewById(R.id.btn_zidingyi);
        getHistory();
        initLoopData();
        setClickListener();
        LogUtil.i("clock_id" + clock_id);
        if (clock_id != (-1)) {//-1添加   否则修改
            initRepeatView();
        }
    }

    private void initRepeatView() {
        String clockList = SPUtils.getString("ClockList", "");
        List<ClockBean> lastList = new ArrayList<>();
        if (!StringUtils.isNullorEmpty(clockList)) {
            Type listType = new TypeToken<List<ClockBean>>() {
            }.getType();
            List<ClockBean> clockBeans = gson.fromJson(clockList, listType);
            lastList.addAll(clockBeans);
        }
        weekdayList = lastList.get(clock_id).getWeekday();
        stringBuffer.append("");

        for (int i = 0; i < weekdayList.size(); i++) {
            stringBuffer.append(weekdayList.get(i) + "&");
        }
        if (stringBuffer.length() > 0) {
            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        repeatStr = localClockList.get(clock_id).getRepeat();
        switch (repeatStr) {
            case "只提醒一次":
                btn_yici.setChecked(true);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(false);
                break;
            case "每天":
                btn_yici.setChecked(false);
                btn_meitian.setChecked(true);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(false);
                break;
            case "周一至周五":
                btn_yici.setChecked(false);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(true);
                btn_zidingyi.setChecked(false);
                break;
            case "自定义":
                btn_yici.setChecked(false);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(true);
                repeatStr = "自定义";
                break;
        }
    }

    private void setClickListener() {
        btn_save.setOnClickListener(this);
        ll_yici.setOnClickListener(this);
        ll_meitian.setOnClickListener(this);
        ll_gongzuori.setOnClickListener(this);
        ll_zidingyi.setOnClickListener(this);

        btn_yici.setChecked(true);
        btn_meitian.setChecked(false);
        btn_gongzuori.setChecked(false);
        btn_zidingyi.setChecked(false);
        weekdayList.clear();
        Calendar calendar = Calendar.getInstance();
        weekdayList.add(calendar.get(Calendar.DAY_OF_WEEK) - 1);
    }

    private void initLoopData() {
        hourList = new ArrayList<>();
        minuteList = new ArrayList<>();
        for (int x = 0; x < 24; x++) {
            if (x < 10) {
                hourList.add("0" + x);
            } else {
                hourList.add(x + "");
            }
        }
        for (int y = 0; y < 60; y++) {
            if (y < 10) {
                minuteList.add("0" + y);
            } else {
                minuteList.add(y + "");
            }
        }

        lp_hour.setItems(hourList);
        lp_minute.setItems(minuteList);

        lp_hour.setTextSize(18);
        lp_minute.setTextSize(18);
        lp_hour.setItemsVisibleCount(7);
        lp_minute.setItemsVisibleCount(7);
        for (int i = 0; i < hourList.size(); i++) {
            if (hour != null && hourList.get(i).equals(hour)) {
                lp_hour.setInitPosition(i);
                shour = hour;

            }
        }
        for (int i = 0; i < minuteList.size(); i++) {
            if (minute != null && minuteList.get(i).equals(minute + "")) {
                lp_minute.setInitPosition(i);
                sminute = minute + "";
            }
        }

        lp_hour.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                shour = hourList.get(index);
            }
        });
        lp_minute.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sminute = minuteList.get(index);
            }
        });

    }

    private Gson gson;
    private List<ClockBean> localClockList = new ArrayList<ClockBean>();
    private List<Integer> weekdayList = new ArrayList<>();

    /**
     * 获取本地闹钟
     */
    private void getHistory() {
        localClockList.clear();
        String clockList = SPUtils.getString("ClockList", "");
        if (!StringUtils.isNullorEmpty(clockList)) {
            Type listType = new TypeToken<List<ClockBean>>() {
            }.getType();
            List<ClockBean> clockBeans = gson.fromJson(clockList, listType);
            localClockList.addAll(clockBeans);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                if (localClockList.size() >= 8) {
                    Toast.makeText(context, "闹钟设置最多不超过八个", Toast.LENGTH_SHORT).show();
                } else {

                    LogUtil.i("本地闹钟有：" + localClockList.size() + "个");
                    ClockBean clockBean = new ClockBean();
                    clockBean.setHour(Integer.valueOf(shour));//小时
                    clockBean.setMinute(Integer.valueOf(sminute));//分钟
                    clockBean.setOffOn(1);//0关1开
                    clockBean.setTime(shour + ":" + sminute);
                    clockBean.setRepeat(repeatStr);
                    clockBean.setWeekday(weekdayList);//周几 0 1 2 3 4 5 6
                    if (clock_id != (-1)) {//-1添加   否则修改
                        clockBean.setId(clock_id);//时钟ID 最多8个
                        localClockList.set(clock_id, clockBean);
                    } else {
                        clockBean.setId(localClockList.size() - 1);//时钟ID 最多8个
                        localClockList.add(clockBean);
                    }
                    SPUtils.setString("ClockList", gson.toJson(localClockList));
                    for (int i = 0; i < localClockList.size(); i++) {
                        for (int j = 0; j < weekdayList.size(); j++) {
                            LogUtil.i("提交clockBean--------" + localClockList.get(i).toString());
                            LogUtil.i("提交weekdayList.get(j)--------" + weekdayList.get(j));
                            commandManager.setAlarmClock(localClockList.get(i), weekdayList.get(j));
                        }
                    }
                    finish();
                }
                break;
            case R.id.ll_yici:
                btn_yici.setChecked(true);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(false);
                weekdayList.clear();
                Calendar calendar = Calendar.getInstance();
                weekdayList.add(calendar.get(Calendar.DAY_OF_WEEK) - 1);
                repeatStr = "只提醒一次";
                break;
            case R.id.ll_meitian:
                btn_yici.setChecked(false);
                btn_meitian.setChecked(true);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(false);
                weekdayList.clear();
                weekdayList.add(0);
                weekdayList.add(1);
                weekdayList.add(2);
                weekdayList.add(3);
                weekdayList.add(4);
                weekdayList.add(5);
                weekdayList.add(6);
                repeatStr = "每天";
                break;
            case R.id.ll_gongzuori:
                btn_yici.setChecked(false);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(true);
                btn_zidingyi.setChecked(false);
                weekdayList.clear();//周日-周六 0 1 2 3 4 5 6
                weekdayList.add(1);
                weekdayList.add(2);
                weekdayList.add(3);
                weekdayList.add(4);
                weekdayList.add(5);
                repeatStr = "周一至周五";
                break;
            case R.id.ll_zidingyi:
                btn_yici.setChecked(false);
                btn_meitian.setChecked(false);
                btn_gongzuori.setChecked(false);
                btn_zidingyi.setChecked(true);
                Intent intent = new Intent(context, WearFitClockWeekdayActivity.class);
                intent.putExtra("weekdays", stringBuffer.toString());
                startActivityForResult(intent, MSG_CLOCK_WEEKDAY_DATA);
                weekdayList.clear();
                repeatStr = "自定义";
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MSG_CLOCK_WEEKDAY_DATA) {
            String weekdays = data.getStringExtra("weekdays");
            if (weekdays != null && weekdays.length() != 0) {
                String[] weekdaysA = weekdays.split("&");
                if (weekdaysA != null)
                    weekdayList.clear();
                for (int i = 0; i < weekdaysA.length; i++) {
                    int week = Integer.valueOf(weekdaysA[i].toString());
                    weekdayList.add(week);
                }
            }
        }
    }
}

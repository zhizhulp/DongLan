package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.ClockBean;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feng on 2018/6/27.
 */
public class WearFitAddClockActivity extends Activity {

    private TextView addclock_title;
    private TextView rightTv;
    private PullToRefreshListView lv_clock;
    private List<ClockBean> localClockList;
    private Gson gson;
    private ClockAdapter clockAdapter;
    private ImageView donglan_back;
    private CommandManager commandManager;

    private TextView tv_error;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclock);
        commandManager = CommandManager.getInstance(getApplicationContext());
        gson = new Gson();
        initView();
    }

    private void initView() {
        addclock_title = findViewById(R.id.donglan_title);
        addclock_title.setText("闹钟提醒");
        rightTv = findViewById(R.id.donglan_right_tv);
        donglan_back = findViewById(R.id.donglan_back);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("添加");
        lv_clock = findViewById(R.id.lv_clock);

        View listEmptyView = findViewById(R.id.rl_error);
        tv_error = listEmptyView.findViewById(R.id.tv_error);
        imageView = listEmptyView.findViewById(R.id.iv_error);
        imageView.setImageResource(R.drawable.img_error);
        tv_error.setText("没有数据");
        lv_clock.getRefreshableView().setEmptyView(listEmptyView);
        lv_clock.getRefreshableView().setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉下拉阴影
        //设置下拉刷新模式both是支持下拉和上拉
        lv_clock.setMode(PullToRefreshBase.Mode.DISABLED);//DISABLED和普通listView一样用

        localClockList = new ArrayList<ClockBean>();
        clockAdapter = new ClockAdapter();
        lv_clock.setAdapter(clockAdapter);
        localClockList.clear();
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (localClockList.size() >= 8) {
                    Toast.makeText(WearFitAddClockActivity.this, "闹钟设置最多不超过八个", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(WearFitAddClockActivity.this, WearFitClockSettingActivity.class);
                    intent.putExtra("hour", "00");
                    intent.putExtra("minute", "00");
                    startActivity(intent);
//                    startActivity(new Intent(WearFitAddClockActivity.this, WearFitClockSettingActivity.class));
//                startActivityForResult(new Intent(WearFitAddClockActivity.this,WearFitClockSettingActivity.class),3);
                }
            }
        });
        donglan_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistory();
        clockAdapter.notifyDataSetChanged();
    }

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

    private void getData() {
        getHistory();
        clockAdapter.notifyDataSetChanged();
        for (int i = 0; i < localClockList.size(); i++) {
            ClockBean clockBean = localClockList.get(i);
            List<Integer> weekdayList = clockBean.getWeekday();//周几 0 1 2 3 4 5 6
            if (weekdayList != null) {
                for (int j = 0; j < weekdayList.size(); j++) {
                    LogUtil.i("提交clockBean--------" + localClockList.get(i).toString());
                    LogUtil.i("提交weekdayList.get(j)--------" + weekdayList.get(j));
                    commandManager.setAlarmClock(localClockList.get(i), weekdayList.get(j));
                }
            }
        }
    }

    private class ClockAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return localClockList.size();
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

            View inflate = LayoutInflater.from(WearFitAddClockActivity.this).inflate(R.layout.item_clock, null);
            TextView tv_time = inflate.findViewById(R.id.tv_time);
            Switch btn_switch = inflate.findViewById(R.id.btn_switch);//闹钟id开关，最多开8个
            RelativeLayout item_layout = inflate.findViewById(R.id.item_layout);//item 后续用来点击,因为setOnItemClickListener 返回的游标有问题！
            String repeat = "";
            if (localClockList.get(i).getRepeat() != null) {
                repeat = "（" + localClockList.get(i).getRepeat() + "）";
            }
            tv_time.setText(localClockList.get(i).getTime() + repeat);
            if (localClockList.get(i).getOffOn() == 1) {//0关1开
                btn_switch.setChecked(true);
            } else {
                btn_switch.setChecked(false);
            }
            btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    ClockBean clockBean = localClockList.get(i);
                    if (b) {
                        clockBean.setOffOn(1);//0关1开
                    } else {
                        clockBean.setOffOn(0);//0关1开
                    }
                    localClockList.set(i, clockBean);
                    SPUtils.setString("ClockList", gson.toJson(localClockList));
                    getData();
                }
            });
            item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WearFitAddClockActivity.this, WearFitClockSettingActivity.class);
//                    LogUtil.i("iii+"+i+"--size--"+localClockList.size());
                    if (localClockList.get(i).getHour() == 0) {
                        intent.putExtra("hour", "00");
                    } else {
                        String hour = localClockList.get(i).getHour() + "";
                        if (hour.length() == 1) {
                            intent.putExtra("hour", "0" + localClockList.get(i).getHour() + "");
                        } else {
                            intent.putExtra("hour", localClockList.get(i).getHour() + "");
                        }
                    }

                    if (localClockList.get(i).getMinute() == 0) {
                        intent.putExtra("minute", "00");
                    } else {
                        String minute = localClockList.get(i).getMinute() + "";
                        if (minute.length() == 1) {
                            intent.putExtra("minute", "0" + localClockList.get(i).getMinute() + "");
                        } else {
                            intent.putExtra("minute", localClockList.get(i).getMinute() + "");
                        }
                    }
                    intent.putExtra("clock_id", i);
                    startActivity(intent);
                }
            });

            return inflate;
        }
    }

    public static boolean isListEqual(List list1, List list2) {
        if (list1 == list2)
            return true;
        if (list1 == null && list2 == null)
            return true;
        if (list1 == null || list2 == null)
            return false;
        if (list1.size() != list2.size())
            return false;
        for (Object o : list1) {
            if (!list2.contains(o))
                return false;
        }
        for (Object o : list2) {
            if (!list1.contains(o))
                return false;
        }
        return true;
    }
}

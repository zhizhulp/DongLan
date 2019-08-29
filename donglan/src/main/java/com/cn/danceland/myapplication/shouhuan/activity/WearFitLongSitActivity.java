package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.shouhuan.bean.LongSit;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.shouhuan.utils.DataHandlerUtils;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by feng on 2018/6/22.
 */
public class WearFitLongSitActivity extends Activity {

    private DongLanTitleView longsit_title;
    private TextView rightTv;
    private TextView tv_start;
    private TextView tv_end;
    private TextView tv_tixing;
    private Switch sw_jiuzuo;

    private Gson gson;
    private LongSit longSit;//久坐

    private String from;
    private CommandManager commandManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfitlongsit);
        from = getIntent().getStringExtra("from");
        commandManager = CommandManager.getInstance(getApplicationContext());
        gson = new Gson();
        longSit = new LongSit();
        initView();
    }

    private void initView() {
        longsit_title = findViewById(R.id.longsit_title);
        tv_tixing = findViewById(R.id.tv_tixing);
        rightTv = findViewById(R.id.donglan_right_tv);
        sw_jiuzuo = findViewById(R.id.sw_jiuzuo);
        tv_start = findViewById(R.id.tv_start);
        tv_end = findViewById(R.id.tv_end);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText("保存");
        if ("久坐提醒".equals(from)) {
            longsit_title.setTitle("久坐提醒");
            tv_tixing.setText("久坐提醒");
        } else if ("勿扰模式".equals(from)) {
            longsit_title.setTitle("勿扰模式");
            tv_tixing.setText("勿扰模式");
        }

        setClick();
    }


    private void getHistory() {
        if ("久坐提醒".equals(from)) {
            String longSitGson = SPUtils.getString("LongSit", "");
            if (!StringUtils.isNullorEmpty(longSitGson)) {
                Type listType = new TypeToken<LongSit>() {
                }.getType();
                longSit = gson.fromJson(longSitGson, listType);
                if(longSit.getOn()==1){//开
                    sw_jiuzuo.setChecked(true);
                }else{
                    sw_jiuzuo.setChecked(false);
                }
            }
        } else if ("勿扰模式".equals(from)) {
            String longSitGson = SPUtils.getString("IgnoreLongSit", "");
            if (!StringUtils.isNullorEmpty(longSitGson)) {
                Type listType = new TypeToken<LongSit>() {
                }.getType();
                longSit = gson.fromJson(longSitGson, listType);
                if(longSit.getOn()==1){//开
                    sw_jiuzuo.setChecked(true);
                }else{
                    sw_jiuzuo.setChecked(false);
                }
            }
        }
    }

    private void setClick() {
        tv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeSelect("开始时间");
            }
        });
        tv_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeSelect("结束时间");
            }
        });
        sw_jiuzuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    longSit.setOn(1);
                } else {
                    longSit.setOn(0);
                }
            }
        });
        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.showToastShort("保存成功");
                if ("久坐提醒".equals(from)) {
                    SPUtils.setString("LongSit", gson.toJson(longSit));
                    commandManager.sendLongSit(longSit);
                } else if ("勿扰模式".equals(from)) {
                    SPUtils.setString("IgnoreLongSit", gson.toJson(longSit));
                    commandManager.sendIgnoreAction(longSit);
                }

            }
        });
    }

    private void showTimeSelect(final String str) {
        int hourStrTemp = 0;
        int minuteStrTemp = 0;
        String[] times = tv_start.getText().toString().split("\\:");
        if ("结束时间".equals(str)) {
            times = tv_end.getText().toString().split("\\:");
        }
        if (times != null && times.length > 1) {
            hourStrTemp = Integer.valueOf(times[0]);
            minuteStrTemp = Integer.valueOf(times[1]);
        }
        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, str);
        customDateAndTimePicker.setGoneYearAndMounth();
        customDateAndTimePicker.showWindow(hourStrTemp,minuteStrTemp);
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                String hourStr = customDateAndTimePicker.getHour();
                String minuteStr = customDateAndTimePicker.getMinute();
                if ("开始时间".equals(str)) {
                    tv_start.setText(hourStr + ":" + minuteStr);
                    longSit.setStartHour(Integer.valueOf(hourStr));
                    longSit.setStartMinute(Integer.valueOf(minuteStr));
                } else if ("结束时间".equals(str)) {
                    tv_end.setText(hourStr + ":" + minuteStr);
                    longSit.setStartHour(Integer.valueOf(hourStr));
                    longSit.setStartMinute(Integer.valueOf(minuteStr));
                }
            }
        });

    }

    //接收蓝牙状态改变的广播
    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
//                LogUtil.i("接收的数据：" + DataHandlerUtils.bytesToHexStr(txValue));

                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);
                if (datas.get(4) == 0x51 && datas.get(5) == 17) {
//                    LogUtil.i(datas.toString());
                }
                if (datas.get(4) == 0x51 && datas.get(5) == 8) {
//                    LogUtil.i(datas.toString());
                }
                if (datas.get(4) == 0x75) {
                    LogUtil.i(datas.toString());
                }
            }
        }
    };

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistory();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }
}

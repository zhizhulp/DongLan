package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.shouhuan.bean.LongSit;
import com.cn.danceland.myapplication.shouhuan.bean.WearFitUser;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.shouhuan.utils.DataHandlerUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.BatteryView;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.PickerViewDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by feng on 2018/6/21.
 */
public class WearFitSettingActivity extends Activity {
    private Context context;
    private DongLanTitleView wearfit_setting_title;
    private TextView tv_shouhuan_name;
    private TextView tv_shouhuan_num;
    private TextView tv_status;
    private TextView tv_power;
    private BatteryView battery_view;
    private Button btn_jiebang;
    private RelativeLayout rl_buchang;
    private RelativeLayout rl_peidai;
    private Switch sw_taishou;
    private Switch sw_fangdiu;
    private CheckBox btn_juli_left;
    private CheckBox btn_juli_right;
    private CheckBox btn_buchang_left;
    private CheckBox btn_buchang_right;
    private RelativeLayout rl_rushui;
    private RelativeLayout rl_wake;
    private Switch sw_laidian;
    private Switch sw_duanxin;
    private RelativeLayout rl_naozhong;
    private RelativeLayout rl_jiuzuo;
    private RelativeLayout rl_wurao;
    private RelativeLayout rl_app;
    private TextView tv_buchang;
    private TextView tv_peidai;
    private TextView tv_rushui;
    private LinearLayout clear_phone_layout;//清除手机数据
    private LinearLayout clear_wearfit_layout;//清除手环数据
    private LinearLayout restore_layout;//恢复出厂设置
    private LinearLayout firmware_update_layout;//固件升级

    private TextView tv_wake;
    private String address;//手环地址
    private String name;
    private CommandManager commandManager;
    private Data infoData;
    private WearFitUser wearFitUser = new WearFitUser();

    private AlertDialog.Builder builder;
    private AlertDialog dia;

    private Gson gson;
    private LongSit sleepTime;//睡眠时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfitsetting);
        context = this;
        //注册广播
        LocalBroadcastManager.getInstance(this).registerReceiver(
                mGattUpdateReceiver, makeGattUpdateIntentFilter());
        infoData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);//动岚个人资料
        if (DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING) != null) {
            wearFitUser = (WearFitUser) DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING);//手环设置
        } else {
            wearFitUser = new WearFitUser();
        }
        initHost();
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mGattUpdateReceiver);
    }

    private void initHost() {
        address = SPUtils.getString(Constants.ADDRESS, "");
        name = SPUtils.getString(Constants.NAME, "");
        commandManager = CommandManager.getInstance(getApplicationContext());
    }

    private void initView() {
        wearfit_setting_title = findViewById(R.id.wearfit_setting_title);
        wearfit_setting_title.setTitle("设置");
        tv_shouhuan_name = findViewById(R.id.tv_shouhuan_name);
        tv_shouhuan_num = findViewById(R.id.tv_shouhuan_num);
        tv_status = findViewById(R.id.tv_status);
        tv_power = findViewById(R.id.tv_power);
        battery_view = findViewById(R.id.battery_view);
        btn_jiebang = findViewById(R.id.btn_jiebang);
        rl_buchang = findViewById(R.id.rl_buchang);
        rl_peidai = findViewById(R.id.rl_peidai);
        sw_taishou = findViewById(R.id.sw_taishou);
        sw_fangdiu = findViewById(R.id.sw_fangdiu);
        btn_juli_left = findViewById(R.id.btn_juli_left);
        btn_juli_right = findViewById(R.id.btn_juli_right);
        btn_buchang_left = findViewById(R.id.btn_buchang_left);
        btn_buchang_right = findViewById(R.id.btn_buchang_right);
        rl_rushui = findViewById(R.id.rl_rushui);
        rl_wake = findViewById(R.id.rl_wake);
        sw_laidian = findViewById(R.id.sw_laidian);
        sw_duanxin = findViewById(R.id.sw_duanxin);
        rl_naozhong = findViewById(R.id.rl_naozhong);
        rl_jiuzuo = findViewById(R.id.rl_jiuzuo);
        rl_wurao = findViewById(R.id.rl_wurao);
        rl_app = findViewById(R.id.rl_app);
        tv_buchang = findViewById(R.id.tv_buchang);
        tv_peidai = findViewById(R.id.tv_peidai);
        tv_rushui = findViewById(R.id.tv_rushui);
        tv_wake = findViewById(R.id.tv_wake);

        clear_phone_layout = findViewById(R.id.clear_phone_layout);
        clear_wearfit_layout = findViewById(R.id.clear_wearfit_layout);
        restore_layout = findViewById(R.id.restore_layout);
        firmware_update_layout = findViewById(R.id.firmware_update_layout);

        setData();
        setClickListener();
    }

    private void initData() {
        builder = new AlertDialog.Builder(this);

        //------同步动岚的个人信息--开始------
        if (infoData != null) {
            if (infoData.getPerson() != null) {

            }
        }
        if ("2".equals(infoData.getPerson().getGender())) {
            wearFitUser.setSex(2);//性别
        } else {
            wearFitUser.setSex(1);
        }
        if (infoData.getPerson().getHeight() != null && infoData.getPerson().getHeight().length() > 0) {//身高  "170.0"
            int idx = infoData.getPerson().getHeight().indexOf(".");
            String hStr = 173 + "";
            if (idx > 0) {
                hStr = infoData.getPerson().getHeight().substring(0, idx);
            }
            wearFitUser.setHeight(Integer.valueOf(hStr));
        } else {
            wearFitUser.setHeight(173);//手环默认
        }
        if (infoData.getPerson().getWeight() != null && infoData.getPerson().getWeight().length() > 0) {
            int idx = infoData.getPerson().getWeight().indexOf(".");
            String wStr = 65 + "";
            if (idx > 0) {
                wStr = infoData.getPerson().getWeight().substring(0, idx);
            }
            wearFitUser.setWeight(Integer.valueOf(wStr));
        } else {
            wearFitUser.setWeight(65);//手环默认
        }
        if (infoData.getPerson().getBirthday() != null) {//1990-12-10
            int age = TimeUtils.getAgeFromBirthTime(new Date(TimeUtils.date2TimeStamp(infoData.getPerson().getBirthday(), "yyyy-MM-dd")));
            wearFitUser.setAge(age);
        } else {
            wearFitUser.setAge(24);
        }
        //------同步动岚的个人信息--结束------

        if (wearFitUser.getWear() != 2) {//佩戴 1左手   2右手
            wearFitUser.setWear(1);
            tv_peidai.setText("左手");
        } else {
            tv_peidai.setText("右手");
        }
        if (wearFitUser.getStepLength() <= 0) {//步长
            wearFitUser.setStepLength(50);
        }
        tv_buchang.setText(wearFitUser.getStepLength() + "");
        if (wearFitUser.getUpHand() != 1) {//抬手亮屏 0关  1开
            wearFitUser.setUpHand(0);
            sw_taishou.setChecked(false);
        } else {
            sw_taishou.setChecked(true);
        }
        if (wearFitUser.getAntiLostAlert() != 1) {//防丢开关 0关  1开
            wearFitUser.setAntiLostAlert(0);
            sw_fangdiu.setChecked(false);
        } else {
            sw_fangdiu.setChecked(true);
        }
        if (wearFitUser.getDistanceUnit() != 1) {//距离单位 0英里 1厘米
            wearFitUser.setDistanceUnit(0);
            btn_juli_left.setChecked(true);
            btn_juli_right.setChecked(false);
        } else {
            btn_juli_left.setChecked(false);
            btn_juli_right.setChecked(true);
        }
        if (wearFitUser.getHeightUnit() != 1) {//身高单位 0英里 1厘米
            wearFitUser.setHeightUnit(0);
            btn_buchang_left.setChecked(true);
            btn_buchang_right.setChecked(false);
        } else {
            btn_buchang_left.setChecked(false);
            btn_buchang_right.setChecked(true);
        }
        String sleepHourStr = "00";
        String sleepMinutesStr = "00";
        String wakeUpHourStr = "00";
        String wakeUpMinutesStr = "00";
        if (wearFitUser.getSleepHour() != 0 && (wearFitUser.getSleepHour() >= 0 && wearFitUser.getSleepHour() < 10)) {
            sleepHourStr = "0" + wearFitUser.getSleepHour();
        }
        if (wearFitUser.getSleepMinutes() != 0 && (wearFitUser.getSleepMinutes() >= 0 && wearFitUser.getSleepMinutes() < 10)) {
            sleepMinutesStr = "0" + wearFitUser.getSleepMinutes();
        }
        if (wearFitUser.getWakeUpHour() != 0 && (wearFitUser.getWakeUpHour() >= 0 && wearFitUser.getWakeUpHour() < 10)) {
            wakeUpHourStr = "0" + wearFitUser.getWakeUpHour();
        }
        if (wearFitUser.getWakeUpMinutes() != 0 && (wearFitUser.getWakeUpMinutes() >= 0 && wearFitUser.getWakeUpMinutes() < 10)) {
            wakeUpMinutesStr = "0" + wearFitUser.getWakeUpMinutes();
        }
        tv_rushui.setText(sleepHourStr + ":" + sleepMinutesStr);//入睡
        tv_wake.setText(wakeUpHourStr  + ":" +wakeUpMinutesStr);//醒来

        if (wearFitUser.getCallsAlerts() != 1) {//来电提醒 0关  1开
            wearFitUser.setCallsAlerts(0);
            sw_laidian.setChecked(false);
        } else {
            sw_laidian.setChecked(true);
        }
        if (wearFitUser.getSMSAlerts() != 1) {//短信提醒 0关  1开
            wearFitUser.setSMSAlerts(0);
            sw_duanxin.setChecked(false);
        } else {
            sw_duanxin.setChecked(true);
        }
        //目标距离  目前没有设置的地方   给默认
        wearFitUser.setGold_steps(8000);
        //血压最大 最小  目前没有设置的地方   给默认
        wearFitUser.setBpMax(120);
        wearFitUser.setBpMin(75);
        commandManager.setSmartWarnNoContent(1, 1);
        battery_view.setPower((float) 0.0);

        gson = new Gson();
        sleepTime = new LongSit();
    }

    private void setData() {
        if (!MyApplication.mBluetoothConnected) {
            tv_shouhuan_name.setText("未绑定");
            btn_jiebang.setText("去绑定");
            tv_power.setText("");
            battery_view.setVisibility(View.GONE);
        } else {
            tv_shouhuan_name.setText(name);
            tv_shouhuan_num.setText(address);
            tv_status.setText("已绑定");
            btn_jiebang.setText("解绑");
        }
        commandManager.getBatteryInfo();//充电  电量
    }

    private void setClickListener() {
        rl_app.setOnClickListener(onClickListener);
        rl_peidai.setOnClickListener(onClickListener);
        rl_naozhong.setOnClickListener(onClickListener);
        rl_rushui.setOnClickListener(onClickListener);
        rl_wake.setOnClickListener(onClickListener);
        rl_jiuzuo.setOnClickListener(onClickListener);
        rl_wurao.setOnClickListener(onClickListener);
        tv_buchang.setOnClickListener(onClickListener);
        clear_phone_layout.setOnClickListener(onClickListener);
        clear_wearfit_layout.setOnClickListener(onClickListener);
        restore_layout.setOnClickListener(onClickListener);
        firmware_update_layout.setOnClickListener(onClickListener);
        btn_juli_left.setOnCheckedChangeListener(juliListener);
        btn_juli_right.setOnCheckedChangeListener(juliListener);
        btn_buchang_left.setOnCheckedChangeListener(buchangListener);
        btn_buchang_right.setOnCheckedChangeListener(buchangListener);
        rl_buchang.setOnClickListener(onClickListener);

        btn_jiebang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.mBluetoothConnected) {
                    try {
                        MyApplication.mBluetoothLeService.disconnect();
                        MyApplication.mBluetoothConnected = false;//更改解绑连接状态 yxx
                        SPUtils.setString(Constants.ADDRESS, "");
                        SPUtils.setString(Constants.NAME, "");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    startActivityForResult(new Intent(WearFitSettingActivity.this, WearFitEquipmentActivity.class), 2);
                }
                setData();//更改解绑连接状态 yxx
            }
        });
        //抬手亮屏开关
        sw_taishou.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("yxx", "抬手亮屏开关" + MyApplication.mBluetoothConnected + MyApplication.mBluetoothConnected + ";b=" + b);
                if (MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address)) {//如果已连接手环
                    if (b) {
                        wearFitUser.setUpHand(1);
                        commandManager.setUpHandLightScreen(1);
                    } else {
                        wearFitUser.setUpHand(0);
                        commandManager.setUpHandLightScreen(0);
                    }
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                } else {
                    Toast.makeText(context, "请先连接手环", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //防丢开关
        sw_fangdiu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address)) {//如果已连接手环
                    if (b) {
                        wearFitUser.setAntiLostAlert(1);
                        commandManager.setAntiLostAlert(1);
                    } else {
                        wearFitUser.setAntiLostAlert(0);
                        commandManager.setAntiLostAlert(0);
                    }
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                } else {
                    Toast.makeText(context, "请先连接手环", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //来电提醒
        sw_laidian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address)) {//如果已连接手环
                    if (b) {
                        wearFitUser.setCallsAlerts(1);//来电提醒 0关  1开
                        commandManager.setSmartWarnNoContent(1, 1);
                    } else {
                        wearFitUser.setCallsAlerts(0);
                        commandManager.setSmartWarnNoContent(1, 0);
                    }
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                } else {
                    Toast.makeText(context, "请先连接手环", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //来短信提醒
        sw_duanxin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address)) {//如果已连接手环
                    if (b) {
                        wearFitUser.setSMSAlerts(1);
                        commandManager.setSmartWarnNoContent(3, 1);
                    } else {
                        wearFitUser.setSMSAlerts(0);
                        commandManager.setSmartWarnNoContent(3, 0);
                    }
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                } else {
                    Toast.makeText(context, "请先连接手环", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address)) {//如果已连接手环
                switch (view.getId()) {
                    case R.id.rl_app:
                        startActivity(new Intent(WearFitSettingActivity.this, WearFitRemindActivity.class));
                        break;
                    case R.id.rl_peidai:
                        showDialogToApp("请选择左右手", "", "左手", "右手"
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tv_peidai.setText("左手");
                                        wearFitUser.setWear(1);
                                        DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        tv_peidai.setText("右手");
                                        wearFitUser.setWear(2);
                                        DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                                    }
                                });
                        break;
                    case R.id.rl_naozhong://闹钟提醒
                        startActivity(new Intent(WearFitSettingActivity.this, WearFitAddClockActivity.class));
                        break;
                    case R.id.rl_rushui:
                        int hourStrTemp = 0;
                        int minuteStrTemp = 0;
                        String[] times = tv_rushui.getText().toString().split("\\:");
                        if (times != null && times.length > 1) {
                            hourStrTemp = Integer.valueOf(times[0]);
                            minuteStrTemp = Integer.valueOf(times[1]);
                        }
                        showTimeSelect("入睡时间", hourStrTemp, minuteStrTemp);
                        break;
                    case R.id.rl_wake:
                        hourStrTemp = 0;
                        minuteStrTemp = 0;
                        times = tv_wake.getText().toString().split("\\:");
                        if (times != null && times.length > 1) {
                            hourStrTemp = Integer.valueOf(times[0]);
                            minuteStrTemp = Integer.valueOf(times[1]);
                        }
                        showTimeSelect("醒来时间", hourStrTemp, minuteStrTemp);
                        break;
                    case R.id.rl_jiuzuo://久坐
                        startActivity(new Intent(WearFitSettingActivity.this, WearFitLongSitActivity.class).putExtra("from", "久坐提醒"));
                        break;
                    case R.id.rl_wurao://勿扰
                        startActivity(new Intent(WearFitSettingActivity.this, WearFitLongSitActivity.class).putExtra("from", "勿扰模式"));
                        break;
                    case R.id.tv_buchang://步长
                        PickerViewDialog dialog = new PickerViewDialog(context, new PickerViewDialog.PickerListener() {
                            @Override
                            public void getPickerSelect(String text) {
                                tv_buchang.setText(text);
                                int stepLength = Integer.valueOf(text);
                                wearFitUser.setStepLength(stepLength);
                                DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                                commandManager.personalData(wearFitUser);
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.rl_buchang://步长
                        dialog = new PickerViewDialog(context, new PickerViewDialog.PickerListener() {
                            @Override
                            public void getPickerSelect(String text) {
                                tv_buchang.setText(text);
                                int stepLength = Integer.valueOf(text);
                                wearFitUser.setStepLength(stepLength);
                                DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                                commandManager.personalData(wearFitUser);
                            }
                        });
                        dialog.show();
                        break;
                    case R.id.clear_phone_layout://清除手机数据
                        showDialogToApp("清除机数据", "清除手机数据后，手机的所有历史数据将被清除，若手环有数据，重连刷新仍会同步过来，是否确认清除？", "确定", "取消"
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {//确定
                                        wearFitUser = new WearFitUser();
                                        DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {//取消
                                        dia.dismiss();
                                    }
                                });
                        break;
                    case R.id.clear_wearfit_layout://清除手环数据
                        showDialogToApp("清除手环数据", "清除手环数据后，手环的所有历史数据将被清除，是否确认清除？", "确定", "取消"
                                , new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {//确定
                                        commandManager.setClearData();
                                    }
                                }, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {//取消
                                        dia.dismiss();
                                    }
                                });
                        break;
                    case R.id.restore_layout://恢复出厂设置
                        final AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                        builder2.setTitle("警告");
                        builder2.setMessage("确定要恢复出厂设置吗?");
                        final AlertDialog alertDialog = builder2.show();
                        builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                alertDialog.dismiss();
                            }
                        });
                        builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                commandManager.setClearData();
                                commandManager.Shutdown();
                            }
                        });
                        builder2.show();
                        break;
                    case R.id.firmware_update_layout://固件升级
                        //TODO 没给这个接口
                        break;
                }
            } else {
                Toast.makeText(context, "请先连接手环", Toast.LENGTH_SHORT).show();
            }
        }
    };

    CompoundButton.OnCheckedChangeListener juliListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.btn_juli_left://距离单位 0英里 1厘米
                    wearFitUser.setDistanceUnit(0);
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                    commandManager.personalData(wearFitUser);
                    btn_juli_left.setTextColor(context.getResources().getColor(R.color.white));
                    btn_juli_left.setBackground(context.getResources().getDrawable(R.drawable.button_left_checked));
                    btn_juli_right.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    btn_juli_right.setBackground(context.getResources().getDrawable(R.drawable.button_right));
                    break;
                case R.id.btn_juli_right://距离单位 0英里 1厘米
                    wearFitUser.setDistanceUnit(1);
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                    commandManager.personalData(wearFitUser);
                    btn_juli_left.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    btn_juli_left.setBackground(context.getResources().getDrawable(R.drawable.button_left));
                    btn_juli_right.setTextColor(context.getResources().getColor(R.color.white));
                    btn_juli_right.setBackground(context.getResources().getDrawable(R.drawable.button_right_checked));
                    break;
            }
        }
    };
    CompoundButton.OnCheckedChangeListener buchangListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.btn_buchang_left://身高单位 0英里 1厘米
                    wearFitUser.setHeightUnit(0);
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                    btn_buchang_left.setTextColor(context.getResources().getColor(R.color.white));
                    btn_buchang_left.setBackground(context.getResources().getDrawable(R.drawable.button_left_checked));
                    btn_buchang_right.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    btn_buchang_right.setBackground(context.getResources().getDrawable(R.drawable.button_right));
                    break;
                case R.id.btn_buchang_right://身高单位 0英里 1厘米
                    wearFitUser.setHeightUnit(1);
                    DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                    btn_buchang_left.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    btn_buchang_left.setBackground(context.getResources().getDrawable(R.drawable.button_left));
                    btn_buchang_right.setTextColor(context.getResources().getColor(R.color.white));
                    btn_buchang_right.setBackground(context.getResources().getDrawable(R.drawable.button_right_checked));
                    break;
            }
        }
    };

    private void getHistory() {
        String sleepTimeGson = SPUtils.getString("SleepTimeLongSit", "");
        if (!StringUtils.isNullorEmpty(sleepTimeGson)) {
            Type listType = new TypeToken<LongSit>() {
            }.getType();
            sleepTime = gson.fromJson(sleepTimeGson, listType);
        }
    }

    private void showTimeSelect(final String str, int lookHour, int lookMinute) {
        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, str);
        customDateAndTimePicker.setGoneYearAndMounth();
        customDateAndTimePicker.showWindow(lookHour, lookMinute);
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                String hourStr = customDateAndTimePicker.getHour();
                String minuteStr = customDateAndTimePicker.getMinute();
                if ("入睡时间".equals(str)) {
                    tv_rushui.setText(hourStr + ":" + minuteStr);
                    sleepTime.setStartHour(Integer.valueOf(hourStr));
                    sleepTime.setStartMinute(Integer.valueOf(minuteStr));
                    wearFitUser.setSleepHour(Integer.valueOf(hourStr));
                    wearFitUser.setSleepMinutes(Integer.valueOf(minuteStr));
                } else if ("醒来时间".equals(str)) {
                    tv_wake.setText(hourStr + ":" + minuteStr);
                    sleepTime.setEndHour(Integer.valueOf(hourStr));
                    sleepTime.setEndMinute(Integer.valueOf(minuteStr));
                    wearFitUser.setWakeUpHour(Integer.valueOf(hourStr));
                    wearFitUser.setWakeUpMinutes(Integer.valueOf(minuteStr));
                }
                sleepTime.setOn(1);
                SPUtils.setString("SleepTimeLongSit", gson.toJson(sleepTime));
                DataInfoCache.saveOneCache(wearFitUser, Constants.MY_WEAR_FIT_SETTING);//新增缓存 手环设置
                commandManager.sendSleepTime(sleepTime);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (MyApplication.mBluetoothConnected) {
                String name = SPUtils.getString(Constants.NAME, "");
                String address = SPUtils.getString(Constants.ADDRESS, "");
                tv_shouhuan_name.setText(name);
                tv_shouhuan_num.setText(address);
                tv_status.setText("已绑定");
                btn_jiebang.setText("解绑");
            } else {
                tv_shouhuan_name.setText("已断开");
                tv_shouhuan_num.setText("");
                tv_status.setText("");
                tv_power.setText("");
                battery_view.setVisibility(View.GONE);
                btn_jiebang.setText("去绑定");
            }
        }
    }

    /**
     * 通用dialog
     *
     * @param title
     * @param message
     * @param PositiveStr
     * @param NegativeStr
     * @param onClick1
     * @param onClick2
     */
    private void showDialogToApp(String title, String message, final String PositiveStr, final String NegativeStr
            , DialogInterface.OnClickListener onClick1, DialogInterface.OnClickListener onClick2) {
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(PositiveStr, onClick1);
        builder.setNegativeButton(NegativeStr, onClick2);
        dia = builder.show();
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    //接收蓝牙状态改变的广播
    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = true;
                MyApplication.isBluetoothConnecting = false;
                ToastUtils.showToastShort("连接成功");
                SPUtils.setString(Constants.ADDRESS, address);
                SPUtils.setString(Constants.NAME, name);
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
//                MyApplication.mBluetoothConnected = false;
                try {
                    MyApplication.mBluetoothLeService.close();//断开更彻底(没有这一句，在某些机型，重连会连不上)
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                tv_shouhuan_name.setText("已断开");
                tv_shouhuan_num.setText("");
                tv_status.setText("");
                tv_power.setText("");
                battery_view.setVisibility(View.GONE);
                btn_jiebang.setText("去绑定");
                ToastUtils.showToastShort("已断开");
                SPUtils.setString(Constants.ADDRESS, "");
                SPUtils.setString(Constants.NAME, "");
                //LogUtil.d("BluetoothLeService", "断开");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
//                Log.i("zgy", "接收到的数据：" + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                LogUtil.i("接收的数据：" + DataHandlerUtils.bytesToHexStr(txValue));

                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);

                if (datas.get(4) == 0x51 && datas.get(5) == 17) {
                    LogUtil.i(datas.toString());
                }
                if (datas.get(4) == 0x51 && datas.get(5) == 8) {
                    LogUtil.i(datas.toString());
                }
                if (datas.get(4) == 0x77) {
                    LogUtil.i("抬手亮屏");
                }
                if (datas.get(4) == 0x91) {//[171, 0, 5, 255, 145, 128, 3, 60]
                    LogUtil.i(datas.toString());

                    Integer integer = datas.get(6);//是否充电
                    Integer integer1 = datas.get(7);//电量多少
                    if (integer == 0) {
                        tv_power.setText(integer1 + "%");
                        battery_view.setmIsCharging(false);
                    } else {
                        tv_power.setText("正在充电  " + integer1 + "%");
                        battery_view.setmIsCharging(true);
                    }
                    battery_view.setPower(((float) integer1) / 100);
                    battery_view.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        getHistory();
        address = SPUtils.getString(Constants.ADDRESS, "");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

}

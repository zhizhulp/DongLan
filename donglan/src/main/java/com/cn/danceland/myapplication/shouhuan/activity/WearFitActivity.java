package com.cn.danceland.myapplication.shouhuan.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.db.HeartRate;
import com.cn.danceland.myapplication.db.HeartRateHelper;
import com.cn.danceland.myapplication.db.WearFitSleepBean;
import com.cn.danceland.myapplication.db.WearFitSleepHelper;
import com.cn.danceland.myapplication.db.WearFitStepBean;
import com.cn.danceland.myapplication.db.WearFitStepHelper;
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
import com.cn.danceland.myapplication.view.DongLanTitleView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 手环首页
 * Created by feng on 2018/6/19.
 */
public class WearFitActivity extends Activity {
    private static final int GPS_REQUEST_CODE = 2;
    private static final int REQUEST_SEARCH = 1;
    private static final int MSG_REFRESH_DATA = 0;//请求数据  心率  睡眠
    private TextView tv_connect;
    private TextView tv_step;
    private TextView tv_kcal;
    private TextView tv_km;
    private ProgressBar progressb_target;
    private String address;
    private String name;
    private GridView gv_wearfit;
    private DongLanTitleView shouhuan_title;
    private int[] imgID = {R.drawable.shouhuan_heart, R.drawable.shouhuan_sleep, R.drawable.shouhuan_pilao, R.drawable.shouhuan_photo, R.drawable.shouhuan_plan, R.drawable.shouhuan_find
            , R.drawable.shouhuan_setting};
    private String[] text1s = {"心率", "睡眠", "疲劳", "摇摇拍照", "健身计划", "查找手环", "设置"};
    private String[] text2s = {"--bpm", "-时-分", "--", "", "", "", ""};
    private ArrayList<ItemBean> itemBeans;
    private FrameLayout step_gauge_layout;//计步
    private ProgressDialog progressDialog;
    private String address1;//手环默认链接地址  yxx
    private RelativeLayout rl_connect;
    private CommandManager commandManager;
    private WearFitAdapter adapter;

    private HeartRateHelper heartRateHelper = new HeartRateHelper();
    private WearFitSleepHelper sleepHelper = new WearFitSleepHelper();
    private WearFitStepHelper stepHelper = new WearFitStepHelper();

    private SharedPreferences wear_fit_home_data;//获取存储中的数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit);
        initHost();
        initView();
    }

    private void initHost() {
        commandManager = CommandManager.getInstance(this);
        progressDialog = new ProgressDialog(this);
        itemBeans = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.img_url = imgID[i];
            itemBean.text1 = text1s[i];
            itemBean.text2 = text2s[i];
            itemBeans.add(itemBean);
        }
        address1 = SPUtils.getString(Constants.ADDRESS, "");
        LogUtil.i("ADDRESS2----" + address1);
        wear_fit_home_data = getSharedPreferences("wear_fit_home_data", MODE_PRIVATE);
    }

    private void initView() {
        rl_connect = findViewById(R.id.rl_connect);
        gv_wearfit = findViewById(R.id.gv_wearfit);
        step_gauge_layout = findViewById(R.id.step_gauge_layout);
        adapter = new WearFitAdapter();
        gv_wearfit.setAdapter(adapter);
        shouhuan_title = findViewById(R.id.shouhuan_title);
        shouhuan_title.setTitle("我的手环");
        tv_connect = findViewById(R.id.tv_connect);
        tv_step = findViewById(R.id.tv_step_gauge);
        tv_kcal = findViewById(R.id.tv_kcal);
        tv_km = findViewById(R.id.tv_km);
        progressb_target = findViewById(R.id.progressb_target);
        setListener();
        heartRateLast = wear_fit_home_data.getInt("heart_rate", 0);
        initChildView(wear_fit_home_data.getInt("step", 0)
                , wear_fit_home_data.getInt("kcal", 0)
                , wear_fit_home_data.getInt("heart_rate", 0)
                , wear_fit_home_data.getInt("sleep", 0));

        step_gauge_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(WearFitActivity.this, WearFitStepActivity.class));//计步
            }
        });
        if (!MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address1) && address1.length() > 0) {
            try {
                LogUtil.i("ADDRESS1----" + address1);
                if (MyApplication.mBluetoothLeService.connect(address1)) {
                    //tv_status.setText(name+"--"+address);
                    //ToastUtils.showToastShort("正在连接...");
                    progressDialog.setMessage("正在连接...");
                    progressDialog.show();
                } else {
                    ToastUtils.showToastShort("连接失败");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            MyApplication.isBluetoothConnecting = true;
            invalidateOptionsMenu();

        } else {
            rl_connect.setVisibility(View.VISIBLE);
            tv_connect.setText("还未绑定手环，点击绑定>>");
        }
        initHeartData();//心率
        initSleepData();//睡眠
        initStepGaugeData();//计步
        commandManager.sendStep();//首页数据
        commandManager.setTimeSync();//同步时间给手环
    }

    private void initChildView(int step, int kcal, int heart_rate, int sleep) {

        WearFitUser wearFitUser = (WearFitUser) DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING);//手环设置

        NumberFormat numberFormat = NumberFormat.getInstance();// 创建一个数值格式化对象
        numberFormat.setMaximumFractionDigits(0);// 设置精确到小数点后2位
        String targetStr = numberFormat.format((float) step / (float) 8000 * 100);//达标
        float kmf = (float) 50 * (float) step / (float) 100000.00;//100步长  1000km
        if (wearFitUser != null) {
            targetStr = numberFormat.format((float) step / (float) wearFitUser.getGold_steps() * 100);//达标
            kmf = (float) wearFitUser.getStepLength() * (float) step / (float) 100000.00;//100步长  1000km
        }
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String km = fnum.format(kmf);
        int target = 0;
        if (StringUtils.isNumeric(targetStr)) {
            target = Integer.valueOf(targetStr);
        }
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int fatigue = 0;//疲劳
        if (hour >= 6 && hour < 11) {
            fatigue = 0;
        } else if (hour >= 11 && hour < 18) {
            fatigue = 10;
        } else if (hour >= 18 && hour < 24) {
            fatigue = 20;
        } else {
            fatigue = 30;
        }
        fatigue = (int) (fatigue + Math.sqrt(step) / 2);

        tv_step.setText(step + "");
        tv_kcal.setText(kcal + WearFitActivity.this.getResources().getString(R.string.kcal_text));
        tv_km.setText(km + WearFitActivity.this.getResources().getString(R.string.km_english_text));
        progressb_target.setProgress(target);
        itemBeans.get(0).text2 = heart_rate + "";//item 心率
        itemBeans.get(1).text2 = sleep + "";//item 睡眠
        itemBeans.get(2).text2 = fatigue + "";//item 疲劳
        adapter.notifyDataSetChanged();
    }


    private class WearFitAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return itemBeans.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            View inflate = View.inflate(WearFitActivity.this, R.layout.shouhuan_gv_item, null);
            ImageView img = inflate.findViewById(R.id.img);
            TextView text1 = inflate.findViewById(R.id.tv_text1);
            TextView text2 = inflate.findViewById(R.id.tv_text2);
            Glide.with(WearFitActivity.this).load(itemBeans.get(i).img_url).into(img);
            text1.setText(itemBeans.get(i).text1);
            text2.setText(itemBeans.get(i).text2);
            return inflate;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (MyApplication.mBluetoothConnected) {
            rl_connect.setVisibility(View.GONE);
        } else {
            rl_connect.setVisibility(View.VISIBLE);
            tv_connect.setText("还未绑定手环，点击绑定>>");
        }
    }
    private final int RESULT_CODE_CAMERA = 1;//判断是否有拍照权限的标识码
    private void setListener() {

        tv_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WearFitActivity.this, WearFitEquipmentActivity.class);
                startActivity(intent);
            }
        });

        gv_wearfit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0://心率
                        startActivity(new Intent(WearFitActivity.this, WearFitHeartRateActivity.class));
                        //   commandManager.realTimeAndOnceMeasure(0x80, 1);
//                        commandManager.setSyncData(System.currentTimeMillis() - 15 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 15 * 24 * 60 * 60 * 1000);
                        break;
                    case 1://睡眠
                        startActivity(new Intent(WearFitActivity.this, WearFitSleepActivity.class));
                        break;
                    case 2://疲劳
                        startActivity(new Intent(WearFitActivity.this, WearFitFatigueActivity.class));
                        break;
                    case 3://摇摇拍照

                        if (MyApplication.mBluetoothConnected) {
                            try {
                                if (ActivityCompat.checkSelfPermission(WearFitActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    //提示用户开户权限
                                    String[] perms = {"android.permission.CAMERA"};
                                    ActivityCompat.requestPermissions(WearFitActivity.this, perms, RESULT_CODE_CAMERA);
                                }else{
                                    startActivity(new Intent(WearFitActivity.this, WearFitCameraActivity.class));
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(WearFitActivity.this, "手环未绑定", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case 4://健身计划
                        ToastUtils.showToastShort("功能正在开发中");
//                        startActivity(new Intent(WearFitActivity.this, WearFitFitnessPlanActivity.class));
                        break;
                    case 5://查找手环
                        openGPSSettings();
//                        startActivityForResult(new Intent(WearFitActivity.this, DeviceScanActivity.class), REQUEST_SEARCH);
                        break;
                    case 6://设置
                        startActivity(new Intent(WearFitActivity.this, WearFitSettingActivity.class));
                        break;
                }
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode, String[] permissions, int[] grantResults) {
        switch (permsRequestCode) {
            case RESULT_CODE_CAMERA:
                boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                if (cameraAccepted) {
                    //授权成功之后，调用系统相机进行拍照操作等
                        startActivity(new Intent(WearFitActivity.this, WearFitCameraActivity.class));
                } else {
                    //用户授权拒绝之后，友情提示一下就可以了
                    Toast.makeText(WearFitActivity.this, "请开启应用拍照权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 检测GPS是否打开
     *
     * @return
     */
    private boolean checkGPSIsOpen() {
        boolean isOpen;
        LocationManager locationManager = (LocationManager) this
                .getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    /**
     * 跳转GPS设置
     */
    private void openGPSSettings() {
        if (checkGPSIsOpen()) {
            startActivityForResult(new Intent(WearFitActivity.this, DeviceScanActivity.class), REQUEST_SEARCH);
        } else {
            //没有打开则弹出对话框
            new AlertDialog.Builder(this)
                    .setTitle(R.string.notifyTitle)
                    .setMessage(R.string.gpsNotifyMsg)
                    // 拒绝, 退出应用
                    .setNegativeButton(R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })

                    .setPositiveButton(R.string.setting,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //跳转GPS设置界面
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivityForResult(intent, GPS_REQUEST_CODE);
                                }
                            })

                    .setCancelable(false)
                    .show();

        }
    }
    private Data infoData;
    private WearFitUser wearFitUser;

    private void setDefaultData() {
        infoData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);//动岚个人资料
        if (DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING) != null) {
            wearFitUser = (WearFitUser) DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING);//手环设置
        } else {
            wearFitUser = new WearFitUser();
            //------同步动岚的个人信息--开始------
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
            }
            if (wearFitUser.getStepLength() <= 0) {//步长
                wearFitUser.setStepLength(50);
            }
            if (wearFitUser.getUpHand() != 1) {//抬手亮屏 0关  1开
                wearFitUser.setUpHand(0);
            }
            if (wearFitUser.getAntiLostAlert() != 1) {//防丢开关 0关  1开
                wearFitUser.setAntiLostAlert(0);
            }
            if (wearFitUser.getDistanceUnit() != 1) {//距离单位 0英里 1厘米
                wearFitUser.setDistanceUnit(0);
            }
            if (wearFitUser.getHeightUnit() != 1) {//身高单位 0英里 1厘米
                wearFitUser.setHeightUnit(0);
            }

            if (wearFitUser.getCallsAlerts() != 1) {//来电提醒 0关  1开
                wearFitUser.setCallsAlerts(0);
            }
            if (wearFitUser.getSMSAlerts() != 1) {//短信提醒 0关  1开
                wearFitUser.setSMSAlerts(0);
            }
            //目标距离  目前没有设置的地方   给默认
            wearFitUser.setGold_steps(8000);
            //血压最大 最小  目前没有设置的地方   给默认
            wearFitUser.setBpMax(120);
            wearFitUser.setBpMin(75);
//            commandManager.setSmartWarnNoContent(1, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_REQUEST_CODE) {
            //做需要做的事情，比如再次检测是否打开GPS了 或者定位
            openGPSSettings();
        }
        if (requestCode == REQUEST_SEARCH && resultCode == RESULT_OK) {
            address = data.getStringExtra(Constants.ADDRESS);
            name = data.getStringExtra(Constants.NAME);
            SPUtils.setString(Constants.ADDRESS, address);
            SPUtils.setString(Constants.NAME, name);
            address1 = SPUtils.getString(Constants.ADDRESS, "");
            if (!TextUtils.isEmpty(address)) {

                try {
                    if (MyApplication.mBluetoothLeService.connect(address)) {
                        //tv_status.setText(name+"--"+address);
                        //ToastUtils.showToastShort("正在连接...");
                        progressDialog.setMessage("正在连接...");
                        progressDialog.show();
                    } else {
                        ToastUtils.showToastShort("连接失败");
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                MyApplication.isBluetoothConnecting = true;
                invalidateOptionsMenu();//显示正在连接 ...
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
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
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {//指明一个与远程设备建立的低级别（ACL）连接。
                initHeartData();//心率
                initSleepData();//睡眠
                initStepGaugeData();//计步
                commandManager.sendStep();//首页数据
                commandManager.setTimeSync();//同步时间给手环
            }
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = true;
                MyApplication.isBluetoothConnecting = false;
                invalidateOptionsMenu();//更新菜单栏
                setDefaultData();//设置手环默认数据

                ToastUtils.showToastShort("连接成功");
                rl_connect.setVisibility(View.GONE);
                progressDialog.dismiss();

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = false;
                invalidateOptionsMenu();//更新菜单栏
                try {
                    MyApplication.mBluetoothLeService.close();//断开更彻底(没有这一句，在某些机型，重连会连不上)
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                ToastUtils.showToastShort("已断开");
                tv_connect.setText("还未绑定手环，点击绑定>>");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
//                LogUtil.i("接收的数据：" + DataHandlerUtils.bytesToHexStr(txValue));

                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);
                //心率传感器
                if (datas.get(4) == 0XB4) {//[171, 0, 4, 255, 180, 128, 1]
                    Integer integer = datas.get(6);
                    if (integer == 0) {
                        Toast.makeText(context, "手环通信不正常", Toast.LENGTH_SHORT).show();
                    } else if (integer == 1) {
                        Toast.makeText(context, "手环通信正常", Toast.LENGTH_SHORT).show();
                    }
                }
                //拉取心率数据
                if (datas.get(4) == 0x51 && datas.size() == 13) {//[171, 0, 10, 255, 81, 17, 18, 5, 19, 4, 35, 62, 62]
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + datas.get(10) + ":" + "00";
                    HeartRate heartRate = new HeartRate();
                    heartRate.setDate(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    heartRate.setHeartRate(datas.get(11));
                    heartRateHelper.insert(heartRate);//心率

                    Calendar calendar = Calendar.getInstance();
                    Date nowTime = new Date(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    Date endTime = new Date(calendar.getTimeInMillis());
                    calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) - 8);
                    Date startTime = new Date(calendar.getTimeInMillis());

                    if (TimeUtils.isEffectiveDate(nowTime, startTime, endTime)) {//八分钟之内  暂时不用 高威2018.8.27
                        heartRateLast = datas.get(11);
                        if (datas.get(11) != 0) {
                            itemBeans.get(0).text2 = heartRateLast + "";//item 心率
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
                //拉取睡眠数据
                if (datas.get(4) == 0x52 && datas.size() == 14) {//[171, 0, 11, 255, 82, 128, 18, 7, 31, 0, 49, 2, 0, 29]  11位state 12位*256+13位
                    LogUtil.i("14位" + datas.toString());
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + datas.get(10) + ":" + "00";
                    WearFitSleepBean sleepBean = new WearFitSleepBean();
                    sleepBean.setTimestamp(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    sleepBean.setState(datas.get(11) + "");//11位state
                    sleepBean.setContinuoustime(datas.get(12) * 256 + datas.get(13));//睡了多久 12位*256+13位
                    LogUtil.i("sleepBean" + sleepBean + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(sleepBean.getTimestamp())).toString());
                    sleepHelper.insert(sleepBean);//睡眠
                }
                //拉取计步数据
                if (datas.get(4) == 0x51 && datas.size() == 20) {
//                    LogUtil.i(datas.toString());
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + "00" + ":" + "00";
                    int step = datas.get(10) * 256 * 256 + datas.get(11) * 256 + datas.get(12);
                    int cal = datas.get(13) * 256 * 256 + datas.get(14) * 256 + datas.get(15);
                    WearFitStepBean wearFitStepBean = new WearFitStepBean();
                    wearFitStepBean.setTimestamp(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    wearFitStepBean.setStep(step);
                    wearFitStepBean.setCal(cal);
                    int fatigue = 0;
                    int hour = datas.get(9);
                    if (hour >= 6 && hour < 11) {
                        fatigue = 0;
                    } else if (hour >= 11 && hour < 18) {
                        fatigue = 10;
                    } else if (hour >= 18 && hour < 24) {
                        fatigue = 20;
                    } else {
                        fatigue = 30;
                    }
                    fatigue = (int) (fatigue + Math.sqrt(step) / 2);
                    wearFitStepBean.setFatigue(fatigue);//疲劳
                    LogUtil.i("时间：" + date + " 步数：" + step + " 卡路里：" + cal);
                    stepHelper.insert(wearFitStepBean);// 计步
                }
                if (datas.get(4) == 0x51 && datas.size() == 17) {//首页数据
//                    LogUtil.i(datas.toString());
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + "00" + ":" + "00";
                    int step = (datas.get(6) << 16) + (datas.get(7) << 8) + datas.get(8);//计步
                    int cal = (datas.get(9) << 16) + (datas.get(10) << 8) + datas.get(11);//卡路里
                    int ligthSleep = datas.get(12) * 60 + datas.get(13);//浅睡
                    int deepSleep = datas.get(14) * 60 + datas.get(15);//深睡
                    int wakeupTime = datas.get(16);//醒来次数
                    initChildView(step, cal, heartRateLast, deepSleep + ligthSleep);
                }
            }
        }
    };
    int heartRateLast = 0;//最后心率值

    private void initHeartData() {
        //显示本地数据库最后一条心率
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        List<HeartRate> heartRates = heartRateHelper.queryByDay(TimeUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));//获取本地数据库心率
        if (heartRates != null && heartRates.size() != 0) {
            heartRateLast = heartRates.get(heartRates.size() - 1).getHeartRate();
            itemBeans.get(0).text2 = heartRateLast + "";//item 心率
            adapter.notifyDataSetChanged();
        }
        long time = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
        LogUtil.i("获取这个之后的心率数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        commandManager.setSyncData(time, time);
    }

    private void initSleepData() {
        long time = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
        LogUtil.i("获取这个之后的睡眠数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        commandManager.setSyncSleepData(time);
    }

    private void initStepGaugeData() {
        long time = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
        LogUtil.i("获取这个之后的计步数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        commandManager.setSyncData(time, time);
    }

    private class ItemBean {
        int img_url;
        String text1;
        String text2;
    }
}

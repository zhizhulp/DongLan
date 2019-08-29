package com.cn.danceland.myapplication.shouhuan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.db.HeartRate;
import com.cn.danceland.myapplication.db.HeartRateHelper;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.shouhuan.utils.DataHandlerUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.cn.danceland.myapplication.MyApplication.mBluetoothConnected;


public class ShouHuanMainActivity extends BaseActivity {

    private static final int REQUEST_SEARCH = 1;
    private TextView device_address;
    private TextView device_name;
    private TextView test_result;
    private String address;
    private String name;
    private GridView gridView;
    private List<String> list;
    private CommandManager manager;
    private boolean isTestHR, isTestSSXL, isTestDCXL = false;
    private MyAdapter myAdapter;
    private TextView tv_search;
    HeartRateHelper heartRateHelper = new HeartRateHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shouhuan);
        manager = CommandManager.getInstance(this);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        device_address = (TextView) findViewById(R.id.device_address);
        device_name = (TextView) findViewById(R.id.device_name);
        test_result = (TextView) findViewById(R.id.test_result);
        tv_search = (TextView) findViewById(R.id.tv_search);
        if (MyApplication.mBluetoothConnected) {
            tv_search.setText("断开");
            device_address.setText(SPUtils.getString(Constants.ADDRESS, ""));
            device_name.setText(SPUtils.getString(Constants.NAME, ""));
        } else {
            tv_search.setText("搜索");
        }
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.mBluetoothConnected) {
                    try {
                        MyApplication.mBluetoothLeService.disconnect();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    tv_search.setText("搜索");
                } else {
                    Intent intent = new Intent(ShouHuanMainActivity.this, DeviceScanActivity.class);
                    startActivityForResult(intent, REQUEST_SEARCH);
                }
            }
        });

        gridView = (GridView) findViewById(R.id.gridView);
//        device_address.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(ShouHuanMainActivity.this, DeviceScanActivity.class);
//                startActivityForResult(intent, REQUEST_SEARCH);
//            }
//        });
        initdata();

        myAdapter = new MyAdapter(list);
        gridView.setAdapter(myAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mBluetoothConnected) {
                    switch (i) {
                        case 0:
                            manager.motorText(1);

                            break;
                        case 1:
                            manager.screenShow(1);
                            break;
                        case 2:
                            manager.smartWarnInfo(7, 2, "微克科技");
//                        参数1
//                        1	Incoming calling
//                        2	Missed Call
//                        3	Messages
//                        4	Mail
//                        5	Calendar
//                        6	FaceTime
//                        7	QQ
//                        8	Skype

//                        参数2
//                        0:关 1：开 2:来消息通知

//                        参数3
//                        消息内容
                            break;
                        case 3:
                            manager.rssiTest();
                            break;
                        case 4:
                            manager.buttonClick();
                            break;
                        case 5:
                            manager.getBatteryInfo();

                            break;
                        case 6:
                            manager.sensorTest();
                            break;
                        case 7:
                            manager.heartRateSensorTest();

                            break;
                        case 8:
                            if (!isTestHR) {
                                Log.i("zgy", "实时测量打开");
                                // manager.realTimeAndOnceMeasure(0x0A, 1);//实时测量
                                isTestHR = true;
                                manager.realTimeAndOnceMeasure(0x80, 1);//实时测量
                                view.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            } else {
                                Log.i("zgy", "实时测量关闭");
                                //   manager.realTimeAndOnceMeasure(0x0A, 0);//实时测量
                                manager.realTimeAndOnceMeasure(0x80, 0);//实时测量
                                isTestHR = false;
                                view.setBackgroundColor(Color.TRANSPARENT);
                            }

                            break;
                        case 9:
                            AlertDialog.Builder builder = new AlertDialog.Builder(ShouHuanMainActivity.this);
                            builder.setTitle("警告");
                            builder.setMessage("确定要清除数据吗?");
                            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    manager.setClearData();

                                }
                            });
                            builder.show();
                            break;
                        case 10:
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(ShouHuanMainActivity.this);
                            builder2.setTitle("警告");
                            builder2.setMessage("确定要恢复出厂设置吗?");
                            builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    manager.setClearData();
                                    manager.Shutdown();

                                }
                            });
                            builder2.show();

                            break;
                        case 11:

                            manager.setSyncData(System.currentTimeMillis() - 3 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 10 * 24 * 60 * 60 * 1000);
                            break;
                        case 12://睡眠数据
                            manager.setSyncSleepData(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
//                            if (!isTestDCXL) {
//                                manager.setOnceOrRealTimeMeasure(0X09, 1);
//                                isTestDCXL = true;
//                            } else {
//                                manager.setOnceOrRealTimeMeasure(0X09, 0);
//                                isTestDCXL = false;
//                            }

                            //      manager.setSyncSleepData(System.currentTimeMillis());
                            // manager.setSyncSleepData(System.currentTimeMillis()- 1 * 24 * 60 * 60 * 1000);
                            break;
//                        case 13://实时心率
//                            if (!isTestSSXL) {
//                                manager.setOnceOrRealTimeMeasure(0X0A, 1);
//                                isTestSSXL = true;
//                            } else {
//                                manager.setOnceOrRealTimeMeasure(0X0A, 0);
//                                isTestSSXL = false;
//                            }
//                            break;
                        case 13:

                            Calendar calendar = Calendar.getInstance();
//获取系统的日期
//年
                            int year = calendar.get(Calendar.YEAR);
//月
                            int month = calendar.get(Calendar.MONTH) + 1;
//日
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
//获取系统时间
//小时
                            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//分钟
                            int minute = calendar.get(Calendar.MINUTE);
//秒
                            int second = calendar.get(Calendar.SECOND);

                          //  time2.setText("Calendar获取当前日期" + year + "年" + month + "月" + day + "日" + hour + ":" + minute + ":" + second);

                            List<HeartRate> heartRateList = heartRateHelper.queryByDay(TimeUtils.date2TimeStamp(year+"-"+month+"-"+day,"yyyy-MM-dd"));
                          //  LogUtil.i(TimeUtils.date2TimeStamp(year+"-"+month+"-"+day+" 00:00:00","yyyy-MM-dd HH:mm:ss")+"");
                            LogUtil.i(heartRateList.toString());

                            break;
                        default:
                            break;
                    }
                } else {
                    Toast.makeText(ShouHuanMainActivity.this, "手环未连接", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("zgy", "onResume");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());


    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!MyApplication.mBluetoothConnected) {
            menu.findItem(R.id.disconnect_ble).setVisible(false);
        } else {
            menu.findItem(R.id.disconnect_ble).setVisible(true);
        }
        if (!MyApplication.isBluetoothConnecting) {
            menu.findItem(R.id.menu_refresh).setActionView(
                    null);
        } else {
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);//正在连接
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_ble:
                Intent intent = new Intent(ShouHuanMainActivity.this, DeviceScanActivity.class);
                startActivityForResult(intent, REQUEST_SEARCH);
                break;
            case R.id.disconnect_ble:
                try {
                    MyApplication.mBluetoothLeService.disconnect();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SEARCH && resultCode == RESULT_OK) {
            address = data.getStringExtra(Constants.ADDRESS);

            name = data.getStringExtra(Constants.NAME);
            SPUtils.setString(Constants.ADDRESS, address);
            SPUtils.setString(Constants.NAME, name);
            if (!TextUtils.isEmpty(address)) {
                try {
                    MyApplication.mBluetoothLeService.connect(address);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                MyApplication.isBluetoothConnecting = true;
                invalidateOptionsMenu();//显示正在连接 ...
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    //接收蓝牙状态改变的广播
    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = true;
                MyApplication.isBluetoothConnecting = false;
                //todo 更改界面ui
                device_address.setText(address);
                device_name.setText(name);
                invalidateOptionsMenu();//更新菜单栏
                Log.d("BluetoothLeService", "连上");
                tv_search.setText("断开");

            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = false;
                //todo 更改界面ui
                device_address.setText("未连接");

                device_name.setText("");
                invalidateOptionsMenu();//更新菜单栏
                try {
                    MyApplication.mBluetoothLeService.close();//断开更彻底(没有这一句，在某些机型，重连会连不上)
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.d("BluetoothLeService", "断开");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
//                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
//                displayData(intent.getStringExtra(BluetoothLeService.EXTRA_DATA));
//                Log.i("zgy", "接收到的数据：" + intent.getStringExtra(BluetoothLeService.EXTRA_DATA));

                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                Log.i("BluetoothLeService", "接收的数据：" + DataHandlerUtils.bytesToHexStr(txValue));

                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);

                //    Log.i("zgy", datas.toString());
                //LogUtil.i(datas.toString());
                if (datas.get(4) == 0x52){
                    LogUtil.i(datas.toString());
                }
                //RSSI
                if (datas.get(4) == 0XB5) {// [171, 0, 4, 255, 181, 128, 72]
                    Integer rssi = datas.get(6);
                    Log.d("zgy", "RSSI" + rssi);
                    test_result.setText("-" + rssi);
                }

                //按键测试
                if (datas.get(4) == 0XB6) {//[171, 0, 4, 255, 182, 128, 0]
                    Integer button = datas.get(6);
                    if (button == 0) {
                        test_result.setText("没有按下");
                    } else if (button == 1) {
                        test_result.setText("按下");

                    }

                }
                //充电 、电量
                if (datas.get(4) == 0X91) {//[171, 0, 5, 255, 145, 128, 0, 100]
                    Integer integer = datas.get(6);//是否充电
                    Integer integer1 = datas.get(7);//电量多少
                    if (integer == 0) {
                        test_result.setText("未充电  " + "电量:" + integer1 + "%");
                    } else if (integer == 1) {
                        test_result.setText("正在充电  " + "电量:" + integer1 + "%");
                    }
                }

                //三轴传感器
                if (datas.get(4) == 0XB3) {//[171, 0, 5, 255, 179, 128, 1, 1]
                    Integer integer1 = datas.get(6);//通信是否正常
                    Integer integer2 = datas.get(7);//初始化是否成功
                    if (integer1 == 0) {
                        if (integer2 == 0) {
                            test_result.setText("通信不正常  " + "初始化不成功");
                        } else if (integer2 == 1) {
                            test_result.setText("通信不正常  " + "初始化成功");

                        }
                    } else if (integer1 == 1) {
                        if (integer2 == 0) {
                            test_result.setText("通信正常  " + "初始化不成功");

                        } else if (integer2 == 1) {
                            test_result.setText("通信正常  " + "初始化成功");

                        }
                    }

                }

                //心率传感器
                if (datas.get(4) == 0XB4) {//[171, 0, 4, 255, 180, 128, 1]
                    Integer integer = datas.get(6);
                    if (integer == 0) {
                        test_result.setText("通信不正常");

                    } else if (integer == 1) {
                        test_result.setText("通信正常");

                    }

                }
                //测量心率
                if (datas.get(4) == 0x84) {//[171, 0, 5, 255, 49, 10, 0, 190]   [171, 0, 5, 255, 49, 10, 84, 48]


                    Integer integer = datas.get(6);
                    test_result.setText(String.valueOf(integer));
                }
                if (datas.get(4) == 0x51 && datas.size() == 13) {//[171, 0, 10, 255, 81, 17, 18, 5, 19, 4, 35, 62, 62]//拉取心率数据
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + datas.get(10) + ":" + "00";
                    HeartRate heartRate = new HeartRate();
                    heartRate.setDate(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    heartRate.setHeartRate(datas.get(11));
                    LogUtil.i(heartRate.toString());
                    heartRateHelper.insert(heartRate);

                }
                if(datas.get(4) == 0x51 && datas.get(5)==17){
                    LogUtil.i(datas.toString());
                }
                if (datas.get(4) == 0x51 && datas.get(5)==8){
                    LogUtil.i(datas.toString());
            }

            }
        }
    };


    private void initdata() {
        list = new ArrayList<>();
        list.add(getString(R.string.find_braclet));
        list.add(getString(R.string.screen_show));
        list.add(getString(R.string.text));
        list.add(getString(R.string.rssi));
        list.add(getString(R.string.button));
        list.add(getString(R.string.battery));
        list.add(getString(R.string.three_six));
        list.add(getString(R.string.heart_senor));
        list.add(getString(R.string.heart_test));
        list.add(getString(R.string.clear_data));
        list.add(getString(R.string.restore));
        list.add(getString(R.string.pull_to_refresh));
        list.add("睡眠数据");
        list.add("查看数据");
//        list.add(getString(R.string.hr_single_measurement));
//        list.add(getString(R.string.hr_real_time_measurement));
//        list.add(getString(R.string.oxygen_single_measurement));
//        list.add(getString(R.string.oxygen_real_time_measurement));
//        list.add(getString(R.string.bp_single_measurement));
//        list.add(getString(R.string.bp_real_time_measurement));
    }


    class MyAdapter extends BaseAdapter {
        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
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
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(ShouHuanMainActivity.this).inflate(R.layout.channel_item, null);
                viewHolder.text = (TextView) convertView.findViewById(R.id.channel_text);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.text.setText(list.get(i));
            return convertView;
        }
    }

    class ViewHolder {
        TextView text;
    }

}

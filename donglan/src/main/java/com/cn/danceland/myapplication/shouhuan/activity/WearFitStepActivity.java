package com.cn.danceland.myapplication.shouhuan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.WearFitStepBean;
import com.cn.danceland.myapplication.db.WearFitStepHelper;
import com.cn.danceland.myapplication.shouhuan.adapter.StepAdapter;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRatePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.MorePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepLastBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepListBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepResultBean;
import com.cn.danceland.myapplication.shouhuan.bean.WearFitUser;
import com.cn.danceland.myapplication.shouhuan.chart.BarEntity;
import com.cn.danceland.myapplication.shouhuan.chart.BarGroup;
import com.cn.danceland.myapplication.shouhuan.chart.BarView;
import com.cn.danceland.myapplication.shouhuan.chart.SourceEntity;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.HorizontalPickerView;
import com.cn.danceland.myapplication.view.NoScrollListView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 计步
 * Created by yxx on 2018/8/13.
 */

public class WearFitStepActivity extends Activity {
    private static final int MSG_REFRESH_DATA = 0;//日
    private static final int MSG_REFRESH_DATA_MORE_DATA = 1;//周 月
    private int lableType = 1;//切换标签 1天  2周  3月
    private Context context;
    private DongLanTitleView title;//数据title
    private LinearLayout statistical_layout;//统计
    private CheckBox day_checkBox;//日
    private CheckBox week_checkBox;//周
    private CheckBox month_checkBox;//年
    private HorizontalPickerView picker;//水平选择器
    private LinearLayout day_layout;
    private LinearLayout more_layout;
    private NoScrollListView listview;
    private BarGroup barGroup;
    private TextView km_tv;
    private TextView step_tv;
    private TextView kilocalorie_tv;
    private TextView average_daily_step_tv;
    private TextView standard_days_tv;
    private ProgressBar progressb_target;

    private ArrayList<String> pickerList = new ArrayList<>();//选择器数据

    private List<String> dayPickerList = new ArrayList<>();//日选择器对应的时间戳  开始-截止  -连接
    private List<String> weekPickerList = new ArrayList<>();//周选择器对应的时间戳  开始-截止  -连接
    private List<String> monthPickerList = new ArrayList<>();//月选择器对应的时间戳  开始-截止  -连接
    private List<WearFitStepBean> wearFitDataBeanList = new ArrayList<>();//本地数据库和后台共用模式
    private List<StepBean> stepBeans = new ArrayList<>();//本地数据库和后台共用模式
    private List<StepResultBean> behindData = new ArrayList<>();//最后一条后面所有的数据 ALL
    private WearFitStepHelper stepHelper = new WearFitStepHelper();

    private WearFitUser wearFitUser = new WearFitUser();//本地用户数据
    private StepAdapter adapter;

    private StepResultBean lastDateTime;//服务器最后睡眠
    private String lastData = "";//选择器上次滚动的数据

    /*柱状图的最大值*/
    private float sourceMax = 0.00f;
    private int left;
    private int baseLineHeiht;
    private RelativeLayout.LayoutParams lp;
    private List<SourceEntity.Source> moreList = new ArrayList<>();
    private HorizontalScrollView root;
    private View popView;
    private PopupWindow popupWindow;
    private DecimalFormat mFormat = new DecimalFormat("##.####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        context = this;
        wearFitUser = (WearFitUser) DataInfoCache.loadOneCache(Constants.MY_WEAR_FIT_SETTING);//手环设置
        initView();

    }

    private void initView() {
        title = findViewById(R.id.shouhuan_title);
        title.setTitle(context.getResources().getString(R.string.step_gauge_text));
        statistical_layout = (LinearLayout) findViewById(R.id.statistical_layout);
        day_checkBox = (CheckBox) findViewById(R.id.day_checkBox);//日
        week_checkBox = (CheckBox) findViewById(R.id.week_checkBox);//周
        month_checkBox = (CheckBox) findViewById(R.id.month_checkBox);//年
        picker = (HorizontalPickerView) findViewById(R.id.scrollPicker);//水平选择器
        day_layout = (LinearLayout) findViewById(R.id.day_layout);
        more_layout = (LinearLayout) findViewById(R.id.more_layout);
        listview = (NoScrollListView) findViewById(R.id.listview);
        barGroup = (BarGroup) findViewById(R.id.bar_group);
        root = (HorizontalScrollView) findViewById(R.id.bar_scroll);
        km_tv = (TextView) findViewById(R.id.km_tv);
        step_tv = (TextView) findViewById(R.id.step_tv);
        kilocalorie_tv = (TextView) findViewById(R.id.kilocalorie_tv);
        average_daily_step_tv = (TextView) findViewById(R.id.average_daily_step_tv);
        standard_days_tv = (TextView) findViewById(R.id.standard_days_tv);
        progressb_target = (ProgressBar) findViewById(R.id.progressb_target);

        popView = LayoutInflater.from(context).inflate(
                R.layout.pop_bg, null);

        day_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        week_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        month_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);

        picker.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    String lableText = picker.getSelectedString();//得到触摸值
                    int lableSelectNum = picker.getSelectNum();//得到触摸位置
                    if (lastData == "") {
                        lastData = picker.getSelectedString();//第一次赋值
                    }
                    if (!lastData.equals(lableText)) {//最后一次和本次滚动的值不相等请求
                        String[] temp = null;
                        setViewClear();
                        switch (lableType) {//切换标签 1天  2周  3月
                            case 1:
                                String[] splitDay = null;
                                splitDay = lableText.split("\\.");
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.MONTH, Integer.valueOf(splitDay[0]));
                                calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(splitDay[1]));
                                int year = calendar.get(Calendar.YEAR);
                                int month = calendar.get(Calendar.MONTH);
                                int day = calendar.get(Calendar.DAY_OF_MONTH);
                                LogUtil.i(year + "-" + month + "-" + day);
                                queryDataByDay(year + "", month + "", day + "");
                                break;
                            case 2:
                                temp = weekPickerList.get(lableSelectNum).split("&");
                                break;
                            case 3:
                                temp = monthPickerList.get(lableSelectNum).split("&");
                                break;
                        }
                        if (temp != null && temp.length == 2) {
                            LogUtil.i("上传参数1" + TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "&" + TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")));
                            LogUtil.i("上传参数" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + ""))).toString() + "&"
                                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtils.timeToTopHour(Long.valueOf(temp[1] + ""))).toString());
                            querysDataByWeekOrMonth(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "", TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")) + "");
                        }
                    }
                    lastData = picker.getSelectedString();
                }
                return false;
            }
        });

        initPickerDay();//默认日数据
        setViewClear();
        defaultQueryStepByDay();
        getLastData();//服务器最后数据
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            setViewClear();
            switch (compoundButton.getId()) {
                case R.id.day_checkBox://日
                    day_layout.setVisibility(View.VISIBLE);
                    more_layout.setVisibility(View.GONE);
                    initPickerDay();
                    lableType = 1;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.week_checkBox://周
                    day_layout.setVisibility(View.GONE);
                    more_layout.setVisibility(View.VISIBLE);
                    initPickerWeek();
                    lableType = 2;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.month_checkBox://月
                    day_layout.setVisibility(View.GONE);
                    more_layout.setVisibility(View.VISIBLE);
                    initPickerMonth();
                    lableType = 3;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    break;
            }
            getData();
        }
    };

    private void getData() {
        String[] temp = null;
        switch (lableType) {//切换标签 1天  2周  3月
            case 1:
                defaultQueryStepByDay();
                break;
            case 2:
                temp = weekPickerList.get(weekPickerList.size() - 1).split("&");
                break;
            case 3:
                temp = monthPickerList.get(monthPickerList.size() - 1).split("&");
                break;
        }
        if (temp != null && temp.length == 2) {
            LogUtil.i("上传参数" + new SimpleDateFormat("yyyy-MM-dd").format(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + ""))).toString() + "&"
                    + new SimpleDateFormat("yyyy-MM-dd").format(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + ""))).toString());
            querysDataByWeekOrMonth(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "", TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")) + "");
        }
    }

    /**
     * 初始化选择器 日数据
     */
    private void initPickerDay() {
        pickerList.clear();
        dayPickerList = new ArrayList<>();//日
        dayPickerList = TimeUtils.getLastMonthDayData();//日
        if (dayPickerList != null && dayPickerList.size() != 0) {
            for (int i = 0; i < dayPickerList.size(); i++) {
                pickerList.add(new SimpleDateFormat("M.d").format(new Date(dayPickerList.get(i))).toString());
            }
        }
        picker.setData(pickerList);
        picker.setSelectNum(pickerList.size() - 1);//时间选择器偏移
    }

    /**
     * 初始化选择器 周数据
     */
    private void initPickerWeek() {
        pickerList.clear();
        weekPickerList = new ArrayList<>();//周
        weekPickerList = TimeUtils.getWeekListData(160);//周  大概三年
        if (weekPickerList != null && weekPickerList.size() != 0) {
            for (int i = 0; i < weekPickerList.size(); i++) {
                String[] temp = null;
                temp = weekPickerList.get(i).split("&");
                if (temp != null && temp.length == 2) {
                    String wStr = new SimpleDateFormat("M.d").format(Long.valueOf(temp[0])).toString() + "-"
                            + new SimpleDateFormat("M.d").format(Long.valueOf(temp[1])).toString();
                    pickerList.add(wStr);
                }
            }
        }
        picker.setData(pickerList);
        picker.setSelectNum(pickerList.size() - 1);//时间选择器偏移
    }

    /**
     * 初始化选择器 月数据
     */
    private void initPickerMonth() {
        pickerList.clear();
        int num = 36;//36个月  3年
        monthPickerList = new ArrayList<>();//周
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < num; i++) {
            pickerList.add(calendar.get(Calendar.YEAR) + "." + (calendar.get(Calendar.MONTH) + 1));
            String times = TimeUtils.getMonthFirstDay(calendar) + "&" + TimeUtils.getMonthLastDay(calendar);
            monthPickerList.add(times);//开始与截止的时间戳
            calendar.add(Calendar.MONTH, -1);
        }
        Collections.reverse(pickerList);
        Collections.reverse(monthPickerList);
        picker.setData(pickerList);
        picker.setSelectNum(pickerList.size() - 1);//时间选择器偏移
    }

    //清除布局值
    private void setViewClear() {
        if (adapter != null) {
            adapter.clear();
        }
        stepBeans.clear();
        wearFitDataBeanList.clear();
        moreList.clear();
        barGroup.removeAllViews();
        km_tv.setText(context.getResources().getString(R.string.sleep_line_text));
        step_tv.setText(context.getResources().getString(R.string.sleep_line_text));
        kilocalorie_tv.setText(context.getResources().getString(R.string.sleep_line_text));
        average_daily_step_tv.setText(context.getResources().getString(R.string.sleep_line_text));
        standard_days_tv.setText(context.getResources().getString(R.string.sleep_line_text));
        if (lableType == 1) {   //----日布局
            progressb_target.setProgress(0);
            day_layout.setVisibility(View.VISIBLE);
            more_layout.setVisibility(View.GONE);
        } else { //----周月布局
            day_layout.setVisibility(View.GONE);
            more_layout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 查询睡眠 日
     *
     * @param year
     * @param month
     * @param day
     */
    public void queryDataByDay(String year, String month, String day) {
        wearFitDataBeanList = stepHelper.queryByDay(TimeUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));//获取本地数据库数据
        LogUtil.i("本地数据库共有个" + wearFitDataBeanList.size());
        if (wearFitDataBeanList != null && wearFitDataBeanList.size() != 0) {
            initViewToData();
        } else {
            HeartRatePostBean heartRatePostBean = new HeartRatePostBean();
            heartRatePostBean.setYear(year);
            heartRatePostBean.setMonth(month);
            heartRatePostBean.setDay(day);
            LogUtil.i("请求后台数据" + heartRatePostBean.toString());
            //获取后台数据
            MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_STEP_LIST)
                    , new Gson().toJson(heartRatePostBean), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    LogUtil.i("jsonObject" + jsonObject.toString());
                    if (jsonObject.toString().contains("true")) {
                        StepListBean stepListBean = new Gson().fromJson(jsonObject.toString(), StepListBean.class);
                        List<StepResultBean> data = stepListBean.getData();
                        if (data != null && data.size() != 0) {
                            LogUtil.i("data.size()" + data.size());
                            wearFitDataBeanList.clear();
                            for (int i = 0; i < data.size(); i++) {
                                String time = data.get(i).getYear() + "-" + data.get(i).getMonth() + "-" + data.get(i).getDay() + " "
                                        + data.get(i).getHour() + ":" + data.get(i).getMinute() + ":" + "00";
                                WearFitStepBean wfsb = new WearFitStepBean();
//                                wfsb.setTimestamp(TimeUtils.date2TimeStamp(time, "yyyy-MM-dd HH:mm:ss"));
                                wfsb.setTimestamp(data.get(i).getTimestamp());
                                wfsb.setStep(Integer.valueOf(data.get(i).getStep()));
                                wfsb.setCal(Integer.valueOf(data.get(i).getCal()));//睡了多久 12位*256+13位
                                wearFitDataBeanList.add(wfsb);
                            }
                        }
                        Message message = new Message();
                        message.what = MSG_REFRESH_DATA;
                        handler.sendMessage(message);
                    } else {
                        ToastUtils.showToastShort("请查看网络连接");
                        Message message = new Message();
                        message.what = MSG_REFRESH_DATA;
                        handler.sendMessage(message);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                    LogUtil.e("onErrorResponse", volleyError.toString());
                }
            }) ;
            MyApplication.getHttpQueues().add(request);
        }
    }

    /**
     * 查询数据 周 月
     *
     * @param timestamp_gt 开始时间
     * @param timestamp_lt 截止时间
     */
    private void querysDataByWeekOrMonth(String timestamp_gt, String timestamp_lt) {
        MorePostBean weekPostBean = new MorePostBean();
        weekPostBean.setTimestamp_gt(timestamp_gt);
        weekPostBean.setTimestamp_lt(timestamp_lt);
        LogUtil.i("请求后台数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(timestamp_gt))) + "-"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(timestamp_lt))));
        //获取后台数据
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_STEP_FINDMAX)
                , new Gson().toJson(weekPostBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("jsonObject" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    StepListBean stepListBean = new Gson().fromJson(jsonObject.toString(), StepListBean.class);
                    List<StepResultBean> data = stepListBean.getData();
                    if (data != null && data.size() != 0) {
                        LogUtil.i("data.size()" + data.size());
                        for (int i = 0; i < data.size(); i++) {

                            SourceEntity.Source source = new SourceEntity.Source();
                            source.setAwakeCount(0);//清醒
                            source.setShallowCount(Integer.valueOf(data.get(i).getStep()));//浅睡 步数
                            source.setCal(Integer.valueOf(data.get(i).getCal()));//cal
                            LogUtil.i("步数" + data.get(i).getStep());
                            source.setDeepCount(0);//深睡
                            source.setDayAwake(10);
                            source.setScale(100);
                            source.setTime(data.get(i).getTimestamp());
                            String weekStr = TimeUtils.timeStamp2Date(data.get(i).getTimestamp() + "", "HH");
                            switch (lableType) {//切换标签 1天  2周  3月
                                case 2:
                                    weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(data.get(i).getTimestamp() + "", "yyyy-MM-dd"));
                                    break;
                                case 3:
                                    weekStr = new SimpleDateFormat("d").format(new Date(Long.valueOf(data.get(i).getTimestamp()))).toString();
                                    break;
                            }
                            source.setSource(weekStr);
                            source.setAllCount(source.getAwakeCount() + source.getShallowCount() + source.getDeepCount());
                            moreList.add(source);
                        }
                    }
                    Message message = new Message();
                    message.what = MSG_REFRESH_DATA_MORE_DATA;
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                    Message message = new Message();
                    message.what = MSG_REFRESH_DATA_MORE_DATA;
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message message = new Message();
                message.what = MSG_REFRESH_DATA_MORE_DATA;
                handler.sendMessage(message);
                ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    private void defaultQueryStepByDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtil.i(year + "-" + month + "-" + day);
        queryDataByDay(year + "", month + "", day + "");
    }

    private void initViewToData() {
        List<StepBean> stepListTemp = new ArrayList<>();//本地数据库和后台共用模式
        for (int i = 0; i < wearFitDataBeanList.size(); i++) {
            if (i == 0) {//第一条
                StepBean stepBean = new StepBean();
                stepBean.setStartTime(wearFitDataBeanList.get(i).getTimestamp());
                stepBean.setEndTime(0);
                stepBean.setStep(wearFitDataBeanList.get(i).getStep());
                stepBean.setCal(wearFitDataBeanList.get(i).getCal());
                stepListTemp.add(stepBean);
            } else if (i == wearFitDataBeanList.size() - 1) {//最后一条
                if (stepListTemp.get(stepListTemp.size() - 1).getEndTime() == 0) {//更改最后一条截止时间
                    StepBean stepB = stepListTemp.get(stepListTemp.size() - 1);
                    stepB.setEndTime(wearFitDataBeanList.get(i).getTimestamp());
                    stepListTemp.set(stepListTemp.size() - 1, stepB);
                }
            } else {
                //stepListTemp最后一条没有截止时间，并且步数和wearFitDataBeanList步数一样
                if (stepListTemp.get(stepListTemp.size() - 1).getEndTime() == 0) {//更改最后一条截止时间
                    StepBean stepB = stepListTemp.get(stepListTemp.size() - 1);
                    stepB.setEndTime(wearFitDataBeanList.get(i).getTimestamp());
                    stepListTemp.set(stepListTemp.size() - 1, stepB);
                }
                if (stepListTemp.get(stepListTemp.size() - 1).getStep() != wearFitDataBeanList.get(i).getStep()) {
                    StepBean stepBean2 = new StepBean();
                    stepBean2.setStartTime(wearFitDataBeanList.get(i).getTimestamp());
                    stepBean2.setEndTime(0);
                    stepBean2.setStep(wearFitDataBeanList.get(i).getStep());
                    stepBean2.setCal(wearFitDataBeanList.get(i).getCal());
                    stepListTemp.add(stepBean2);
                }
            }
        }
        for (int i = 0; i < stepListTemp.size(); i++) {
            if (i == 0) {//第一条
                stepBeans.add(stepListTemp.get(i));
            } else {
                StepBean lastStep = stepListTemp.get(i);
                int step = stepListTemp.get(i).getStep() - stepListTemp.get(i - 1).getStep();
                int cal = stepListTemp.get(i).getCal() - stepListTemp.get(i - 1).getCal();
                StepBean stepBean = new StepBean();
                stepBean.setStep(step);
                stepBean.setStartTime(lastStep.getStartTime());
                stepBean.setEndTime(lastStep.getEndTime());
                if (cal > 0) {
                    stepBean.setCal(cal);
                } else {
                    stepBean.setCal(stepListTemp.get(i).getCal());
                }
                stepBeans.add(stepBean);
            }
        }
        Collections.reverse(stepBeans);

        initChildView1();
        addChildLayout();
        int stepLength = 50;//默认步长
        if (wearFitUser != null) {
            stepLength = wearFitUser.getStepLength();
        }
        adapter = new StepAdapter(context, stepBeans, stepLength);
        listview.setAdapter(adapter);
    }

    private void initChildView1() {
        if (wearFitDataBeanList != null && wearFitDataBeanList.size() != 0) {
            float kmf = (float) 50 * (float) wearFitDataBeanList.get(wearFitDataBeanList.size() - 1).getStep() / (float) 100000.00;//100步长  1000km
            int step = wearFitDataBeanList.get(wearFitDataBeanList.size() - 1).getStep();
            int cal = wearFitDataBeanList.get(wearFitDataBeanList.size() - 1).getCal();
            DecimalFormat fnum = new DecimalFormat("##0.00");
            NumberFormat numberFormat = NumberFormat.getInstance();// 创建一个数值格式化对象
            numberFormat.setMaximumFractionDigits(0);// 设置精确到小数点后2位
            String targetStr = numberFormat.format((float) step / (float) 8000 * 100);//达标
            if (wearFitUser != null) {
                kmf = (float) wearFitUser.getStepLength() * (float) wearFitDataBeanList.get(wearFitDataBeanList.size() - 1).getStep() / (float) 100000.00;//100步长  1000km
                targetStr = numberFormat.format((float) step / (float) wearFitUser.getGold_steps() * 100);//达标
            }
            String km = fnum.format(kmf);
            int target = 0;
            if (StringUtils.isNumeric(targetStr)) {
                target = Integer.valueOf(targetStr);
            }
            km_tv.setText(km + "");
            step_tv.setText(step + "");
            kilocalorie_tv.setText(cal + "");
            //----日布局
            progressb_target.setProgress(target);
            day_layout.setVisibility(View.VISIBLE);
            more_layout.setVisibility(View.GONE);
        }
    }

    private void initChildView2() {
        int sumStep = 0;//总步数
        int sumCal = 0;//cal
        int standard = 0;//达标天数
        for (int i = 0; i < moreList.size(); i++) {
            sumStep += moreList.get(i).getShallowCount();//浅睡 步数
            sumCal += moreList.get(i).getCal();// cal
            if (moreList.get(i).getShallowCount() >= wearFitUser.getGold_steps()) {
                ++standard;
            }
        }
        float kmf = (float) wearFitUser.getStepLength() * (float) sumStep / (float) 100000.00;//100步长  1000km
        DecimalFormat fnum = new DecimalFormat("##0.00");
        String km = fnum.format(kmf);
        km_tv.setText(km + "");
        step_tv.setText(sumStep + "");
        kilocalorie_tv.setText(sumCal + "");
        //----周月布局
        average_daily_step_tv.setText(sumStep / moreList.size() + "");//日均步数
        standard_days_tv.setText(standard + "");//达标天数
        day_layout.setVisibility(View.GONE);
        more_layout.setVisibility(View.VISIBLE);
    }

    private void addChildLayout() {
        for (int i = 0; i < stepBeans.size(); i++) {
            SourceEntity.Source source = new SourceEntity.Source();
            source.setAwakeCount(0);//清醒
            source.setShallowCount(stepBeans.get(i).getStep());//浅睡 步数
            LogUtil.i("步数" + stepBeans.get(i).getStep());
            source.setDeepCount(0);//深睡
            source.setDayAwake(10);
            source.setScale(100);
            source.setTime(stepBeans.get(i).getStartTime());
            String weekStr = TimeUtils.timeStamp2Date(stepBeans.get(i).getStartTime() + "", "HH");
            weekStr = TimeUtils.timeStamp2Date(stepBeans.get(i).getStartTime() + "", "HH");
            source.setSource(weekStr);
            if (weekStr.equals("星期二")) {
                LogUtil.i("&&&&&-" + stepBeans.get(i).getState() + "-&&-"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(stepBeans.get(i).getStartTime()))));
            }
            source.setAllCount(source.getAwakeCount() + source.getShallowCount() + source.getDeepCount());
            moreList.add(source);
        }
        Collections.reverse(moreList);
        if (moreList != null && moreList.size() != 0) {
            setBarChart();
        }
    }

    public void setBarChart() {
        final SourceEntity sourceEntity = new SourceEntity();
//        sourceEntity.parseData();
        sourceEntity.setList(moreList);
        setYAxis(sourceEntity.getList());

        barGroup.removeAllViews();
        List<BarEntity> datas = new ArrayList<>();
        final int size = sourceEntity.getList().size();
        for (int i = 0; i < size; i++) {
            BarEntity barEntity = new BarEntity();
            SourceEntity.Source entity = sourceEntity.getList().get(i);
            String negative = mFormat.format(entity.getAwakeCount() / sourceMax);
            barEntity.setNegativePer(Float.parseFloat(negative));
            String neutral = mFormat.format(entity.getDeepCount() / sourceMax);
            barEntity.setNeutralPer(Float.parseFloat(neutral));
            String positive = mFormat.format(entity.getShallowCount() / sourceMax);
            barEntity.setPositivePer(Float.parseFloat(positive));
            barEntity.setTitle(entity.getSource());
            barEntity.setScale(entity.getScale());
            barEntity.setAllcount(entity.getAllCount());
            /*计算柱状图透明区域的比例*/
            barEntity.setFillScale(1 - entity.getAllCount() / sourceMax);
            datas.add(barEntity);
        }
        barGroup.setDatas(datas);
        //计算间距
        barGroup.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                barGroup.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = findViewById(R.id.bg).getMeasuredHeight();
                final View baseLineView = findViewById(R.id.left_base_line);
                int baseLineTop = baseLineView.getTop();
                int width = 15;
                switch (lableType) {//切换标签 1天  2周  3月
                    case 1:
                        width = 15;
                        break;
                    case 2:
                        width = 30;
                        break;
                    case 3:
                        width = 30;
                        break;
                }
                barGroup.setHeight(sourceMax, height - baseLineTop - baseLineView.getHeight() / 2, width);
                barGroup.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        final BarView barItem = (BarView) barGroup.getChildAt(0).findViewById(R.id.barView);
                        baseLineHeiht = findViewById(R.id.base_line).getTop();
                        lp = (RelativeLayout.LayoutParams) root.getLayoutParams();
                        left = baseLineView.getLeft();
                        lp.leftMargin = (int) (left + context.getResources().getDisplayMetrics().density * 3);
                        lp.topMargin = Math.abs(baseLineHeiht - barItem.getHeight());
                        root.setLayoutParams(lp);
//                        final int initHeight = barItem.getHeight();
//                        final ObjectAnimator anim = ObjectAnimator.ofFloat(barItem, "alpha", 0, 0.5f, 1.0f).setDuration(1500);
//                        final LinearLayout.LayoutParams barLP= (LinearLayout.LayoutParams) barItem.getLayoutParams();
//                        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                            @Override
//                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                                float cVal = (Float) anim.getAnimatedValue();
//                                barLP.height = (int) (initHeight * cVal);
//                                barItem.setLayoutParams(barLP);
//                            }
//                        });
//                        anim.start();
                    }
                }, 0);

                for (int i = 0; i < size; i++) {
                    final BarView barItem = (BarView) barGroup.getChildAt(i).findViewById(R.id.barView);
                    final int finalI = i;
                    barItem.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final float top = view.getHeight() - barItem.getFillHeight();
                            SourceEntity.Source ss = sourceEntity.getList().get(finalI);
                            String showText = new SimpleDateFormat("HH:mm").format(new Date(Long.valueOf(ss.getTime()))).toString() + "\n"
                                    + ss.getShallowCount() + "步\n";
                            switch (lableType) {//切换标签 1天  2周  3月
                                case 1:
                                    showText = new SimpleDateFormat("HH:mm").format(new Date(Long.valueOf(ss.getTime()))).toString() + "\n"
                                            + ss.getShallowCount() + "步\n";
                                    break;
                                case 2:
                                    showText = ss.getSource() + "\n"
                                            + ss.getShallowCount() + "步\n";
                                    break;
                                case 3:
                                    showText = ss.getShallowCount() + "步\n";
                                    break;
                            }
                            ((TextView) popView.findViewById(R.id.txt)).setText(showText);
                            showPop(barItem, top);
                        }
                    });
                }
                return false;
            }
        });
    }

    private void setYAxis(List<SourceEntity.Source> list) {
//        sourceMax = list.get(0).getAllCount();
        switch (lableType) {//切换标签 1天  2周  3月
            case 1:
                sourceMax = 1500;
                break;
            case 2:
                sourceMax = 15000;
                break;
            case 3:
                sourceMax = 15000;
                break;
        }
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getAllCount() > sourceMax) {
                sourceMax = list.get(i).getAllCount();
            }
        }
        ((TextView) findViewById(R.id.tv_num1)).setText((int) sourceMax / 3 + "");
        ((TextView) findViewById(R.id.tv_num2)).setText((int) sourceMax * 2 / 3 + "");
        ((TextView) findViewById(R.id.tv_num3)).setText((int) sourceMax + "");
    }

    private int initPopHeitht = 0;

    @SuppressLint("NewApi")
    private void showPop(final View barItem, final float top) {
        if (popupWindow != null)
            popupWindow.dismiss();
        popupWindow = null;
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(barItem, barItem.getWidth() / 2, -((int) top + initPopHeitht));
        if (initPopHeitht == 0) {
            popView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    popView.getViewTreeObserver().removeOnPreDrawListener(this);
                    initPopHeitht = popView.getHeight();
                    popupWindow.update(barItem, barItem.getWidth() / 2, -((int) top + initPopHeitht),
                            popupWindow.getWidth(), popupWindow.getHeight());
                    return false;
                }
            });
        }
    }

    //服务器最后数据
    private void getLastData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_STEP_FANDLAST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("服务器最后一条数据是" + s);
                StepLastBean lastBean = new Gson().fromJson(s, StepLastBean.class);
                long lastTime = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
                lastDateTime = lastBean.getData();//服务器最后数据
                if (lastDateTime != null) {
                    lastTime = lastDateTime.getTimestamp();
                }

                queryAllSleepByDay(lastTime);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtil.i(volleyError.toString());
                } else {
                    LogUtil.i("NULL");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void queryAllSleepByDay(long date1Time) {//最后一条数据时间  获取本地数据库这时间后的数据
        int day = TimeUtils.differentDaysByMillisecond(date1Time, new Date().getTime());// 计算差多少天
        LogUtil.i("day" + day);
        for (int i = 0; i < day + 1; i++) {
            List<WearFitStepBean> sleepList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date1Time));
            LogUtil.i("data" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + i));
            sleepList = stepHelper.queryByDay(TimeUtils.date2TimeStamp(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + i), "yyyy-MM-dd"));//获取本地数据库数据
            for (int j = 0; j < sleepList.size(); j++) {
                StepResultBean bean = new StepResultBean();//提交对象
                bean.setYear(new SimpleDateFormat("yyyy").format(new Date(sleepList.get(j).getTimestamp())).toString());//年
                bean.setMonth(new SimpleDateFormat("MM").format(new Date(sleepList.get(j).getTimestamp())).toString());//月
                bean.setDay(new SimpleDateFormat("dd").format(new Date(sleepList.get(j).getTimestamp())).toString());//日
                bean.setHour(new SimpleDateFormat("HH").format(new Date(sleepList.get(j).getTimestamp())).toString());//时
                bean.setMinute(new SimpleDateFormat("mm").format(new Date(sleepList.get(j).getTimestamp())).toString());//分
                bean.setTimestamp(sleepList.get(j).getTimestamp());//long 时间戳
                bean.setStep(sleepList.get(j).getStep() + "");//步数
                bean.setCal(sleepList.get(j).getCal() + "");//卡路里
                bean.setFatigue(getFatigueData(Integer.valueOf(new SimpleDateFormat("HH").format(new Date(sleepList.get(j).getTimestamp())).toString())
                        , sleepList.get(i).getStep()) + "");//疲劳共用
                LogUtil.i(bean.toString());
                behindData.add(bean);
            }
        }
        isPostData();
    }

    private int getFatigueData(int hour, int step) {
        int fatigue = 0;
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
        return fatigue;
    }

    /**
     * 提交数据  先请求后台最后一条   对比本地   提交剩下未提交的数据
     */
    private void isPostData() {
        LogUtil.i("behindData" + behindData.size());
        List<StepResultBean> postDataList = new ArrayList<>();
        for (int i = 0; i < behindData.size(); i++) {
            if (lastDateTime != null) {
                if (lastDateTime.getTimestamp() != 0) {//不为空
                    Date date1 = new Date(lastDateTime.getTimestamp());//后台时间
                    Date date2 = new Date(behindData.get(i).getTimestamp());
                    LogUtil.i("比较" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1) + "**"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2));
                    if (date1.before(date2)) { //表示date1小于date2  最后一条数据日期小于手环date2
                        postDataList.add(behindData.get(i));
                    }
                } else {
                    postDataList = behindData;
                }
            } else {
                postDataList = behindData;
            }
        }
        if (postDataList != null && postDataList.size() != 0) {
            LogUtil.i("提交" + postDataList.size());
            postHeart(postDataList);
        }
    }

    private void postHeart(List<StepResultBean> postDataList) {
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_STEP_SAVE)
                , new Gson().toJson(postDataList), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("提交返回" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    LogUtil.i("提交成功");
                } else {
                    LogUtil.i("提交失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_DATA:
                    initViewToData();
                    break;
                case MSG_REFRESH_DATA_MORE_DATA:
                    if (moreList != null && moreList.size() != 0) {
                        setBarChart();
                        initChildView2();
                    }
                    break;
                default:
                    break;
            }
        }
    };
}

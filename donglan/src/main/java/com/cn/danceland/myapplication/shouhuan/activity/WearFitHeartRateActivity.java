package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.HeartRate;
import com.cn.danceland.myapplication.db.HeartRateHelper;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRateBean;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRateLastBean;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRatePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRateResultBean;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRateViewBean;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRateViewPostBean;
import com.cn.danceland.myapplication.shouhuan.bean.MorePostBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.ViewUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.HorizontalPickerView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 心率
 * Created by yxx on 2018/7/18.
 */
public class WearFitHeartRateActivity extends Activity implements View.OnClickListener {
    private int lableType = 1;//切换标签 1天  2周  3月
    private static final int MSG_REFRESH_HEART_DATA = 0;
    private static final int MSG_REFRESH_HEARTVIEW_DATA = 1;
    private Context context;
    private DongLanTitleView heart_title;//心率title
    private CheckBox day_checkBox;//日
    private CheckBox week_checkBox;//周
    private CheckBox month_checkBox;//年
    private TextView heart_success_percentage_tv;//达标率
    private TextView heart_success_count_tv;//达标次数
    private ProgressBar heart_success_pro;//达标率进度条
    private TextView heart_abnormal_percentage_tv;//异常率
    private TextView heart_abnormal_count_tv;//异常次数
    private ProgressBar heart_abnormal_pro;//异常率进度条
    private TextView heart_average_tv;//平均心率
    private LineChartView lineChart; //折线
    private HorizontalPickerView picker;//水平选择器
    private View leftImageView;//选择器左箭头
    private View rightImageView;//选择器右箭头
    private ImageView iv_triangle;//水平标尺 小三角
    private ImageView iv_triangle_mark;//水平标尺

    private List<HeartRate> heartRates = new ArrayList<>();//心率数据 HeartRate
    private ArrayList<String> pickerList = new ArrayList<>();//选择器数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();//折线数据list
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();//折线X轴list
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();//折线Y轴list
    private List<String> dayPickerList = new ArrayList<>();//日选择器对应的时间戳  开始-截止  -连接
    private List<String> weekPickerList = new ArrayList<>();//周选择器对应的时间戳  开始-截止  -连接
    private List<String> monthPickerList = new ArrayList<>();//月选择器对应的时间戳  开始-截止  -连接
    private List<Date> ds = TimeUtils.getSegmentationTime(5, new Date());//默认轴数据
    private List<HeartRateResultBean> behindData = new ArrayList<>();//最后一条后面所有的心率数据 ALL
    private HeartRateViewBean.Data heartViewBean = new HeartRateViewBean.Data();
    private String lastData = "";//选择器上次滚动的数据

    private HeartRateHelper heartRateHelper = new HeartRateHelper();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit_heartrate);
        context = this;
        initView();
    }

    private void initView() {
        heart_title = findViewById(R.id.shouhuan_title);
        heart_title.setTitle(context.getResources().getString(R.string.heart_rate_text));
        day_checkBox = (CheckBox) findViewById(R.id.day_checkBox);//日
        week_checkBox = (CheckBox) findViewById(R.id.week_checkBox);//周
        month_checkBox = (CheckBox) findViewById(R.id.month_checkBox);//年
        lineChart = (LineChartView) findViewById(R.id.line_chart);//折线
        heart_success_percentage_tv = (TextView) findViewById(R.id.heart_success_percentage_tv);//达标率
        heart_success_count_tv = (TextView) findViewById(R.id.heart_success_count_tv);//达标次数
        heart_success_pro = (ProgressBar) findViewById(R.id.heart_success_pro);//达标率进度条
        heart_abnormal_percentage_tv = (TextView) findViewById(R.id.heart_abnormal_percentage_tv);//异常率
        heart_abnormal_count_tv = (TextView) findViewById(R.id.heart_abnormal_count_tv);//异常次数
        heart_abnormal_pro = (ProgressBar) findViewById(R.id.heart_abnormal_pro);//异常率进度条
        heart_average_tv = (TextView) findViewById(R.id.heart_average_tv);//平均心率
        picker = (HorizontalPickerView) findViewById(R.id.scrollPicker);//水平选择器
        iv_triangle = (ImageView) findViewById(R.id.iv_triangle);//水平标尺 小三角
        iv_triangle_mark = (ImageView) findViewById(R.id.iv_triangle_mark);//水平标尺

        day_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        week_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        month_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        setDefaultView();//默认数据
        initPickerDay();//默认日数据

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtil.i(year + "-" + month + "-" + day);
        queryHeartByDay(year + "", month + "", day + "");
        getLastData();//服务器最后数据
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
                        setDefaultView();
                        String[] temp = null;
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
                                queryHeartByDay(year + "", month + "", day + "");
                                break;
                            case 2:
                                temp = weekPickerList.get(lableSelectNum).split("&");
                                break;
                            case 3:
                                temp = monthPickerList.get(lableSelectNum).split("&");
                                break;
                        }
                        if (temp != null && temp.length == 2) {
                            LogUtil.i("上传参数" + temp[0] + "&" + temp[1]);
                            Date dd = new Date();
                            dd.setTime(Long.valueOf(temp[0] + ""));
                            Date dd22 = new Date();
                            dd22.setTime(Long.valueOf(temp[1] + ""));
                            LogUtil.i("上传参数" + new SimpleDateFormat("yyyy-MM-dd").format(dd).toString() + "&"
                                    + new SimpleDateFormat("yyyy-MM-dd").format(dd22).toString());
                            queryHeartByWeekOrMonth(temp[0] + "", temp[1] + "");
                            setHeartViewToService(temp[0] + "", temp[1] + "");
                        }
                    }
                    lastData = picker.getSelectedString();
                }
                return false;
            }
        });
    }

    //默认数据
    private void setDefaultView() {
        heartRates.clear();//心率数据 HeartRate
        mPointValues.clear();
        getAxisXLables();//获取x轴的标注
        getAxisYLables();//获取y轴的标注
        getAxisPoints();//获取坐标点
        initLineChart();//初始化
        heart_success_count_tv.setText(0 + context.getResources().getString(R.string.count_text));
        heart_abnormal_count_tv.setText(0 + context.getResources().getString(R.string.count_text));
        heart_success_percentage_tv.setText(0 + context.getResources().getString(R.string.percentage_text));//达标
        heart_success_pro.setProgress(0);
        heart_abnormal_percentage_tv.setText(0 + context.getResources().getString(R.string.percentage_text));//异常
        heart_abnormal_pro.setProgress(0);
        heart_average_tv.setText(0 + "");//平均心率
        ViewUtils.setMargins(iv_triangle, 0, 0, 0, 0);//默认值
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                picker.setAnLeftOffset();
                break;
            case R.id.iv_right:
                picker.setAnRightOffset();
                break;
            default:
                break;
        }
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.day_checkBox://日
                    initPickerDay();
                    lableType = 1;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.week_checkBox://周
                    initPickerWeek();
                    lableType = 2;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.month_checkBox://月
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
        setDefaultView();
        String[] temp = null;
        switch (lableType) {//切换标签 1天  2周  3月
            case 1:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(dayPickerList.get(dayPickerList.size() - 1)));
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                LogUtil.i(year + "-" + month + "-" + day);
                queryHeartByDay(year + "", month + "", day + "");
                break;
            case 2:
                temp = weekPickerList.get(weekPickerList.size() - 1).split("&");
                break;
            case 3:
                temp = monthPickerList.get(monthPickerList.size() - 1).split("&");
                break;
        }
        if (temp != null && temp.length == 2) {
            LogUtil.i("上传参数" + temp[0] + "&" + temp[1]);
            Date dd = new Date();
            dd.setTime(Long.valueOf(temp[0] + ""));
            Date dd22 = new Date();
            dd22.setTime(Long.valueOf(temp[1] + ""));
            LogUtil.i("上传参数" + new SimpleDateFormat("yyyy-MM-dd").format(dd).toString() + "&"
                    + new SimpleDateFormat("yyyy-MM-dd").format(dd22).toString());
            queryHeartByWeekOrMonth(temp[0] + "", temp[1] + "");
            setHeartViewToService(temp[0] + "", temp[1] + "");
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
//    /**
//     * 初始化选择器 日数据
//     */
//    private void initPickerDay() {
//        pickerList.clear();
//        ArrayList<String> dayDataStr = new ArrayList<>();//日
//        dayDataStr = TimeUtils.getLastMonthDayData(new SimpleDateFormat("M.d"));//日
//        if (dayDataStr != null && dayDataStr.size() != 0) {
//            for (int i = 0; i < dayDataStr.size(); i++) {
//                pickerList.add(dayDataStr.get(i));
//            }
//        }
//        picker.setData(pickerList);
//        picker.setSelectNum(pickerList.size() - 1);//时间选择器偏移
//    }

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
//        LogUtil.i("/"+pickerList.size()/2+"%"+pickerList.size()%2+"aa"+(pickerList.size()-pickerList.size()%2)/2);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private HeartRateResultBean lastDateTime;//服务器最后心率

    //服务器最后数据
    private void getLastData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_HEART_RATE_FANDLAST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("服务器最后一条数据是" + s);
                HeartRateLastBean heartRateLastBean = new Gson().fromJson(s, HeartRateLastBean.class);

                long lastTime = 0;
                if (heartRateLastBean != null) {
                    lastDateTime = heartRateLastBean.getData();//服务器最后心率
                    if(lastDateTime!=null) {
                        lastTime = lastDateTime.getTimestamp();
                    }
                }
                queryAllHeartByDay(lastTime);
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

    private void queryAllHeartByDay(long date1Time) {//最后一条心率数据时间  获取本地数据库这时间后的数据
        int day = TimeUtils.differentDaysByMillisecond(date1Time, new Date().getTime());// 计算差多少天
        for (int i = 0; i < day + 1; i++) {
            List<HeartRate> hrList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date1Time));
            hrList = heartRateHelper.queryByDay(TimeUtils.date2TimeStamp(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + i), "yyyy-MM-dd"));//获取本地数据库心率
            for (int j = 0; j < hrList.size(); j++) {
                HeartRateResultBean heartRateResultBean = new HeartRateResultBean();//提交对象
                heartRateResultBean.setYear(new SimpleDateFormat("yyyy").format(new Date(hrList.get(j).getDate())).toString());//年
                heartRateResultBean.setMonth(new SimpleDateFormat("MM").format(new Date(hrList.get(j).getDate())).toString());//月
                heartRateResultBean.setDay(new SimpleDateFormat("dd").format(new Date(hrList.get(j).getDate())).toString());//日
                heartRateResultBean.setHour(new SimpleDateFormat("HH").format(new Date(hrList.get(j).getDate())).toString());//时
                heartRateResultBean.setMinute(new SimpleDateFormat("mm").format(new Date(hrList.get(j).getDate())).toString());//分
                heartRateResultBean.setMax_value(hrList.get(j).getHeartRate() + "");//心率
                heartRateResultBean.setTimestamp(hrList.get(j).getDate());//long 时间戳
                behindData.add(heartRateResultBean);
            }
        }
        isPostHeart();
    }

    /**
     * 提交心率  先请求后台最后一条   对比本地   提交剩下未提交的数据
     */
    private void isPostHeart() {
        List<HeartRateResultBean> postHeartList = new ArrayList<>();
        for (int i = 0; i < behindData.size(); i++) {
            if (lastDateTime != null) {
                if (lastDateTime.getTimestamp() != 0) {//不为空
                    Date date1 = new Date(lastDateTime.getTimestamp());//后台时间
                    Date date2 = new Date(behindData.get(i).getTimestamp());
                    LogUtil.i("比较" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1) + "**"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2));
                    if (date1.before(date2)) { //表示date1小于date2  最后一条心率日期小于手环date2
                        postHeartList.add(behindData.get(i));
                    }
                } else {
                    postHeartList = behindData;
                }
            } else {
                postHeartList = behindData;
            }
        }
        if (postHeartList != null && postHeartList.size() != 0) {
            LogUtil.i("提交心率" + postHeartList.size());
            postHeart(postHeartList);
        }
    }

    private void postHeart(List<HeartRateResultBean> postHeartList) {
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_HEART_RATE_SAVE)
                , new Gson().toJson(postHeartList), new Response.Listener<JSONObject>() {
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

    /**
     * 查询心率 周 月
     *
     * @param timestamp_gt 开始时间
     * @param timestamp_lt 截止时间
     */
    private void queryHeartByWeekOrMonth(String timestamp_gt, String timestamp_lt) {

        MorePostBean weekPostBean = new MorePostBean();
        weekPostBean.setTimestamp_gt(timestamp_gt);
        weekPostBean.setTimestamp_lt(timestamp_lt);
        LogUtil.i("请求后台心率" + weekPostBean.toString());
        //获取后台数据
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_HEART_RATE_FANDAVG)
                , new Gson().toJson(weekPostBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("jsonObject" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    HeartRateBean heartRateBean = new Gson().fromJson(jsonObject.toString(), HeartRateBean.class);
                    List<HeartRateResultBean> data = heartRateBean.getData();
                    if (data != null && data.size() != 0) {
                        LogUtil.i("data.size()" + data.size());
                        heartRates.clear();
                        for (int i = 0; i < data.size(); i++) {
                            HeartRate heartRate = new HeartRate();
                            heartRate.setDate(data.get(i).getTimestamp());
                            float ff = Float.valueOf(data.get(i).getMax_value());
                            heartRate.setHeartRate((int) ff);
//                            LogUtil.i("数据" + i + "---" + heartRate.toString() + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(data.get(i).getTimestamp())));
                            heartRates.add(heartRate);
                        }
                    }
                    Message message = new Message();
                    message.what = MSG_REFRESH_HEART_DATA;
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort("请查看网络连接");
                    Message message = new Message();
                    message.what = MSG_REFRESH_HEART_DATA;
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message message = new Message();
                message.what = MSG_REFRESH_HEART_DATA;
                handler.sendMessage(message);
                ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 查询心率 日
     *
     * @param year
     * @param month
     * @param day
     */
    private void queryHeartByDay(String year, String month, String day) {
        heartRates = heartRateHelper.queryByDay(TimeUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));//获取本地数据库心率
        LogUtil.i("本地数据库共有个" + heartRates.size());
        if (heartRates != null && heartRates.size() != 0) {
            getAxisXLables();//获取x轴的标注
            getAxisYLables();//获取y轴的标注
            getAxisPoints();//获取坐标点
            initLineChart();//初始化
            setHeartView(heartRates);
        } else {
            HeartRatePostBean heartRatePostBean = new HeartRatePostBean();
            heartRatePostBean.setYear(year);
            heartRatePostBean.setMonth(month);
            heartRatePostBean.setDay(day);
            LogUtil.i("请求后台心率" + heartRatePostBean.toString());
            //获取后台数据
            MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_HEART_RATE_LIST)
                    , new Gson().toJson(heartRatePostBean), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    LogUtil.i("jsonObject" + jsonObject.toString());
                    if (jsonObject.toString().contains("true")) {
                        HeartRateBean heartRateBean = new Gson().fromJson(jsonObject.toString(), HeartRateBean.class);
                        List<HeartRateResultBean> data = heartRateBean.getData();
                        if (data != null && data.size() != 0) {
                            LogUtil.i("data.size()" + data.size());
                            heartRates.clear();
                            for (int i = 0; i < data.size(); i++) {
                                HeartRate heartRate = new HeartRate();
                                String time = data.get(i).getYear() + "-" + data.get(i).getMonth() + "-" + data.get(i).getDay() + " "
                                        + data.get(i).getHour() + ":" + data.get(i).getMinute() + ":" + "00";
                                heartRate.setDate(TimeUtils.date2TimeStamp(time, "yyyy-MM-dd HH:mm:ss"));
                                heartRate.setHeartRate(Integer.valueOf(data.get(i).getMax_value()));
                                heartRates.add(heartRate);
                            }
                        }
                        Message message = new Message();
                        message.what = MSG_REFRESH_HEART_DATA;
                        handler.sendMessage(message);
                    } else {
                        ToastUtils.showToastShort("请查看网络连接");
                        Message message = new Message();
                        message.what = MSG_REFRESH_HEART_DATA;
                        handler.sendMessage(message);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Message message = new Message();
                    message.what = MSG_REFRESH_HEART_DATA;
                    handler.sendMessage(message);
                    ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                    LogUtil.e("onErrorResponse", volleyError.toString());
                }
            }) ;
            MyApplication.getHttpQueues().add(request);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_HEART_DATA:
                    getAxisXLables();//获取x轴的标注
                    getAxisYLables();//获取y轴的标注
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化
                    if (lableType == 1) {
                        setHeartView(heartRates);
                    }
                    break;
                case MSG_REFRESH_HEARTVIEW_DATA:
                    if (heartViewBean != null) {
                        if (heartViewBean.getSumcount() != null && heartViewBean.getCount() != null) {
                            initHeartView(heartViewBean.getSumcount(), heartViewBean.getCount());
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 折线图下方布局  取服务器数据
     */
    private void setHeartViewToService(String timeToMin1, String timeToMin2) {
        HeartRateViewPostBean bean = new HeartRateViewPostBean();
        bean.setMax_value_gt(51 + "");//默认51-129
        bean.setMax_value_lt(129 + "");//默认51-129
        bean.setTimestamp_gt(timeToMin1);
        bean.setTimestamp_lt(timeToMin2);
        //获取后台数据
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_HEART_RATE_FANDRATE)
                , new Gson().toJson(bean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("合格率jsonObject" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    HeartRateViewBean viewBean = new Gson().fromJson(jsonObject.toString(), HeartRateViewBean.class);
                    heartViewBean = viewBean.getData();
                    Message message = new Message();
                    message.what = MSG_REFRESH_HEARTVIEW_DATA;
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort("请查看网络连接");
                    Message message = new Message();
                    message.what = MSG_REFRESH_HEARTVIEW_DATA;
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message message = new Message();
                message.what = MSG_REFRESH_HEARTVIEW_DATA;
                handler.sendMessage(message);
                ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 折线图下方布局
     */
    private void setHeartView(List<HeartRate> heartRatesTemp) {
        List<HeartRate> successHeartRates = new ArrayList<>();//达标
        List<HeartRate> abnormalHeartRates = new ArrayList<>();//异常

        for (int i = 0; i < heartRatesTemp.size(); i++) {
            int hTemp = Integer.valueOf(heartRatesTemp.get(i).getHeartRate());
            if (50 < hTemp && hTemp < 130) {//达标范围50~xx~130   不包含50、130
                successHeartRates.add(heartRatesTemp.get(i)); //达标率：全天总达标心率除总达标数  达标范围50~xx~130   不包含50、130
            } else {
                abnormalHeartRates.add(heartRatesTemp.get(i)); //异常率：全天总异常心率除总异常数
            }
        }
        initHeartView(heartRatesTemp.size(), successHeartRates.size());
    }

    /**
     * @param sumcount 总数
     * @param count    达标数
     */
    private void initHeartView(int sumcount, int count) {
        if (heartRates != null && heartRates.size() != 0) {
            int averageHeart = 0; //平均心率：全天总心率除总心率个数
            for (int i = 0; i < heartRates.size(); i++) {
                int hTemp = Integer.valueOf(heartRates.get(i).getHeartRate());
                averageHeart += hTemp;
            }
            NumberFormat numberFormat = NumberFormat.getInstance();// 创建一个数值格式化对象
            numberFormat.setMaximumFractionDigits(0);// 设置精确到小数点后2位
            String successHeart = numberFormat.format((float) count / (float) sumcount * 100);//达标
            String abnormalHeart = numberFormat.format((float) (sumcount - count) / (float) sumcount * 100);//异常

            heart_success_count_tv.setText(count + context.getResources().getString(R.string.count_text));
            heart_abnormal_count_tv.setText(sumcount - count + context.getResources().getString(R.string.count_text));
            int successHeartInt = 0;
            int abnormalHeartInt = 0;
            if (StringUtils.isNumeric(successHeart)) {
                successHeartInt = Integer.valueOf(successHeart);
            }
            heart_success_percentage_tv.setText(successHeartInt + context.getResources().getString(R.string.percentage_text));//达标
            heart_success_pro.setProgress(Integer.valueOf(successHeartInt));
            if (StringUtils.isNumeric(abnormalHeart)) {
                abnormalHeartInt = Integer.valueOf(abnormalHeart);
            }
            heart_abnormal_percentage_tv.setText(abnormalHeartInt + context.getResources().getString(R.string.percentage_text));//异常
            heart_abnormal_pro.setProgress(Integer.valueOf(abnormalHeartInt));

            if (StringUtils.isNumeric(averageHeart + "")) {
                if (averageHeart != 0) {
                    int avg = averageHeart / heartRates.size();
                    heart_average_tv.setText(avg + "");//平均心率
                    final int finalAvg = avg;
                    iv_triangle_mark.post(new Runnable() {
                        @Override
                        public void run() {
                            int viewWidth = iv_triangle_mark.getWidth(); // 获取宽度
                            ViewUtils.setMargins(iv_triangle, (viewWidth / 180) * finalAvg, 0, 0, 0);
                            LogUtil.i("标尺--" + (viewWidth / 180) * finalAvg + "-宽-" + viewWidth);
                        }
                    });
                } else {
                    ViewUtils.setMargins(iv_triangle, 0, 0, 0, 0);//默认值
                }
            } else {
                ViewUtils.setMargins(iv_triangle, 0, 0, 0, 0);//默认值
            }
        } else {//默认数据
            setDefaultView();//默认数据
        }
    }

    /**
     * 初始化LineChart的一些设置
     */
    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#ff6600"));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.SQUARE）
        line.setCubic(false);//曲线是否平滑
        line.setStrokeWidth(1);//线条的粗细，默认是3
        line.setPointRadius(2);//点的大小
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//		line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用直线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        Axis axisY = new Axis();  //Y轴
        axisX.setName("");  //表格名称
        axisY.setName("");//y轴标注
        axisX.setHasTiltedLabels(true);  //X轴下面坐标轴字体是斜的显示还是直的，true是斜的显示
        axisY.setHasTiltedLabels(false);//true表示斜的
        axisX.setTextColor(Color.parseColor("#666666"));//设置字体颜色
        axisY.setTextColor(Color.parseColor("#666666"));//设置字体颜色
        axisX.setTextSize(11);//设置字体大小
        axisY.setTextSize(11);//设置字体大小
        axisX.setMaxLabelChars(7); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisValues.length
        axisY.setMaxLabelChars(7);//最多几个y轴坐标，意思就是你的缩放让y轴上数据的个数7<=y<=mAxisValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        axisY.setValues(mAxisYValues); //设置Y轴各个坐标点名称
        axisX.setHasLines(true); //x 轴分割线
        axisY.setHasLines(false); //y 轴分割线
        data.setAxisXBottom(axisX); //x 轴在底部
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisXTop(axisX);  //x 轴在顶部
        //data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);//设置该图表是否可交互。如不可交互，则图表不会响应缩放、滑动、选择或点击等操作。默认值为true，可交互。
        lineChart.setZoomType(ZoomType.HORIZONTAL);  //缩放类型，水平
        lineChart.setMaxZoom((float) 3);//缩放比例,默认值20。
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
//        lineChart.setZoomEnabled(false);//设置是否可缩放。
//        lineChart.setScrollEnabled(true);//设置是否可滑动。
//        lineChart.setValueTouchEnabled(false);//设置是否允许点击图标上的值，默认为true。
//        lineChart.startDataAnimation();//开始以动画的形式更新图表数据。

        /**注：下面的7，10只是代表一个数字去类比而已
         * 尼玛搞的老子好辛苦！！！见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         * 下面几句可以设置X轴数据的显示个数（x轴0-7个数据），当数据点个数小于（29）的时候，缩小到极致hellochart默认的是所有显示。当数据点个数大于（29）的时候，
         * 若不设置axisX.setMaxLabelChars(int count)这句话,则会自动适配X轴所能显示的尽量合适的数据个数。
         * 若设置axisX.setMaxLabelChars(int count)这句话,
         * 33个数据点测试，若 axisX.setMaxLabelChars(10);里面的10大于v.right= 7; 里面的7，则
         刚开始X轴显示7条数据，然后缩放的时候X轴的个数会保证大于7小于10
         若小于v.right= 7;中的7,反正我感觉是这两句都好像失效了的样子 - -!
         * 并且Y轴是根据数据的大小自动设置Y轴上限
         * 若这儿不设置 v.right= 7; 这句话，则图表刚开始就会尽可能的显示所有数据，交互性太差
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 210;
        lineChart.setMaximumViewport(v);//固定Y轴的范围,如果没有这个,Y轴的范围会根据数据的最大值和最小值决定
        v.left = 0;
        v.right = 7;
        lineChart.setCurrentViewport(v);//这2个属性的设置一定要在lineChart.setMaximumViewport(v);这个方法之后,不然显示的坐标数据是不能左右滑动查看更多数据的
    }

    /**
     * X 轴的显示
     */
    private void getAxisXLables() {
        mAxisXValues.clear();//重新绘图的时候，clear 避免旧数据残留问题
        if (heartRates != null && heartRates.size() != 0) {//手环无数据展示X轴
            for (int i = 0; i < heartRates.size(); i++) {
                switch (lableType) {//切换标签 1天  2周  3月
                    case 1:
                        mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat("HH:mm").format(heartRates.get(i).getDate()).toString()));
                        break;
                    case 2:
                        String weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(heartRates.get(i).getDate() + "", "yyyy-MM-dd"));
                        mAxisXValues.add(new AxisValue(i).setLabel(weekStr));
                        break;
                    case 3:
                        mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat("dd").format(heartRates.get(i).getDate()).toString()));
                        break;
                }
            }
        } else {
            for (int i = 0; i < ds.size(); i++) {
                mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat(" HH:mm").format(ds.get(i)).toString()));
            }
        }
    }

    /**
     * Y 轴的显示
     */
    private void getAxisYLables() {
        mAxisYValues.clear();//重新绘图的时候，clear 避免旧数据残留问题
        for (int i = 0; i < 245; i += 35) {
            mAxisYValues.add(new AxisValue(i).setLabel(i + ""));
        }
    }

    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        mPointValues.clear();//重新绘图的时候，clear 避免旧数据残留问题
        for (int i = 0; i < heartRates.size(); i++) {
            mPointValues.add(new PointValue(i, heartRates.get(i).getHeartRate()));
        }
    }
}
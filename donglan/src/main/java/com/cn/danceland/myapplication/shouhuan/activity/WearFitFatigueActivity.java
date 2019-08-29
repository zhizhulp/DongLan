package com.cn.danceland.myapplication.shouhuan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.WearFitStepBean;
import com.cn.danceland.myapplication.db.WearFitStepHelper;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRatePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.MorePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepListBean;
import com.cn.danceland.myapplication.shouhuan.bean.StepResultBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.ViewUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.HorizontalPickerView;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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
 * 疲劳  取计步数据，不用提交到后台
 * Created by yxx on 2018/7/18.
 */
public class WearFitFatigueActivity extends Activity {
    private static final int MSG_REFRESH_DATA = 0;//日
    //    private static final int MSG_REFRESH_DATA_MORE_DATA = 1;//周 月
    private Context context;
    private DongLanTitleView heart_title;//数据title
    private TextView rightTv;
    private CheckBox day_checkBox;//日
    private CheckBox week_checkBox;//周
    private CheckBox month_checkBox;//年
    private LineChartView lineChart; //折线
    private HorizontalPickerView picker;//水平选择器
    private TextView fatigue_average_tv;//平均值
    private TextView fatigue_analysis_tv;//偏劳分析
    private ImageView iv_triangle;//水平标尺 小三角
    private ImageView iv_triangle_mark;//水平标尺

    private ArrayList<String> pickerList = new ArrayList<>();//选择器数据
    private List<PointValue> mPointValues = new ArrayList<PointValue>();//折线数据list
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();//折线X轴list
    private List<AxisValue> mAxisYValues = new ArrayList<AxisValue>();//折线Y轴list
    private List<String> dayPickerList = new ArrayList<>();//日选择器对应的时间戳  开始-截止  -连接
    private List<String> weekPickerList = new ArrayList<>();//周选择器对应的时间戳  开始-截止  -连接
    private List<String> monthPickerList = new ArrayList<>();//月选择器对应的时间戳  开始-截止  -连接
    private List<WearFitStepBean> wearFitDataBeanList = new ArrayList<>();////本地数据库和后台共用模式
    private List<Date> ds = TimeUtils.getSegmentationTime(5, new Date());//默认轴数据

    private int lableType = 1;//切换标签 1天  2周  3月
    private String lastData = "";//选择器上次滚动的数据

    private WearFitStepHelper stepHelper = new WearFitStepHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit_fatigue);
        context = this;
        initView();
    }

    private void initView() {
        heart_title = findViewById(R.id.shouhuan_title);
        heart_title.setTitle(context.getResources().getString(R.string.fatigue_text));
        rightTv = findViewById(R.id.donglan_right_tv);
        rightTv.setVisibility(View.VISIBLE);
        rightTv.setText(context.getResources().getString(R.string.measure_text));
        day_checkBox = (CheckBox) findViewById(R.id.day_checkBox);//日
        week_checkBox = (CheckBox) findViewById(R.id.week_checkBox);//周
        month_checkBox = (CheckBox) findViewById(R.id.month_checkBox);//年
        lineChart = (LineChartView) findViewById(R.id.line_chart);//折线
        picker = (HorizontalPickerView) findViewById(R.id.scrollPicker);//水平选择器
        fatigue_average_tv = (TextView) findViewById(R.id.fatigue_average_tv);//平均疲劳
        fatigue_analysis_tv = (TextView) findViewById(R.id.fatigue_analysis_tv);//疲劳分析
        iv_triangle = (ImageView) findViewById(R.id.iv_triangle);//水平标尺 小三角
        iv_triangle_mark = (ImageView) findViewById(R.id.iv_triangle_mark);//水平标尺

        day_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        week_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);
        month_checkBox.setOnCheckedChangeListener(onCheckedChangeListener);

        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, WearFitFatigueMeasureActivity.class));//单次测量
            }
        });

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
                        wearFitDataBeanList.clear();//数据数据 HeartRate
                        mPointValues.clear();
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
        defaultQueryStepByDay();

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
        wearFitDataBeanList.clear();//数据数据 HeartRate
        mPointValues.clear();
        String[] temp = null;
        switch (lableType) {//切换标签 1天  2周  3月
            case 1:
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date(dayPickerList.get(dayPickerList.size() - 1)));
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                LogUtil.i(year + "-" + month + "-" + day);
                queryDataByDay(year + "", month + "", day + "");
                break;
            case 2:
                temp = weekPickerList.get(weekPickerList.size() - 1).split("&");
                break;
            case 3:
                temp = monthPickerList.get(monthPickerList.size() - 1).split("&");
                break;
        }
        if (temp != null && temp.length == 2) {
            LogUtil.i("上传参数1" + TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "&" + TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")));
            LogUtil.i("上传参数" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + ""))).toString() + "&"
                    + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtils.timeToTopHour(Long.valueOf(temp[1] + ""))).toString());
            querysDataByWeekOrMonth(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "", TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")) + "");
        }
    }

    private void defaultQueryStepByDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtil.i(year + "-" + month + "-" + day);
        queryDataByDay(year + "", month + "", day + "");
    }

    /**
     * 查询睡眠 日
     *
     * @param year
     * @param month
     * @param day
     */
    public void queryDataByDay(String year, String month, String day) {
        wearFitDataBeanList.clear();
        mPointValues.clear();
        wearFitDataBeanList = stepHelper.queryByDay(TimeUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));//获取本地数据库数据
        LogUtil.i("本地数据库共有个" + wearFitDataBeanList.size());
        if (wearFitDataBeanList != null && wearFitDataBeanList.size() != 0) {
            getAxisXLables();//获取x轴的标注
            getAxisYLables();//获取y轴的标注
            getAxisPoints();//获取坐标点
            initLineChart();//初始化
            addChildLayout();
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
                                WearFitStepBean wfsb = new WearFitStepBean();
                                wfsb.setTimestamp(data.get(i).getTimestamp());
                                if (data.get(i).getFatigue() != null) {
                                    wfsb.setFatigue(Integer.valueOf(data.get(i).getFatigue()));
                                } else {
                                    wfsb.setFatigue(0);
                                }
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
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_STEP_FINDFATIGUE_AVG)
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
                            WearFitStepBean wfsb = new WearFitStepBean();
                            wfsb.setTimestamp(data.get(i).getTimestamp());
                            if (data.get(i).getFatigue() != null) {
                                int idx = data.get(i).getFatigue().indexOf(".");
                                String hStr = 65 + "";
                                if (idx > 0) {
                                    hStr = data.get(i).getFatigue().substring(0, idx);
                                }
                                wfsb.setFatigue(Integer.valueOf(hStr));
                            } else {
                                wfsb.setFatigue(0);
                            }
                            wearFitDataBeanList.add(wfsb);
                        }
                    }
                    Message message = new Message();
                    message.what = MSG_REFRESH_DATA;
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                    Message message = new Message();
                    message.what = MSG_REFRESH_DATA;
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message message = new Message();
                message.what = MSG_REFRESH_DATA;
                handler.sendMessage(message);
                ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    private void addChildLayout() {
        int sum = 0;
        int avg = 0;
        if (wearFitDataBeanList != null && wearFitDataBeanList.size() != 0) {
            for (WearFitStepBean bb : wearFitDataBeanList) {
                sum += bb.getFatigue();
            }
            avg = sum / wearFitDataBeanList.size();
            fatigue_average_tv.setText(avg + "");

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
            fatigue_average_tv.setText(context.getResources().getString(R.string.sleep_line_text));
            ViewUtils.setMargins(iv_triangle, 0, 0, 0, 0);
        }
        String str = "状态良好";
        if (avg < 35) {
            str = "状态良好";
        } else if (avg < 35 && avg < 45) {
            str = "轻度疲劳";
        } else {
            str = "重度疲劳";
        }
        fatigue_analysis_tv.setText(str);
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
        v.top = 220;
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
        if (wearFitDataBeanList != null && wearFitDataBeanList.size() != 0) {//手环无数据展示X轴
            for (int i = 0; i < wearFitDataBeanList.size(); i++) {
                switch (lableType) {//切换标签 1天  2周  3月
                    case 1:
                        mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat("HH:mm").format(wearFitDataBeanList.get(i).getTimestamp()).toString()));
                        break;
                    case 2:
//                        mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat("MM.dd").format(wearFitDataBeanList.get(i).getTimestamp()).toString()));
                        String weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(wearFitDataBeanList.get(i).getTimestamp() + "", "yyyy-MM-dd"));
                        mAxisXValues.add(new AxisValue(i).setLabel(weekStr));
                        break;
                    case 3:
                        mAxisXValues.add(new AxisValue(i).setLabel(new SimpleDateFormat("dd").format(wearFitDataBeanList.get(i).getTimestamp()).toString()));
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
        for (int i = 0; i < wearFitDataBeanList.size(); i++) {
            mPointValues.add(new PointValue(i, wearFitDataBeanList.get(i).getFatigue()));
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_DATA:
                    getAxisXLables();//获取x轴的标注
                    getAxisYLables();//获取y轴的标注
                    getAxisPoints();//获取坐标点
                    initLineChart();//初始化
                    addChildLayout();
                    break;
//                case MSG_REFRESH_DATA_MORE_DATA:
//                    getAxisXLables();//获取x轴的标注
//                    getAxisYLables();//获取y轴的标注
//                    getAxisPoints();//获取坐标点
//                    initLineChart();//初始化
//                    addChildLayout();
//                    break;
                default:
                    break;
            }
        }
    };
}

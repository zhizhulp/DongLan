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
import com.cn.danceland.myapplication.db.WearFitSleepBean;
import com.cn.danceland.myapplication.db.WearFitSleepHelper;
import com.cn.danceland.myapplication.shouhuan.adapter.SleepAdapter;
import com.cn.danceland.myapplication.shouhuan.bean.HeartRatePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepLastBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepListBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepMorePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepResultBean;
import com.cn.danceland.myapplication.shouhuan.chart.BarEntity;
import com.cn.danceland.myapplication.shouhuan.chart.BarGroup;
import com.cn.danceland.myapplication.shouhuan.chart.BarView;
import com.cn.danceland.myapplication.shouhuan.chart.SourceEntity;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
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
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 睡眠
 * Created by yxx on 2018/7/18.
 */
public class WearFitSleepActivity extends Activity {
    private static final int MSG_REFRESH_SLEEP_DATA = 0;//日
    private static final int MSG_REFRESH_SLEEP_MORE_DATA = 1;//周 月
    private int lableType = 1;//切换标签 1天  2周  3月
    private Context context;
    private DongLanTitleView heart_title;//心率title
    private LinearLayout column_layout;
    private LinearLayout more_layout;//周月布局
    private LinearLayout col_layout;//日布局
    private NoScrollListView listview;
    private TextView sleep_detail_tv;//上面最小的蓝色item描述
    private CheckBox day_checkBox;//日
    private CheckBox week_checkBox;//周
    private CheckBox month_checkBox;//年
    private HorizontalPickerView picker;//水平选择器
    private TextView deep_sleep_two_tv;//深睡
    private TextView shallow_sleep_two_tv;//浅睡
    private TextView sleep_quality_tv;//睡眠质量
    private TextView sleep_time_tv;//睡眠时长
    private TextView more_deep_sleep_two_tv;//日均深睡
    private TextView more_shallow_sleep_two_tv;//日均浅睡
    private TextView more_sleep_quality_tv;//日均清醒
    private TextView more_sleep_time_tv;//日均时长
    private TextView sleep_start_tv;//开始睡眠时长
    private TextView end_start_tv;//截止睡眠时长
    private BarGroup barGroup;
    private HorizontalScrollView root;
    private View popView;
    private PopupWindow popupWindow;

    private DecimalFormat mFormat = new DecimalFormat("##.####");

    private SleepAdapter sleepAdapter;
    private WearFitSleepHelper sleepHelper = new WearFitSleepHelper();

    private ArrayList<String> pickerList = new ArrayList<>();//选择器数据
    private List<String> dayPickerList = new ArrayList<>();//日选择器对应的时间戳  开始-截止  -连接
    private List<String> weekPickerList = new ArrayList<>();//周选择器对应的时间戳  开始-截止  -连接
    private List<String> monthPickerList = new ArrayList<>();//月选择器对应的时间戳  开始-截止  -连接
    private List<SleepBean> sleepBeans = new ArrayList<>();//睡眠数据
    private List<SleepResultBean> behindData = new ArrayList<>();//最后一条后面所有的数据 ALL
    private List<WearFitSleepBean> wearFitSleepBeanList = new ArrayList<>();//本地数据库和后台共用模式

    private SleepResultBean lastDateTime;//服务器最后睡眠
    private String lastData = "";//选择器上次滚动的数据

    /*柱状图的最大值*/
    private float sourceMax = 0.00f;
    private int left;
    private int baseLineHeiht;
    private RelativeLayout.LayoutParams lp;
    private List<SourceEntity.Source> moreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit_sleep);
        context = this;
        initView();
    }

    private void initView() {
        heart_title = findViewById(R.id.shouhuan_title);
        heart_title.setTitle(context.getResources().getString(R.string.sleep_text));
        column_layout = (LinearLayout) findViewById(R.id.column_layout);
        more_layout = (LinearLayout) findViewById(R.id.more_layout);
        col_layout = (LinearLayout) findViewById(R.id.col_layout);
        listview = (NoScrollListView) findViewById(R.id.listview);
        sleep_detail_tv = (TextView) findViewById(R.id.sleep_detail_tv);
        day_checkBox = (CheckBox) findViewById(R.id.day_checkBox);//日
        week_checkBox = (CheckBox) findViewById(R.id.week_checkBox);//周
        month_checkBox = (CheckBox) findViewById(R.id.month_checkBox);//年
        picker = (HorizontalPickerView) findViewById(R.id.scrollPicker);//水平选择器
        deep_sleep_two_tv = (TextView) findViewById(R.id.deep_sleep_two_tv);//深睡
        shallow_sleep_two_tv = (TextView) findViewById(R.id.shallow_sleep_two_tv);//浅睡
        sleep_quality_tv = (TextView) findViewById(R.id.sleep_quality_tv);//睡眠质量
        sleep_time_tv = (TextView) findViewById(R.id.sleep_time_tv);//睡眠时长
        more_deep_sleep_two_tv = (TextView) findViewById(R.id.more_deep_sleep_two_tv);//日均深睡
        more_shallow_sleep_two_tv = (TextView) findViewById(R.id.more_shallow_sleep_two_tv);//日均浅睡
        more_sleep_quality_tv = (TextView) findViewById(R.id.more_sleep_quality_tv);//日均清醒
        more_sleep_time_tv = (TextView) findViewById(R.id.more_sleep_time_tv);//日均时长
        sleep_start_tv = (TextView) findViewById(R.id.sleep_start_tv);//开始睡眠时长
        end_start_tv = (TextView) findViewById(R.id.end_start_tv);//截止睡眠时长
        barGroup = (BarGroup) findViewById(R.id.bar_group);
        root = (HorizontalScrollView) findViewById(R.id.bar_scroll);
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
                        sleepBeans.clear();//睡眠数据
                        wearFitSleepBeanList.clear();//本地数据库和后台共用模式
                        column_layout.removeAllViews();
                        sleep_detail_tv.setVisibility(View.VISIBLE);
                        sleep_detail_tv.setText("");
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
                                querySleepByDay(year + "", month + "", day + "");
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
                            querysSleepByWeekOrMonth(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "", TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")) + "");
                        }
                    }
                    lastData = picker.getSelectedString();
                }
                return false;
            }
        });

        initPickerDay();//默认日数据
        defaultQuerySleepByDay();
        getLastData();//服务器最后数据
        col_layout.setVisibility(View.VISIBLE);
        more_layout.setVisibility(View.GONE);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.day_checkBox://日
                    col_layout.setVisibility(View.VISIBLE);
                    more_layout.setVisibility(View.GONE);
                    initPickerDay();
                    lableType = 1;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.week_checkBox://周
                    col_layout.setVisibility(View.GONE);
                    more_layout.setVisibility(View.VISIBLE);
                    initPickerWeek();
                    lableType = 2;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    week_checkBox.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                    month_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
                    break;
                case R.id.month_checkBox://月
                    col_layout.setVisibility(View.GONE);
                    more_layout.setVisibility(View.VISIBLE);
                    initPickerMonth();
                    lableType = 3;//切换标签 1天  2周  3月
                    day_checkBox.setTextColor(context.getResources().getColor(R.color.colorGray8));
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
                defaultQuerySleepByDay();
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
            querysSleepByWeekOrMonth(TimeUtils.timeToTopHour(Long.valueOf(temp[0] + "")) + "", TimeUtils.timeToTopHour(Long.valueOf(temp[1] + "")) + "");
//            setHeartViewToService(temp[0] + "", temp[1] + "");
        }
    }

    private void defaultQuerySleepByDay() {
        sleepBeans.clear();//睡眠数据
        wearFitSleepBeanList.clear();//本地数据库和后台共用模式
        column_layout.removeAllViews();
        sleep_detail_tv.setVisibility(View.VISIBLE);
        sleep_detail_tv.setText("");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        LogUtil.i(year + "-" + month + "-" + day);
        querySleepByDay(year + "", month + "", day + "");
    }

    private void addChildLayout() {//-1 清醒  1 浅水  2 深睡\
        int screenWidth = AppUtils.getWidth() - 30;//获取屏幕宽度dp,px 减掉左右边距
        int sumItemWidth = 0;
        for (SleepBean sbb : sleepBeans
                ) {
            sumItemWidth += sbb.getContinuoustime();
        }
//        LogUtil.i("所有柱子总宽度" + sumItemWidth);
        for (int i = 0; i < sleepBeans.size(); i++) {
            NumberFormat numberFormat = NumberFormat.getInstance();// 创建一个数值格式化对象
            numberFormat.setMaximumFractionDigits(0);// 设置精确到小数点后2位
            String successHeart = numberFormat.format((float) sleepBeans.get(i).getContinuoustime() / (float) sumItemWidth * screenWidth);//达标
            int successHeartInt = 0;
            if (StringUtils.isNumeric(successHeart)) {
                successHeartInt = Integer.valueOf(successHeart);
            }
//            LogUtil.i("所有柱子占比" + successHeartInt);

            LinearLayout chlidLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(successHeartInt, LinearLayout.LayoutParams.MATCH_PARENT);
            switch (sleepBeans.get(i).getState()) {
                case -1:
                    layoutParams.setMargins(0, 80, 0, 0);
                    chlidLayout.setBackgroundColor(context.getResources().getColor(R.color.color_dl_yellow));
                    break;
                case 1:
                    layoutParams.setMargins(0, 40, 0, 0);
                    chlidLayout.setBackgroundColor(context.getResources().getColor(R.color.shallow_sleep_bg));
                    break;
                case 2:
                    layoutParams.setMargins(0, 0, 0, 0);
                    chlidLayout.setBackgroundColor(context.getResources().getColor(R.color.deep_sleep_bg));
                    break;
            }
            chlidLayout.setLayoutParams(layoutParams);
            chlidLayout.setTag(i);//标识
            chlidLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < column_layout.getChildCount(); i++) {
                        if (view.getTag().equals(column_layout.getChildAt(i).getTag())) {
                            sleep_detail_tv.setVisibility(View.VISIBLE);
                            switch (sleepBeans.get(i).getState()) {
                                case -1:
                                    sleep_detail_tv.setText(context.getResources().getString(R.string.awake_text) + sleepBeans.get(i).getContinuoustime() + "分钟");
                                    sleep_detail_tv.setTextColor(context.getResources().getColor(R.color.color_dl_yellow));
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.color_tab_text_yellow));
                                    break;
                                case 1:
                                    sleep_detail_tv.setText(context.getResources().getString(R.string.shallow_sleep_text) + sleepBeans.get(i).getContinuoustime() + "分钟");
                                    sleep_detail_tv.setTextColor(context.getResources().getColor(R.color.shallow_sleep_bg));
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.color_tab_text_yellow));
                                    break;
                                case 2:
                                    sleep_detail_tv.setText(context.getResources().getString(R.string.deep_sleep_text) + sleepBeans.get(i).getContinuoustime() + "分钟");
                                    sleep_detail_tv.setTextColor(context.getResources().getColor(R.color.deep_sleep_bg));
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.color_tab_text_yellow));
                                    break;
                            }
                        } else {
                            switch (sleepBeans.get(i).getState()) {
                                case -1:
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.color_dl_yellow));
                                    break;
                                case 1:
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.shallow_sleep_bg));
                                    break;
                                case 2:
                                    column_layout.getChildAt(i).setBackgroundColor(context.getResources().getColor(R.color.deep_sleep_bg));
                                    break;
                            }
                        }
                    }
                }
            });
            column_layout.addView(chlidLayout);
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
                barGroup.setHeight(sourceMax, height - baseLineTop - baseLineView.getHeight() / 2,45);
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
//                        final ObjectAnimator anim = ObjectAnimator.ofFloat(barItem, "scaleY", 0.0F, 1.0F).setDuration(1500);
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
                            String showText = "深睡：" + (int) ss.getDeepCount() + "分\n"
                                    + "浅睡：" + (int) ss.getShallowCount() + "分\n"
                                    + "清醒：" + (int) ss.getAwakeCount() + "分";
                            ((TextView) popView.findViewById(R.id.txt)).setText(showText);
                            showPop(barItem, top);
                        }
                    });
                }
                return false;
            }
        });
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

    private void setYAxis(List<SourceEntity.Source> list) {
//        sourceMax = list.get(0).getAllCount();
        sourceMax = 750;
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).getAllCount() > sourceMax) {
                sourceMax = list.get(i).getAllCount();
            }
        }
        ((TextView) findViewById(R.id.tv_num1)).setText((int) sourceMax / 3 + "");
        ((TextView) findViewById(R.id.tv_num2)).setText((int) sourceMax * 2 / 3 + "");
        ((TextView) findViewById(R.id.tv_num3)).setText((int) sourceMax + "");
    }

    /**
     * 查询睡眠 日
     *
     * @param year
     * @param month
     * @param day
     */
    public void querySleepByDay(String year, String month, String day) {
        deep_sleep_two_tv.setText(context.getResources().getString(R.string.sleep_line_text));//深睡
        shallow_sleep_two_tv.setText(context.getResources().getString(R.string.sleep_line_text));//浅睡
        sleep_time_tv.setText(context.getResources().getString(R.string.sleep_line_text));//睡眠时长
        sleep_quality_tv.setText(context.getResources().getString(R.string.sleep_line_text));//睡眠质量
        if (sleepBeans != null && sleepBeans.size() != 0) {
            sleep_start_tv.setText("00:00");//开始睡眠时长
            end_start_tv.setText("00:00");//截止睡眠时长
        }
        wearFitSleepBeanList = sleepHelper.queryByDay(TimeUtils.date2TimeStamp(year + "-" + month + "-" + day, "yyyy-MM-dd"));//获取本地数据库心率
        LogUtil.i("本地数据库共有个" + wearFitSleepBeanList.size());
        if (wearFitSleepBeanList != null && wearFitSleepBeanList.size() != 0) {
            initViewToData();
        } else {
            HeartRatePostBean heartRatePostBean = new HeartRatePostBean();
            heartRatePostBean.setYear(year);
            heartRatePostBean.setMonth(month);
            heartRatePostBean.setDay(day);
            LogUtil.i("请求后台心率" + heartRatePostBean.toString());
            //获取后台数据
            MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_SLEEP_LIST)
                    , new Gson().toJson(heartRatePostBean), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    LogUtil.i("jsonObject" + jsonObject.toString());
                    if (jsonObject.toString().contains("true")) {
                        SleepListBean sleepListBean = new Gson().fromJson(jsonObject.toString(), SleepListBean.class);
                        List<SleepResultBean> data = sleepListBean.getData();
                        if (data != null && data.size() != 0) {
                            LogUtil.i("data.size()" + data.size());
                            wearFitSleepBeanList.clear();
                            for (int i = 0; i < data.size(); i++) {
                                String time = data.get(i).getYear() + "-" + data.get(i).getMonth() + "-" + data.get(i).getDay() + " "
                                        + data.get(i).getHour() + ":" + data.get(i).getMinute() + ":" + "00";
                                WearFitSleepBean wfsb = new WearFitSleepBean();
                                wfsb.setTimestamp(TimeUtils.date2TimeStamp(time, "yyyy-MM-dd HH:mm:ss"));
                                wfsb.setState(data.get(i).getState());//11位state
                                wfsb.setContinuoustime(Integer.valueOf(data.get(i).getMinutes()));//睡了多久 12位*256+13位
                                wearFitSleepBeanList.add(wfsb);
                            }
                        }
                        Message message = new Message();
                        message.what = MSG_REFRESH_SLEEP_DATA;
                        handler.sendMessage(message);
                    } else {
                        ToastUtils.showToastShort("请查看网络连接");
                        Message message = new Message();
                        message.what = MSG_REFRESH_SLEEP_DATA;
                        handler.sendMessage(message);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                    LogUtil.e("onErrorResponse", volleyError.toString());
                }
            });
            MyApplication.getHttpQueues().add(request);
        }
    }

    /**
     * 查询心率 周 月
     *
     * @param timestamp_gt 开始时间
     * @param timestamp_lt 截止时间
     */
    private void querysSleepByWeekOrMonth(String timestamp_gt, String timestamp_lt) {
        more_deep_sleep_two_tv.setText(context.getResources().getString(R.string.sleep_line_text));//深睡
        more_shallow_sleep_two_tv.setText(context.getResources().getString(R.string.sleep_line_text));//浅睡
        more_sleep_time_tv.setText(context.getResources().getString(R.string.sleep_line_text));//睡眠时长
        more_sleep_quality_tv.setText(context.getResources().getString(R.string.sleep_line_text));//睡眠质量
        moreList.clear();
        barGroup.removeAllViews();
        SleepMorePostBean weekPostBean = new SleepMorePostBean();
        weekPostBean.setGroup_time_gt(timestamp_gt);
        weekPostBean.setGroup_time_lt(timestamp_lt);
        LogUtil.i("请求后台心率" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(timestamp_gt))) + "-"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(timestamp_lt))));
        //获取后台数据
        MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_SLEEP_FINDSUM)
                , new Gson().toJson(weekPostBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("jsonObject" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    SleepListBean sleepListBean = new Gson().fromJson(jsonObject.toString(), SleepListBean.class);
                    List<SleepResultBean> data = sleepListBean.getData();
                    if (data != null && data.size() != 0) {
                        LogUtil.i("data.size()" + data.size());
                        wearFitSleepBeanList.clear();
                        for (int i = 0; i < data.size(); i++) {
                            SourceEntity.Source source = new SourceEntity.Source();
                            source.setAwakeCount(0);//清醒
                            int sum = Integer.valueOf(data.get(i).getMinutes1());
                            int shenshui = Integer.valueOf(data.get(i).getMinutes2());
                            source.setShallowCount(sum - shenshui);//浅睡
                            source.setDeepCount(shenshui);//深睡
                            source.setDayAwake(Long.valueOf(data.get(i).getState_one_count() + ""));
                            source.setScale(100);
                            source.setTime(Long.valueOf(data.get(i).getGroup_time()));
                            String weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(data.get(i).getGroup_time() + "", "yyyy-MM-dd"));
                            switch (lableType) {//切换标签 1天  2周  3月
                                case 2:
                                    weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(data.get(i).getGroup_time() + "", "yyyy-MM-dd"));
                                    break;
                                case 3:
                                    weekStr = new SimpleDateFormat("d").format(new Date(Long.valueOf(data.get(i).getGroup_time()))).toString();
                                    break;
                            }
                            source.setSource(weekStr);
                            source.setAllCount(source.getAwakeCount() + source.getShallowCount() + source.getDeepCount());
                            moreList.add(source);
                        }
                    }

                    Message message = new Message();
                    message.what = MSG_REFRESH_SLEEP_MORE_DATA;
                    handler.sendMessage(message);
                } else {
                    ToastUtils.showToastShort("请查看网络连接");
                    Message message = new Message();
                    message.what = MSG_REFRESH_SLEEP_MORE_DATA;
                    handler.sendMessage(message);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Message message = new Message();
                message.what = MSG_REFRESH_SLEEP_MORE_DATA;
                handler.sendMessage(message);
                ToastUtils.showToastShort(context.getResources().getText(R.string.network_connection_text).toString());
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        }) ;
        MyApplication.getHttpQueues().add(request);
    }

    private void initViewToData() {
        //数据库中查询的为状态1的数据
        List<SleepBean> sleep1 = new ArrayList<>();
        //数据库中查询的为状态2的数据,这是最终显示的状态2的数据集合
        List<SleepBean> sleep2 = new ArrayList<>();
        //最终显示的状态1的数据集合
        List<SleepBean> sleep3 = new ArrayList<>();
        //清醒的数据信息
        List<SleepBean> sleep4 = new ArrayList<>();

        //根据状态信息将数据信息分成2中状态信息的集合
        for (WearFitSleepBean wfsb : wearFitSleepBeanList) {
            if (wfsb.getState().equals("2")) {
                SleepBean ss = new SleepBean();
                ss.setContinuoustime(wfsb.getContinuoustime());
                ss.setState(2);//图形状态 -1清醒  1浅水  2深睡
                ss.setStartTime(wfsb.getTimestamp());
                ss.setEndTime(wfsb.getTimestamp() + (wfsb.getContinuoustime() * 60 * 1000));
                sleep2.add(ss);//状态2的数据
            } else if (wfsb.getState().equals("1")) {
                SleepBean ss = new SleepBean();
                ss.setContinuoustime(wfsb.getContinuoustime());
                ss.setState(1);//图形状态 -1清醒  1浅水  2深睡
                ss.setStartTime(wfsb.getTimestamp());
                ss.setEndTime(wfsb.getTimestamp() + (wfsb.getContinuoustime() * 60 * 1000));
                sleep1.add(ss);//状态1的数据
            }
        }

        while (sleep1.size() > 0) {
            SleepBean sep = sleep1.get(0);
            long starTime = sep.getStartTime();
            long endTime = sep.getEndTime();
            boolean f = false;//数据是否包含state=1的数据

            for (SleepBean s : sleep2) {
                //如果状态1的开始时间小于状态2的开始时间并且状态2的开始时间小于状态1的结束时间
                if (starTime < s.getStartTime() && s.getStartTime() < endTime) {
                    f = true;
                    //生成一条新的状态1的数据信息
                    SleepBean newState1 = new SleepBean();
                    newState1.setContinuoustime((int) (s.getStartTime() - starTime) / 60 / 1000);
                    newState1.setState(1);//图形状态 -1清醒  1浅水  2深睡
                    newState1.setStartTime(starTime);
                    newState1.setEndTime(s.getStartTime());
                    sleep3.add(newState1);
                }
                //如果状态2的数据的结束时间小于状态1的结束时间  那么创建一个状态1的数据信息
                if (starTime < s.getEndTime() && s.getEndTime() < endTime) {
                    f = true;
                    SleepBean newState1 = new SleepBean();
                    newState1.setContinuoustime((int) (endTime - s.getEndTime()) / 60 / 1000);
                    newState1.setState(1);//图形状态 -1清醒  1浅水  2深睡
                    newState1.setStartTime(s.getEndTime());
                    newState1.setEndTime(endTime);
                    sleep1.add(newState1);
                    break;
                }
            }
            if (!f) {
                sleep3.add(sep);
            }
            if (sleep1.get(0).getStartTime() == starTime) {
                sleep1.remove(0);
            }
        }
        //将深度睡眠和潜睡时间合并成一个新的数据并按照时间排序
        sleepBeans.addAll(sleep2);
        sleepBeans.addAll(sleep3);
        Collections.sort(sleepBeans, new Comparator<SleepBean>() {

            public int compare(SleepBean o1, SleepBean o2) {

                if (o1.getStartTime() > o2.getStartTime()) {
                    return 1;
                }
                if (o1.getStartTime() == o2.getStartTime()) {
                    return 0;
                }
                return -1;
            }
        });

        for (int i = 0; i < sleepBeans.size(); i++) {

            if ((i + 1) == sleepBeans.size()) {
                break;
            }
            if (sleepBeans.get(i).getEndTime() != sleepBeans.get(i + 1).getStartTime()) {
                SleepBean newState1 = new SleepBean();
                newState1.setContinuoustime((int) (sleepBeans.get(i + 1).getStartTime() - sleepBeans.get(i).getEndTime()) / 60 / 1000);
                newState1.setState(-1);//图形状态 -1清醒  1浅水  2深睡
                newState1.setStartTime(sleepBeans.get(i).getEndTime());
                newState1.setEndTime(sleepBeans.get(i + 1).getStartTime());
                sleep4.add(newState1);
            }
        }

        sleepBeans.addAll(sleep4);
        Collections.sort(sleepBeans, new Comparator<SleepBean>() {

            public int compare(SleepBean o1, SleepBean o2) {

                if (o1.getStartTime() > o2.getStartTime()) {
                    return 1;
                }
                if (o1.getStartTime() == o2.getStartTime()) {
                    return 0;
                }
                return -1;
            }
        });

        long deepSleep = 0;//深睡
        long shallowSleep = 0;//浅睡
        long sleepTime = 0;//睡眠时长

        for (int i = 0; i < sleepBeans.size(); i++) {
            if (sleepBeans.get(i).getState() == 1) {
                sleepTime += sleepBeans.get(i).getContinuoustime();//睡眠时长
                shallowSleep += sleepBeans.get(i).getContinuoustime();//浅睡
            }
            if (sleepBeans.get(i).getState() == 2) {
                sleepTime += sleepBeans.get(i).getContinuoustime();//睡眠时长
                deepSleep += sleepBeans.get(i).getContinuoustime();//深睡
            }
        }

        initChildView1(deepSleep, shallowSleep, sleepTime);
        addChildLayout();

        sleepAdapter = new SleepAdapter(context, sleepBeans);
        listview.setAdapter(sleepAdapter);
    }

    /**
     * 子布局
     *
     * @param deepSleep    深睡
     * @param shallowSleep 浅睡
     * @param sleepTime    总睡
     */
    private void initChildView1(long deepSleep, long shallowSleep, long sleepTime) {
        String sleepQuality = "不佳";//睡眠质量
        if (deepSleep < 60) {
            sleepQuality = "不佳";
        } else if (deepSleep < 90) {
            sleepQuality = "良好";
        } else {
            sleepQuality = "优";
        }
        deep_sleep_two_tv.setText(deepSleep / 60 + "时" + deepSleep % 60 + "分");//深睡
        shallow_sleep_two_tv.setText(shallowSleep / 60 + "时" + shallowSleep % 60 + "分");//浅睡
        sleep_time_tv.setText(sleepTime / 60 + "时" + sleepTime % 60 + "分");//睡眠时长
        sleep_quality_tv.setText(sleepQuality);//睡眠质量
        if (sleepBeans != null && sleepBeans.size() != 0) {
            sleep_start_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(0).getStartTime())).toString());//开始睡眠时长
            end_start_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(sleepBeans.size() - 1).getEndTime())).toString());//截止睡眠时长
        }
    }

    /**
     * 子布局 more
     *
     * @param deepCount    深睡
     * @param shallowCount 浅睡
     * @param awakeCount   清醒
     * @param sleepTime    总睡
     */
    private void initChildView2(long deepCount, long shallowCount, long awakeCount, long sleepTime) {

        more_deep_sleep_two_tv.setText(deepCount / 60 + "时" + deepCount % 60 + "分");//深睡
        more_shallow_sleep_two_tv.setText(shallowCount / 60 + "时" + shallowCount % 60 + "分");//浅睡
        more_sleep_time_tv.setText(sleepTime / 60 + "时" + sleepTime % 60 + "分");//睡眠时长
        more_sleep_quality_tv.setText(awakeCount / 60 + "时" + awakeCount % 60 + "分");//睡眠质量
    }

    //服务器最后数据
    private void getLastData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_SLEEP_FANDLAST), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("服务器最后一条数据是" + s);
                SleepLastBean heartRateLastBean = new Gson().fromJson(s, SleepLastBean.class);
                long lastTime = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
                lastDateTime = heartRateLastBean.getData();//服务器最后数据
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
            List<WearFitSleepBean> sleepList = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date(date1Time));
            LogUtil.i("data" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + i));
            sleepList = sleepHelper.queryByDay(TimeUtils.date2TimeStamp(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH) + i), "yyyy-MM-dd"));//获取本地数据库心率
            for (int j = 0; j < sleepList.size(); j++) {
                SleepResultBean bean = new SleepResultBean();//提交对象
                bean.setYear(new SimpleDateFormat("yyyy").format(new Date(sleepList.get(j).getTimestamp())).toString());//年
                bean.setMonth(new SimpleDateFormat("MM").format(new Date(sleepList.get(j).getTimestamp())).toString());//月
                bean.setDay(new SimpleDateFormat("dd").format(new Date(sleepList.get(j).getTimestamp())).toString());//日
                bean.setHour(new SimpleDateFormat("HH").format(new Date(sleepList.get(j).getTimestamp())).toString());//时
                bean.setMinute(new SimpleDateFormat("mm").format(new Date(sleepList.get(j).getTimestamp())).toString());//分
                bean.setTimestamp(sleepList.get(j).getTimestamp());//long 时间戳
                bean.setGroup_time(getWhichDaySleep(sleepList.get(j).getTimestamp()));//睡了多久  算好属于哪天的觉
                bean.setState(sleepList.get(j).getState() + "");//睡觉状态
                LogUtil.i("睡觉状态-" + sleepList.get(j).getState());
                bean.setMinutes(sleepList.get(j).getContinuoustime() + "");
                LogUtil.i(bean.toString());
                behindData.add(bean);
            }
        }
        isPostData();
    }

    /**
     * 提交心率  先请求后台最后一条   对比本地   提交剩下未提交的数据
     */
    private void isPostData() {
        LogUtil.i("behindData" + behindData.size());
        List<SleepResultBean> postHeartList = new ArrayList<>();
        for (int i = 0; i < behindData.size(); i++) {
            if (lastDateTime != null) {
                if (lastDateTime.getTimestamp() != 0) {//不为空
                    Date date1 = new Date(lastDateTime.getTimestamp());//后台时间
                    Date date2 = new Date(behindData.get(i).getTimestamp());
                    LogUtil.i("比较" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1) + "**"
                            + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date2));
                    if (date1.before(date2)) { //表示date1小于date2  最后一条数据日期小于手环date2
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
            LogUtil.i("提交" + postHeartList.size());
            postHeart(postHeartList);
        }
    }

    private void postHeart(List<SleepResultBean> postHeartList) {
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_WEAR_FIT_SLEEP_SAVE)
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

    //属于哪天的觉
    private String getWhichDaySleep(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time));
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (hour < 12) {//2018-08-08 06:02
            LogUtil.i("现在是12点以前" + day + hour);
        } else {
            LogUtil.i("现在是12点以后" + day + hour);
            calendar.set(Calendar.DAY_OF_MONTH, day + 1);
        }
        LogUtil.i("时间" + new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTimeInMillis()).toString());
        return calendar.getTimeInMillis() + "";
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_SLEEP_DATA:
                    initViewToData();
                    break;
                case MSG_REFRESH_SLEEP_MORE_DATA:
                    if (moreList != null && moreList.size() != 0) {
                        setBarChart();
                        long deepSleep = 0;//深睡
                        long shallowSleep = 0;//浅睡
                        long awakeCount = 0;//清醒
                        long sleepTime = 0;//睡眠时长

                        for (int i = 0; i < moreList.size(); i++) {
                            shallowSleep += moreList.get(i).getShallowCount();//浅睡
                            deepSleep += moreList.get(i).getDeepCount();//深睡
                            awakeCount += moreList.get(i).getDayAwake();//清醒
                        }
                        sleepTime = shallowSleep + deepSleep;
                        initChildView2(deepSleep / moreList.size()
                                , shallowSleep / moreList.size()
                                , awakeCount / moreList.size()
                                , sleepTime / moreList.size());
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
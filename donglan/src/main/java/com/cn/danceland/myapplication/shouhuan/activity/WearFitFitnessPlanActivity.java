package com.cn.danceland.myapplication.shouhuan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.db.WearFitSleepBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepListBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepMorePostBean;
import com.cn.danceland.myapplication.shouhuan.bean.SleepResultBean;
import com.cn.danceland.myapplication.shouhuan.chart.BarEntity;
import com.cn.danceland.myapplication.shouhuan.chart.BarGroup;
import com.cn.danceland.myapplication.shouhuan.chart.BarView;
import com.cn.danceland.myapplication.shouhuan.chart.SourceEntity;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * 健身计划
 * Created by yxx on 2018/8/8.
 */
public class WearFitFitnessPlanActivity extends Activity {
    private Context context;
    private BarGroup barGroup;
    private HorizontalScrollView root;
    private Button btRefresh1;
    private View popView;
    private PopupWindow popupWindow;

    private DecimalFormat mFormat = new DecimalFormat("##.####");

    /*柱状图的最大值*/
    private float sourceMax = 0.00f;
    private int left;
    private int baseLineHeiht;
    private RelativeLayout.LayoutParams lp;
    private List<SourceEntity.Source> moreList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfit_fitness_plan);
        context = this;
        btRefresh1 = (Button) findViewById(R.id.bt_refresh1);
        btRefresh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setBarChart();
            }
        });
//        1533398400000&1533916800000
        querysSleepByWeekOrMonth("1533484800000", "1533830400000");
    }

    public void setBarChart() {
        barGroup = (BarGroup) findViewById(R.id.bar_group);
        root = (HorizontalScrollView) findViewById(R.id.bar_scroll);
        popView = LayoutInflater.from(context).inflate(
                R.layout.pop_bg, null);

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
                        BarView barItem = (BarView) barGroup.getChildAt(0).findViewById(R.id.barView);
                        baseLineHeiht = findViewById(R.id.base_line).getTop();
                        lp = (RelativeLayout.LayoutParams) root.getLayoutParams();
                        left = baseLineView.getLeft();
                        lp.leftMargin = (int) (left + context.getResources().getDisplayMetrics().density * 3);
                        lp.topMargin = Math.abs(baseLineHeiht - barItem.getHeight());
                        root.setLayoutParams(lp);
//                        final int initHeight = barItem.getHeight();
//                        final ObjectAnimator anim = ObjectAnimator.ofFloat(barItem, "zch", 0.0F, 1.0F).setDuration(1500);
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


    //----------------------------------------------------------------------------------------------------

    private static final int MSG_REFRESH_SLEEP_DATA = 0;
    private List<SleepBean> sleepBeans = new ArrayList<>();//睡眠数据
    private List<WearFitSleepBean> wearFitSleepBeanList = new ArrayList<>();//本地数据库和后台共用模式

    /**
     * 查询心率 周 月
     *
     * @param timestamp_gt 开始时间
     * @param timestamp_lt 截止时间
     */
    private void querysSleepByWeekOrMonth(String timestamp_gt, String timestamp_lt) {

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
                        List<SourceEntity.Source> list = new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            SourceEntity.Source source = new SourceEntity.Source();
                            source.setAwakeCount(0);//清醒
                            int sum = Integer.valueOf(data.get(i).getMinutes1());
                            int shenshui = Integer.valueOf(data.get(i).getMinutes2());
                            source.setShallowCount(sum-shenshui);//浅睡
                            source.setDeepCount(shenshui);//深睡
                            source.setScale(100);
                            source.setTime(Long.valueOf(data.get(i).getGroup_time()));
                            String weekStr = TimeUtils.dateToWeek2(TimeUtils.timeStamp2Date(data.get(i).getGroup_time() + "", "yyyy-MM-dd"));
                            source.setSource(weekStr);
                            if (weekStr.equals("星期二")) {
                                LogUtil.i("&&&&&-" + (sum-shenshui)+"-&&-"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(data.get(i).getGroup_time()))));
                            }
                            source.setAllCount(source.getAwakeCount() + source.getShallowCount() + source.getDeepCount());
                            moreList.add(source);
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
                Message message = new Message();
                message.what = MSG_REFRESH_SLEEP_DATA;
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

//        long deepSleep = 0;//深睡
//        long shallowSleep = 0;//浅睡
//        long sleepTime = 0;//睡眠时长
//        String sleepQuality = "不佳";//睡眠质量
//        for (int i = 0; i < sleepBeans.size(); i++) {
//            if (sleepBeans.get(i).getState() == 1) {
//                sleepTime += sleepBeans.get(i).getContinuoustime();//睡眠时长
//                shallowSleep += sleepBeans.get(i).getContinuoustime();//浅睡
//            }
//            if (sleepBeans.get(i).getState() == 2) {
//                sleepTime += sleepBeans.get(i).getContinuoustime();//睡眠时长
//                deepSleep += sleepBeans.get(i).getContinuoustime();//深睡
//            }
//        }
//        if (deepSleep < 60) {
//            sleepQuality = "不佳";
//        } else if (deepSleep < 90) {
//            sleepQuality = "良好";
//        } else {
//            sleepQuality = "优";
//        }
//        deep_sleep_two_tv.setText(deepSleep / 60 + "时" + deepSleep % 60 + "分");//深睡
//        shallow_sleep_two_tv.setText(shallowSleep / 60 + "时" + shallowSleep % 60 + "分");//浅睡
//        sleep_time_tv.setText(sleepTime / 60 + "时" + sleepTime % 60 + "分");//睡眠时长
//        sleep_quality_tv.setText(sleepQuality);//睡眠质量
//        if (sleepBeans != null && sleepBeans.size() != 0) {
//
//            sleep_start_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(0).getStartTime())).toString());//开始睡眠时长
//            end_start_tv.setText(new SimpleDateFormat("HH:mm").format(new Date(sleepBeans.get(sleepBeans.size() - 1).getEndTime())).toString());//截止睡眠时长
//        }
//
//        addChildLayout();
//
//        sleepAdapter = new SleepAdapter(context, sleepBeans);
//        listview.setAdapter(sleepAdapter);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MSG_REFRESH_SLEEP_DATA:
                    setBarChart();
                    initViewToData();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}

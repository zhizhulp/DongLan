package com.cn.danceland.myapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.bean.CalendarPointBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.GroupRecordBean;
import com.cn.danceland.myapplication.bean.SiJiaoRecordBean;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueConBean;
import com.cn.danceland.myapplication.bean.TuanKeRecordBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.SiJiaoFragment;
import com.cn.danceland.myapplication.fragment.SiJiaoRecordFragment;
import com.cn.danceland.myapplication.fragment.TuanKeFragment;
import com.cn.danceland.myapplication.fragment.TuanKeRecordFragment;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.R.attr.value;


/**
 * Created by feng on 2018/1/11.
 */

public class CourseActivity extends BaseFragmentActivity {
    FragmentManager fragmentManager;
    SiJiaoFragment siJiaoFragment;
    TuanKeFragment tuanKeFragment;
    SiJiaoRecordFragment siJiaoRecordFragment;
    TuanKeRecordFragment tuanKeRecordFragment;
    ImageView course_back;
    //  NCalendar nccalendar;
    TextView tv_date;
    RelativeLayout rl_nv, rl_tuanke_record;
    LinearLayout week;
    TabLayout.Tab tab1, tab2;
    TabLayout tablayout;

    String type;//0是列表，1是记录，2是小团课课程表
    String startTime, endTime;
    int id, course_type_id;
    String isTuanke;//团课isTuanke==0；一对一和小团课==1
    private int flag;//0普通 1进入团体私教
    Time time;
    String nowTime, nowTimeLen, role, auth;
    Data data;
    long monthFirstDay, monthLastDay;
    String currentSelectDate;//当前选择日期
    Gson gson;
    ArrayList<String> yuyueTimeList;
    String from;
    CalendarView mCalendarView;
    private CalendarPointBean calendarPointBean;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    if (calendarPointBean.getData() != null && calendarPointBean.getData().size() > 0) {
                        Map<String, Calendar> map = new HashMap<>();

                        for (int i = 0; i < calendarPointBean.getData().size(); i++) {
                            List<Integer> colors = new ArrayList<>();
                            if (calendarPointBean.getData().get(i).getCourse()) {
                                colors.add(0xFF262626);
                            }
                            if (calendarPointBean.getData().get(i).getFreeGroupCourse()) {
                                colors.add(0xFF5ac8fb);
                            }
                            if (calendarPointBean.getData().get(i).getGroupCourse()) {
                                colors.add(0xFFd81159);
                            }
                            map.put(getSchemePintCalendar(calendarPointBean.getData().get(i).getDate() + "", colors).toString(),
                                    getSchemePintCalendar(calendarPointBean.getData().get(i).getDate() + "", colors));


                        }


                        //此方法在巨大的数据量上不影响遍历性能，推荐使用
                        mCalendarView.setSchemeDate(map);
                    }


                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);
        EventBus.getDefault().register(this);
        initHost();
        initView();
        setOnclick();
        loadCalendar();
//        if(startTime!=null){
//            getRecordTime();
//            setPoint();
//        }

    }

    private Calendar getSchemePintCalendar(String date, List<Integer> colors) {
        String[] dates = TimeUtils.timeStamp2Date(date, "yyyy-MM-dd").split("-");

        Calendar calendar = new Calendar();
        calendar.setYear(Integer.valueOf(dates[0]));
        calendar.setMonth(Integer.valueOf(dates[1]));
        calendar.setDay(Integer.valueOf(dates[2]));
        calendar.setSchemeColor(0);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme("");
        //    calendar.addScheme(new Calendar.Scheme());
        List<Calendar.Scheme> schemes = new ArrayList<>();
        for (int i = 0; i < colors.size(); i++) {
            schemes.add(new Calendar.Scheme(colors.get(i), ""));

        }
        calendar.setSchemes(schemes);
//        calendar.addScheme(0xFF008800, "假");
//        calendar.addScheme(0xFF008800, "节");
//        //calendar.setSchemes();
        return calendar;
    }

    private void loadCalendar() {
        String url;
        if (!isTuanke.equals("0")) {//不是团课
            url = Constants.plus(Constants.QUERY_MEMBER_CALENDAR);

        } else {
            url = Constants.plus(Constants.GROUP_QUERY_MEMBER_CALENDAR);
        }
        LogUtil.i(url);
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                calendarPointBean = new Gson().fromJson(s, CalendarPointBean.class);
//                Message message=Message.obtain();
//                message.what=100;
                handler.sendEmptyMessage(100);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("memberId", data.getMember().getId());
                map.put("date", startTime);
                LogUtil.i(map.toString());
                return map;

            }
        };
        MyApplication.getHttpQueues().add(stringRequest);

    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case value:
                break;
            default:
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void setPoint() {

        //      nccalendar.setPoint(yuyueTimeList);
        yuyueTimeList.clear();
    }

    public String getCurrentSelectDate() {

        return this.currentSelectDate;
    }

    private void initHost() {
        data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        isTuanke = getIntent().getStringExtra("isTuanke");// .putExtra("id", id)  .putExtra("course_type_id", courseTypeId));
        flag = getIntent().getIntExtra("flag", 0);
        id = getIntent().getIntExtra("id", 0);
        course_type_id = getIntent().getIntExtra("course_type_id", 0);
        if (isTuanke == null) {
            isTuanke = "1";
        }
        time = new Time();
        time.setToNow();
        yuyueTimeList = new ArrayList<String>();
        nowTime = time.year + "-" + (time.month + 1) + "-" + time.monthDay + " 00:00:00";
        startTime = TimeUtils.date2TimeStamp(nowTime, "yyyy-MM-dd HH:mm:ss") + "";
        endTime = (Long.valueOf(startTime) + 86400000) + "";


        role = getIntent().getStringExtra("role");
        auth = getIntent().getStringExtra("auth");

        gson = new Gson();

    }

    private void getSiJiaoRecordTime() {

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        if (role != null) {
            siJiaoYuYueConBean.setEmployee_id(data.getEmployee().getId());
        } else {
            siJiaoYuYueConBean.setMember_no(data.getPerson().getMember_no());
        }
        final java.util.Calendar calendar = TimeUtils.dataToCalendar(new Date(Long.valueOf(startTime)));

        monthFirstDay = TimeUtils.getMonthFirstDay(calendar);
        monthLastDay = TimeUtils.getMonthLastDay(calendar);

        siJiaoYuYueConBean.setCourse_date_gt(monthFirstDay);
        siJiaoYuYueConBean.setCourse_date_lt(monthLastDay);

        final String s = gson.toJson(siJiaoYuYueConBean);

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.APPOINTLIST), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.e("zzf", jsonObject.toString());
                //yuyueTimeList.clear();
                SiJiaoRecordBean siJiaoRecordBean = gson.fromJson(jsonObject.toString(), SiJiaoRecordBean.class);
                if (siJiaoRecordBean != null) {
                    SiJiaoRecordBean.Data data = siJiaoRecordBean.getData();
                    if (data != null) {
                        List<SiJiaoRecordBean.Content> content = data.getContent();
                        if (content != null) {
                            for (int i = 0; i < content.size(); i++) {
                                String s1 = TimeUtils.timeStamp2Date(content.get(i).getCourse_date() + "", "yyyy-MM-dd");
                                yuyueTimeList.add(s1);
                            }
                        }
                    }
                }
                getGroupData(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void getGroupData(String s) {

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FINDGROUPCOURSEAPPOINTLIST), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                //if("2".equals(course_category)){
                GroupRecordBean groupRecordBean = gson.fromJson(jsonObject.toString(), GroupRecordBean.class);
                if (groupRecordBean != null) {
                    List<GroupRecordBean.Data> data = groupRecordBean.getData();
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            String s1 = TimeUtils.timeStamp2Date(data.get(i).getDate() + "", "yyyy-MM-dd");
                            yuyueTimeList.add(s1);
                        }
                    }
                }

                setPoint();
                //}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private int mYear;
    CalendarLayout mCalendarLayout;

    private void setOnclick() {
        mYear = mCalendarView.getCurYear();
        //   mCalendarLayout.shrink();
        tv_date.setText(String.format(Locale.getDefault(), "%d.%d.%d", mCalendarView.getCurYear(), mCalendarView.getCurMonth(), mCalendarView.getCurDay()));
        Calendar selectedCalendar = mCalendarView.getSelectedCalendar();
        currentSelectDate = selectedCalendar.getYear() + "-" + selectedCalendar.getMonth() + "-" + selectedCalendar.getDay() + "";
        startTime = TimeUtils.date2TimeStamp(currentSelectDate + " 00:00:00", "yyyy-MM-dd 00:00:00") + "";
        mCalendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean b) {
                LogUtil.i(calendar.getYear() + "年" + calendar + "月" + calendar.getDay() + b);
                tv_date.setText(calendar.getYear() + "." + calendar.getMonth() + "." + calendar.getDay() + "");
                currentSelectDate = calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + "";
                EventBus.getDefault().post(new StringEvent(currentSelectDate, 4331));
                mYear = calendar.getYear();


                startTime = TimeUtils.date2TimeStamp(currentSelectDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss") + "";
                endTime = (Long.valueOf(startTime) + 86400000) + "";
                Log.d("test_lp", "isTuanke: " + isTuanke + "  type:   " + type);
                if ("0".equals(isTuanke) || "1".equals(type)) {
                    showFragment(type, isTuanke);
                } else if ("2".equals(type)) {
                    showFragment(type, isTuanke);
                }


            }
        });
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!mCalendarLayout.isExpand()) {
//                    mCalendarView.showYearSelectLayout(mYear);
//                    return;
//                }
//                mCalendarView.showYearSelectLayout(mYear);
            }

        });
//
//        nccalendar.setOnCalendarChangedListener(new OnCalendarChangedListener() {
//            @Override
//            public void onCalendarChanged(DateTime dateTime) {
//                if(dateTime!=null){
//                    LogUtil.e("zzf",dateTime.toString());
//                    String[] ts = dateTime.toString().split("T");
//                    tv_date.setText(ts[0]);
//
//                    startTime = TimeUtils.date2TimeStamp(ts[0]+" 00:00:00", "yyyy-MM-dd HH:mm:ss")+"";
//                    endTime = (Long.valueOf(startTime)+86400000)+"";
//                    if("0".equals(isTuanke)||"1".equals(type)){
//                        showFragment(type,isTuanke);
//                    }else if("2".equals(type)){
//                        showFragment(type,isTuanke);
//                    }
//
//
//                    if("0".equals(isTuanke)){
//                        getTuanKeRecordTime();
//                    }else {
//                        if(startTime!=null){
//                            getSiJiaoRecordTime();
//                        }
//                    }
//
//                }
//            }
//
//
//        });
//
//        nccalendar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                startActivity(new Intent(CourseActivity.this,SiJiaoOrderActivity.class));
//
//            }
//        });

    }


    private void getTuanKeRecordTime() {

        SiJiaoYuYueConBean siJiaoYuYueConBean = new SiJiaoYuYueConBean();
        siJiaoYuYueConBean.setMember_no(data.getPerson().getMember_no());
        final java.util.Calendar calendar = TimeUtils.dataToCalendar(new Date(Long.valueOf(startTime)));

        monthFirstDay = TimeUtils.getMonthFirstDay(calendar);
        monthLastDay = TimeUtils.getMonthLastDay(calendar);

        siJiaoYuYueConBean.setCourse_date_gt(monthFirstDay);
        siJiaoYuYueConBean.setCourse_date_lt(monthLastDay);

        String s = gson.toJson(siJiaoYuYueConBean);

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.FREECOURSELIST), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.e("zzf", jsonObject.toString());
                TuanKeRecordBean tuanKeRecordBean = gson.fromJson(jsonObject.toString(), TuanKeRecordBean.class);
                if (tuanKeRecordBean != null) {

                    List<TuanKeRecordBean.Data> data = tuanKeRecordBean.getData();
                    if (data != null) {
                        for (int i = 0; i < data.size(); i++) {
                            String s1 = TimeUtils.timeStamp2Date(data.get(i).getDate() + "", "yyyy-MM-dd");
                            yuyueTimeList.add(s1);
                        }
                    }
                }
                setPoint();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("获取记录失败！");
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void initView() {
        rl_tuanke_record = findViewById(R.id.rl_tuanke_record);
        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        //  nccalendar = findViewById(nccalendar);
        tv_date = findViewById(R.id.tv_date);
        //mDate = findViewById(mDate);
        week = findViewById(R.id.week);
        tablayout = findViewById(R.id.tablayout);
        tab1 = tablayout.getTabAt(0);
        tab2 = tablayout.getTabAt(1);
        rl_nv = findViewById(R.id.rl_nv);
        if (type == null) {
            type = "0";
        }
        showFragment(type, isTuanke);
        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        type = "0";
                        showFragment(type, isTuanke);
                        break;
                    case 1:
                        type = "1";
                        showFragment(type, isTuanke);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public void getItemId(int id, int course_type_id, String type) {
        this.id = id;
        this.course_type_id = course_type_id;
        this.type = type;
    }

    public void showFragment(String type, String isTuanke) {
        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if ("0".equals(type)) {//列表
            if ("0".equals(isTuanke)) {//团课
                //      mDate.setVisibility(View.VISIBLE);
                //     week.setVisibility(View.VISIBLE);
                //     nccalendar.setVisibility(View.VISIBLE);
                mCalendarView.setVisibility(View.VISIBLE);
                rl_tuanke_record.setVisibility(View.GONE);
                tab1.setText("团课");
                if (tuanKeFragment == null) {
                    tuanKeFragment = new TuanKeFragment();
                }
                if (flag == 2) {
                    from = "免费团课";
                } else {
                    from = "小团课";
                }
                tuanKeFragment.refresh(from, startTime, endTime, course_type_id, id);

                //fragmentTransaction.replace(R.id.rl_nv, tuanKeFragment);
                if (!tuanKeFragment.isAdded()) fragmentTransaction.add(R.id.rl_nv, tuanKeFragment);
                fragmentTransaction.show(tuanKeFragment);
                if (tuanKeRecordFragment != null && tuanKeRecordFragment.isAdded())
                    fragmentTransaction.hide(tuanKeRecordFragment);
                if (siJiaoRecordFragment != null && siJiaoRecordFragment.isAdded())
                    fragmentTransaction.hide(siJiaoRecordFragment);
                if (siJiaoFragment != null && siJiaoFragment.isAdded())
                    fragmentTransaction.hide(siJiaoFragment);
            } else {//私教
                if (siJiaoFragment == null) {
                    siJiaoFragment = new SiJiaoFragment();
                }

                siJiaoFragment.getRoles(role, auth, startTime);
                //fragmentTransaction.replace(R.id.rl_nv, siJiaoFragment);
                if (!siJiaoFragment.isAdded()) fragmentTransaction.add(R.id.rl_nv, siJiaoFragment);
                fragmentTransaction.show(siJiaoFragment);
                if (siJiaoRecordFragment != null && siJiaoRecordFragment.isAdded())
                    fragmentTransaction.hide(siJiaoRecordFragment);
                if (tuanKeRecordFragment != null && tuanKeRecordFragment.isAdded())
                    fragmentTransaction.hide(tuanKeRecordFragment);
                if (tuanKeFragment != null && tuanKeFragment.isAdded())
                    fragmentTransaction.hide(tuanKeFragment);
            }

        } else if ("1".equals(type)) {

            if ("0".equals(isTuanke)) {
                //     mDate.setVisibility(View.GONE);
                //  week.setVisibility(View.GONE);
                //    nccalendar.setVisibility(View.GONE);
                if (flag == 1) {
                    mCalendarView.setVisibility(View.VISIBLE);
                    rl_tuanke_record.setVisibility(View.GONE);
                    if (siJiaoRecordFragment == null) {
                        siJiaoRecordFragment = new SiJiaoRecordFragment();
                    }
                    siJiaoRecordFragment.getStartTime(startTime);
                    siJiaoRecordFragment.getRoles(role, auth);
                    Bundle bundle = new Bundle();
                    bundle.putString("currentSelectDate", currentSelectDate);
                    siJiaoRecordFragment.setArguments(bundle);
                    //fragmentTransaction.replace(R.id.rl_nv, siJiaoRecordFragment);
                    if (!siJiaoRecordFragment.isAdded())
                        fragmentTransaction.add(R.id.rl_nv, siJiaoRecordFragment);
                    fragmentTransaction.show(siJiaoRecordFragment);
                    if (siJiaoFragment != null && siJiaoFragment.isAdded())
                        fragmentTransaction.hide(siJiaoFragment);
                    if (tuanKeRecordFragment != null && tuanKeRecordFragment.isAdded())
                        fragmentTransaction.hide(tuanKeRecordFragment);
                    if (tuanKeFragment != null && tuanKeFragment.isAdded())
                        fragmentTransaction.hide(tuanKeFragment);
                } else {
                    mCalendarView.setVisibility(View.GONE);
                    rl_tuanke_record.setVisibility(View.VISIBLE);
                    if (tuanKeRecordFragment == null) {
                        tuanKeRecordFragment = new TuanKeRecordFragment();
                    }

                    tuanKeRecordFragment.getStartTime(startTime);
                    //fragmentTransaction.replace(R.id.rl_tuanke_record, tuanKeRecordFragment);
                    if (!tuanKeRecordFragment.isAdded())
                        fragmentTransaction.add(R.id.rl_tuanke_record, tuanKeRecordFragment);
                    fragmentTransaction.show(tuanKeRecordFragment);
                    if (tuanKeFragment != null && tuanKeFragment.isAdded())
                        fragmentTransaction.hide(tuanKeFragment);
                    if (siJiaoRecordFragment != null && siJiaoRecordFragment.isAdded())
                        fragmentTransaction.hide(siJiaoRecordFragment);
                    if (siJiaoFragment != null && siJiaoFragment.isAdded())
                        fragmentTransaction.hide(siJiaoFragment);
                }

            } else {
                if (siJiaoRecordFragment == null) {
                    siJiaoRecordFragment = new SiJiaoRecordFragment();
                }


                siJiaoRecordFragment.getStartTime(startTime);
                siJiaoRecordFragment.getRoles(role, auth);
                Bundle bundle = new Bundle();
                bundle.putString("currentSelectDate", currentSelectDate);
                siJiaoRecordFragment.setArguments(bundle);
                //fragmentTransaction.replace(R.id.rl_nv, siJiaoRecordFragment);
                if (!siJiaoRecordFragment.isAdded())
                    fragmentTransaction.add(R.id.rl_nv, siJiaoRecordFragment);
                fragmentTransaction.show(siJiaoRecordFragment);
                if (siJiaoFragment != null && siJiaoFragment.isAdded())
                    fragmentTransaction.hide(siJiaoFragment);
                if (tuanKeRecordFragment != null && tuanKeRecordFragment.isAdded())
                    fragmentTransaction.hide(tuanKeRecordFragment);
                if (tuanKeFragment != null && tuanKeFragment.isAdded())
                    fragmentTransaction.hide(tuanKeFragment);
            }

        } else if ("2".equals(type)) {
            if (tuanKeFragment == null) {
                tuanKeFragment = new TuanKeFragment();
            }

            from = "小团课";
            tuanKeFragment.refresh(from, startTime, endTime, course_type_id, id);

            //fragmentTransaction.replace(R.id.rl_nv, tuanKeFragment);
            if (!tuanKeFragment.isAdded()) fragmentTransaction.add(R.id.rl_nv, tuanKeFragment);
            fragmentTransaction.show(tuanKeFragment);
            if (tuanKeRecordFragment != null && tuanKeRecordFragment.isAdded())
                fragmentTransaction.hide(tuanKeRecordFragment);
            if (siJiaoFragment != null && siJiaoFragment.isAdded())
                fragmentTransaction.hide(siJiaoFragment);
            if (siJiaoRecordFragment != null && siJiaoRecordFragment.isAdded())
                fragmentTransaction.hide(siJiaoRecordFragment);
        }

        fragmentTransaction.commit();
    }
}

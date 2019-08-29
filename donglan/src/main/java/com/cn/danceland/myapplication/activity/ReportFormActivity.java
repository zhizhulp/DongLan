package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.ReportCommitBean;
import com.cn.danceland.myapplication.bean.ReportCommitResultBean;
import com.cn.danceland.myapplication.bean.ReportResultBean;
import com.cn.danceland.myapplication.bean.RequestConsultantInfoBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.XCRoundRectImageView;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会籍报表
 * Created by feng on 2018/4/24.
 */

public class ReportFormActivity extends BaseActivity {
    MyListView report_mv, report_mv_02;
    String role_type, role, emp_id;
    DongLanTitleView report_title;
    Gson gson;
    MyListViewAdapter report_mv_Adapter, report_mv_02_Adapter;
    RecyclerView report_rv;
    String nowDate, selectDate;
    TextView tv_date;
    LinearLayout btn_date;
    TextView btn_all;
    Data myInfo;
    String target_role_type;//要查询什么报表
    String branch_id;
    private Time time;

    private TextView group_type_tv;
    private LinearLayout group_type_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initHost();
        initView();
        initData();
    }

    // BaseActivity中统一调用MobclickAgent 类的 onResume/onPause 接口
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }


    private void initData() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDREPORT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ReportCommitResultBean reportCommitResultBean = gson.fromJson(s, ReportCommitResultBean.class);
                if (reportCommitResultBean != null && reportCommitResultBean.getData() != null) {
                    LogUtil.i("onResponse" + s);
                    ReportCommitResultBean.Data data = reportCommitResultBean.getData();
                    boolean isEditor = false;
                    if (!StringUtils.isNullorEmpty(data.getMeet())) {
                        tv_meet.setText(data.getMeet() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getClean())) {
                        tv_clean.setText(data.getClean() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getItem_placement())) {
                        tv_item_placement.setText(data.getItem_placement() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getBody_build())) {
                        tv_body_build.setText(data.getBody_build() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getSport_device())) {
                        tv_sport_device.setText(data.getSport_device() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getGroup_course())) {
                        tv_group_course.setText(data.getGroup_course() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getCourse())) {
                        tv_course.setText(data.getCourse() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getPower())) {
                        tv_power.setText(data.getPower() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getDoor())) {
                        tv_door.setText(data.getDoor() + "");
                        isEditor = true;
                    }
                    if (!StringUtils.isNullorEmpty(data.getRemark())) {
                        tv_remark.setText(data.getRemark() + "");
                        isEditor = true;
                    }
                    if (isEditor) {//有值  不让编辑
                        tv_meet.setFocusable(false);
                        tv_clean.setFocusable(false);
                        tv_item_placement.setFocusable(false);
                        tv_body_build.setFocusable(false);
                        tv_sport_device.setFocusable(false);
                        tv_group_course.setFocusable(false);
                        tv_course.setFocusable(false);
                        tv_power.setFocusable(false);
                        tv_door.setFocusable(false);
                        tv_remark.setFocusable(false);
                        tv_meet.setLongClickable(false);
                        tv_clean.setLongClickable(false);
                        tv_item_placement.setLongClickable(false);
                        tv_body_build.setLongClickable(false);
                        tv_sport_device.setLongClickable(false);
                        tv_group_course.setLongClickable(false);
                        tv_course.setLongClickable(false);
                        tv_power.setLongClickable(false);
                        tv_door.setLongClickable(false);
                        tv_remark.setLongClickable(false);
                        btn_commit.setClickable(false);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("employee_id", emp_id);
                map.put("date", selectDate);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    //业务报表
    private void initBusData(final String date, final String current_role_type, final String employee_id) {


        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.BUSSTATISTICSREPORT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                ReportResultBean reportResultBean = gson.fromJson(s, ReportResultBean.class);
                if (reportResultBean != null) {
                    report_mv_Adapter = new MyListViewAdapter(reportResultBean.getData());
                    report_mv.setAdapter(report_mv_Adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", date);
                map.put("current_role_type", current_role_type);
                //map.put("target_role_type",target_role_type);
                if (employee_id != null) {
                    map.put("employee_id", employee_id);
                }

                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    //业绩报表
    private void initScoreData(final String date, final String current_role_type, final String target_role_type, final String employee_id) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SCORESTATISTICSREPORT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                ReportResultBean reportResultBean = gson.fromJson(s, ReportResultBean.class);
                if (reportResultBean != null) {
                    report_mv_02_Adapter = new MyListViewAdapter(reportResultBean.getData());
                    report_mv_02.setAdapter(report_mv_02_Adapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("date", date);
                map.put("current_role_type", current_role_type);
                map.put("target_role_type", target_role_type);
                if (employee_id != null) {
                    map.put("employee_id", employee_id);
                }

                return map;
            }


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    private void initView() {

        report_mv = findViewById(R.id.report_mv);
        report_mv_02 = findViewById(R.id.report_mv_02);
        tv_date = findViewById(R.id.tv_date);
        tv_date.setText(nowDate);

        btn_date = findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });

        btn_all = findViewById(R.id.btn_all);
        group_type_tv = findViewById(R.id.group_type_tv);
        group_type_layout = findViewById(R.id.group_type_layout);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emp_id = null;
                if (PreclickText != null) {
                    PreclickText.setTextColor(Color.parseColor("#808080"));
                }
                initBusData(selectDate, role, emp_id);
                initScoreData(selectDate, role, target_role_type, emp_id);
                initReportData(selectDate, emp_id);
            }
        });

        report_rv = findViewById(R.id.report_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        report_rv.setLayoutManager(linearLayoutManager);
        report_rv.addItemDecoration(new SpacesItemDecoration(40));

        report_title = findViewById(R.id.report_title);
        if ("会籍顾问".equals(role_type)) {
            role = "1";
            //emp_id = myInfo.getEmployee().getId() + "";
//            report_rv.setVisibility(View.GONE);
            btn_all.setVisibility(View.GONE);
            report_title.setTitle("会籍报表");
            group_type_tv.setText("会籍");
            getPeople();
        } else if ("教练".equals(role_type)) {
            role = "2";
            btn_all.setVisibility(View.GONE);
            report_title.setTitle("教练报表");
            group_type_tv.setText("教练");
            getPeople();
        } else if ("店长".equals(role_type)) {
            role = "4";
            report_title.setTitle("全店报表");
            group_type_tv.setText("服务");
            report_rv.setVisibility(View.GONE);
            group_type_layout.setVisibility(View.GONE);
            getPeople();
        } else if ("会籍主管".equals(role_type)) {
            role = "5";
            report_title.setTitle("会籍报表");
            group_type_tv.setText("会籍");
            getPeople();
        } else if ("前台主管".equals(role_type)) {
            role = "7";
            report_title.setTitle("服务报表");
            group_type_tv.setText("服务");

        } else if ("教练主管".equals(role_type)) {
            role = "6";
            report_title.setTitle("教练报表");
            group_type_tv.setText("教练");
            getPeople();
        } else if ("操教".equals(role_type)) {
            role = "8";
            btn_all.setVisibility(View.GONE);
            report_title.setTitle("教练报表");
            group_type_tv.setText("教练");
            getPeople();
        } else if ("兼职教练".equals(role_type)) {
            role = "11";
            btn_all.setVisibility(View.GONE);
            report_title.setTitle("教练报表");
            group_type_tv.setText("教练");
            getPeople();
        } else if ("前台".equals(role_type)) {
            role = "3";
            btn_all.setVisibility(View.GONE);
            report_title.setTitle("服务报表");
            group_type_tv.setText("服务");
            getPeople();
        }
//        if ("会籍顾问".equals(role_type)) {
//            role = "1";
//            //emp_id = myInfo.getEmployee().getId() + "";
//            report_rv.setVisibility(View.GONE);
//            btn_all.setVisibility(View.GONE);
//            report_title.setTitle("会籍报表");
//        } else if ("教练".equals(role_type)) {
//            role = "2";
//            report_rv.setVisibility(View.GONE);
//            btn_all.setVisibility(View.GONE);
//            report_title.setTitle("教练报表");
//        } else if ("店长".equals(role_type)) {
//            role = "4";
//            report_title.setTitle("全店报表");
//            getPeople();
//        } else if ("会籍主管".equals(role_type)) {
//            role = "5";
//            report_title.setTitle("会籍报表");
//            getPeople();
//        } else if ("前台主管".equals(role_type)) {
//            role = "7";
//            report_title.setTitle("服务报表");
//            getPeople();
//        } else if ("教练主管".equals(role_type)) {
//            role = "6";
//            report_title.setTitle("教练报表");
//            getPeople();
//        } else if ("操教".equals(role_type)) {
//            role = "8";
//            //emp_id = myInfo.getEmployee().getId() + "";
//            report_rv.setVisibility(View.GONE);
//            btn_all.setVisibility(View.GONE);
//            report_title.setTitle("教练报表");
//        } else if ("兼职教练".equals(role_type)) {
//            role = "11";
//            //emp_id = myInfo.getEmployee().getId() + "";
//            report_rv.setVisibility(View.GONE);
//            btn_all.setVisibility(View.GONE);
//            report_title.setTitle("教练报表");
//        } else if ("前台".equals(role_type)) {
//            role = "3";
//            //emp_id = myInfo.getEmployee().getId() + "";
//            report_rv.setVisibility(View.GONE);
//            btn_all.setVisibility(View.GONE);
//            report_title.setTitle("服务报表");
//        }
        emp_id = myInfo.getEmployee().getId() + "";
        initBusData(selectDate, role, emp_id);
        initScoreData(selectDate, role, target_role_type, emp_id);
        initReportData(selectDate, emp_id);

        tv_meet = findViewById(R.id.et_meet);
        tv_clean = findViewById(R.id.et_clean);
        tv_item_placement = findViewById(R.id.et_item_placement);
        tv_body_build = findViewById(R.id.et_body_build);
        tv_sport_device = findViewById(R.id.et_sport_device);
        tv_group_course = findViewById(R.id.et_group_course);
        tv_course = findViewById(R.id.et_course);
        tv_power = findViewById(R.id.et_power);
        tv_door = findViewById(R.id.et_door);
        tv_remark = findViewById(R.id.et_remark);

        btn_commit = findViewById(R.id.btn_commit);
//        tv_meet.setOnClickListener(onClickListener);
//        tv_clean.setOnClickListener(onClickListener);
//        tv_item_placement.setOnClickListener(onClickListener);
//        tv_body_build.setOnClickListener(onClickListener);
//        tv_sport_device.setOnClickListener(onClickListener);
//        tv_group_course.setOnClickListener(onClickListener);
//        tv_course.setOnClickListener(onClickListener);
//        tv_power.setOnClickListener(onClickListener);
//        tv_door.setOnClickListener(onClickListener);
//        tv_remark.setOnClickListener(onClickListener);

        btn_commit.setOnClickListener(onClickListener);

    }

    private void initReportData(final String selectDate, final String emp_id) {
//不知道代码具体想干嘛，这里应该有坑
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.FINDREPORT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                ReportCommitResultBean reportCommitResultBean = gson.fromJson(s, ReportCommitResultBean.class);
                if (reportCommitResultBean != null && reportCommitResultBean.getData() != null) {
                    LogUtil.i("onResponse" + s);
                    ReportCommitResultBean.Data data = reportCommitResultBean.getData();
                    if (!StringUtils.isNullorEmpty(data.getMeet())) {
                        tv_meet.setText(data.getMeet() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getClean())) {
                        tv_clean.setText(data.getClean() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getItem_placement())) {
                        tv_item_placement.setText(data.getItem_placement() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getBody_build())) {
                        tv_body_build.setText(data.getBody_build() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getSport_device())) {
                        tv_sport_device.setText(data.getSport_device() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getGroup_course())) {
                        tv_group_course.setText(data.getGroup_course() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getCourse())) {
                        tv_course.setText(data.getCourse() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getPower())) {
                        tv_power.setText(data.getPower() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getDoor())) {
                        tv_door.setText(data.getDoor() + "");
                    }
                    if (!StringUtils.isNullorEmpty(data.getRemark())) {
                        tv_remark.setText(data.getRemark() + "");
                    }
                    clickAble = true;
                }
                if (!nowDate.equals(selectDate) || clickAble) {
//                    tv_meet.setFocusable(false);
//                    tv_clean.setFocusable(false);
//                    tv_item_placement.setFocusable(false);
//                    tv_body_build.setFocusable(false);
//                    tv_sport_device.setFocusable(false);
//                    tv_group_course.setFocusable(false);
//                    tv_course.setFocusable(false);
//                    tv_power.setFocusable(false);
//                    tv_door.setFocusable(false);
//                    tv_remark.setFocusable(false);
//                    tv_meet.setLongClickable(false);
//                    tv_clean.setLongClickable(false);
//                    tv_item_placement.setLongClickable(false);
//                    tv_body_build.setLongClickable(false);
//                    tv_sport_device.setLongClickable(false);
//                    tv_group_course.setLongClickable(false);
//                    tv_course.setLongClickable(false);
//                    tv_power.setLongClickable(false);
//                    tv_door.setLongClickable(false);
//                    tv_remark.setLongClickable(false);
//                    btn_commit.setClickable(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("employee_id", emp_id);
                map.put("mDate", selectDate);

                return map;
            }


        };
        MyApplication.getHttpQueues().add(stringRequest);
    }


    LinearLayout btn_commit;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_commit:
                    boolean isPost = true;
                    if (!TextUtils.isEmpty(tv_meet.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_clean.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_item_placement.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_body_build.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_sport_device.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_group_course.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_course.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_power.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_door.getText().toString())) {//不能空
                        isPost = false;
                    }
                    if (!TextUtils.isEmpty(tv_remark.getText().toString())) {//不能空
                        isPost = false;
                    }
                    clickAble = true;
                    LogUtil.i("--00--");
                    if (!isPost) {
                        LogUtil.i("--11--");
                        showAleart();
                    } else {
                        LogUtil.i("--22--");
                        ToastUtils.showToastShort("请填写完整");
                    }
                    break;
            }
        }
    };

    private void showAleart() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("提示");
        builder.setMessage("提交成功后将不能修改！");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                commit();
            }
        });
        builder.show();

    }

    boolean clickAble;//提交后不可点击
    EditText tv_meet, tv_clean, tv_item_placement, tv_body_build, tv_sport_device, tv_group_course, tv_course, tv_power, tv_door, tv_remark;

    private void commit() {
        ReportCommitBean reportCommitBean = new ReportCommitBean();
        reportCommitBean.setMeet(tv_meet.getText().toString());
        reportCommitBean.setClean(tv_clean.getText().toString());
        reportCommitBean.setItem_placement(tv_item_placement.getText().toString());
        reportCommitBean.setBody_build(tv_body_build.getText().toString());
        reportCommitBean.setSport_device(tv_sport_device.getText().toString());
        reportCommitBean.setGroup_course(tv_group_course.getText().toString());
        reportCommitBean.setCourse(tv_course.getText().toString());
        reportCommitBean.setPower(tv_power.getText().toString());
        reportCommitBean.setDoor(tv_door.getText().toString());
        reportCommitBean.setRemark(tv_remark.getText().toString());
        //reportCommitBean.setDate(nowDate);

        String s = gson.toJson(reportCommitBean);
        LogUtil.i("tojson--" + s);
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST,Constants.plus( Constants.SAVEREPORT), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject.toString().contains("true")) {
                    clickAble = true;
                    tv_meet.setFocusable(false);
                    tv_clean.setFocusable(false);
                    tv_item_placement.setFocusable(false);
                    tv_body_build.setFocusable(false);
                    tv_sport_device.setFocusable(false);
                    tv_group_course.setFocusable(false);
                    tv_course.setFocusable(false);
                    tv_power.setFocusable(false);
                    tv_door.setFocusable(false);
                    tv_remark.setFocusable(false);
                    tv_meet.setLongClickable(false);
                    tv_clean.setLongClickable(false);
                    tv_item_placement.setLongClickable(false);
                    tv_body_build.setLongClickable(false);
                    tv_sport_device.setLongClickable(false);
                    tv_group_course.setLongClickable(false);
                    tv_course.setLongClickable(false);
                    tv_power.setLongClickable(false);
                    tv_door.setLongClickable(false);
                    tv_remark.setLongClickable(false);
                    btn_commit.setClickable(false);
                    ToastUtils.showToastShort("提交成功");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showDate() {

        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, "选择日期");
        customDateAndTimePicker.setGoneHourAndMinute();
        customDateAndTimePicker.setMax((time.month + 1), time.monthDay);
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                selectDate = customDateAndTimePicker.getDateStringF();
                LogUtil.i(selectDate);
                tv_date.setText(selectDate);
                initBusData(selectDate, role, emp_id);
                initScoreData(selectDate, role, target_role_type, emp_id);
                initReportData(selectDate, emp_id);

            }
        });

        customDateAndTimePicker.showWindow();

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            //outRect.bottom = space;
        }
    }

    private void initHost() {
        gson = new Gson();
        role_type = getIntent().getStringExtra("role_type");
        target_role_type = getIntent().getStringExtra("target_role_type");
        time = new Time();
        time.setToNow();


        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        if (myInfo != null && myInfo.getPerson() != null) {
            branch_id = myInfo.getPerson().getDefault_branch();
        }

        if ((time.month + 1) < 10 && time.monthDay >= 10) {
            nowDate = time.year + "-0" + (time.month + 1) + "-" + time.monthDay;
        } else if (time.monthDay < 10 && (time.month + 1) >= 10) {
            nowDate = time.year + "-" + (time.month + 1) + "-0" + time.monthDay;
        } else if (time.monthDay < 10 && (time.month + 1) < 10) {
            nowDate = time.year + "-0" + (time.month + 1) + "-0" + time.monthDay;
        } else {
            nowDate = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
        }

        //nowDate = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
        selectDate = nowDate;
    }

    /**
     * 除了主管和店长 显示本地用户的im
     */
    private void getPeople() {
        String url = Constants.plus(Constants.FIND_SERVICE_URL);
        boolean isLeader;
        switch (role) {
            case "4"://店长 服务
                url = Constants.plus(Constants.FIND_SERVICE_URL);
                isLeader = true;
                break;
            case "5"://会籍
                url = Constants.plus(Constants.FIND_CONSULTANT_URL);
                isLeader = true;
                break;
            case "6"://教练
                url = Constants.plus(Constants.FIND_JIAOLIAN_URL);
                isLeader = true;
                break;
            default:
                isLeader = false;
                break;
        }
        if (isLeader) {
            MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    LogUtil.i(s);
                    RequestConsultantInfoBean requestConsultantInfoBean = gson.fromJson(s, RequestConsultantInfoBean.class);
                    if (requestConsultantInfoBean != null) {
                        List<RequestConsultantInfoBean.Data> data = requestConsultantInfoBean.getData();
                        if (data != null && data.size() > 0) {
                            report_rv.setAdapter(new MyRecyclerViewAdapter(data));
                        } else {
                            report_rv.setVisibility(View.GONE);
                        }
                    } else {
                        report_rv.setVisibility(View.GONE);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtil.i(volleyError.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("branch_id", branch_id);
                    return map;
                }
            };
            MyApplication.getHttpQueues().add(stringRequest);
        } else {//不是领导
            List<RequestConsultantInfoBean.Data> data = new ArrayList<>();
            RequestConsultantInfoBean.Data data1 = new RequestConsultantInfoBean.Data();
            data1.setCname(myInfo.getEmployee().getCname());
            data1.setSelf_avatar_path(Constants.HOST + myInfo.getEmployee().getSelf_avatar_path());
            data.add(data1);
            report_rv.setAdapter(new MyRecyclerViewAdapter(data));
        }
    }

    TextView PreclickText;
    int click = -1;

    public class MyRecyclerViewAdapter extends RecyclerView.Adapter<ReportFormActivity.ViewHolder> {
        List<RequestConsultantInfoBean.Data> data;

        MyRecyclerViewAdapter(List<RequestConsultantInfoBean.Data> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = View.inflate(ReportFormActivity.this, R.layout.report_huiji_item, null);

            ViewHolder viewHolder = new ViewHolder(inflate);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
//            LogUtil.i("avatar=" + data.get(position).getAvatar_url());
            RequestOptions options = new RequestOptions().placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
            Glide.with(ReportFormActivity.this).load(data.get(position).getAvatar_url()).apply(options).into(holder.img_touxiang);
            if (click != -1 && click == position && emp_id.equals(data.get(position).getId() + "")) {
                holder.img_touxiang_stroke_bg.setVisibility(View.VISIBLE);
                holder.tv_name.setTextColor(Color.parseColor("#FF5E3A"));
            } else{
                holder.img_touxiang_stroke_bg.setVisibility(View.GONE);
                holder.tv_name.setTextColor(Color.parseColor("#6D819C"));
            }
            holder.item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (click != position && PreclickText != null) {
                        PreclickText.setTextColor(Color.parseColor("#FF5E3A"));
                    }
                    click = position;
                    PreclickText = holder.tv_name;
                    holder.tv_name.setTextColor(Color.parseColor("#FF5E3A"));
                    holder.img_touxiang_stroke_bg.setVisibility(View.VISIBLE);
                    emp_id = data.get(position).getId() + "";
                    initBusData(selectDate, role, emp_id);
                    initScoreData(selectDate, role, target_role_type, emp_id);
                    initReportData(selectDate, emp_id);
                    notifyDataSetChanged();
                }
            });
            holder.tv_name.setText(data.get(position).getCname());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        XCRoundRectImageView img_touxiang;
        ImageView img_touxiang_stroke_bg;
        RelativeLayout item_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            item_layout = itemView.findViewById(R.id.item_layout);
            img_touxiang = itemView.findViewById(R.id.img_touxiang);
            tv_name = itemView.findViewById(R.id.tv_name);
            img_touxiang_stroke_bg = itemView.findViewById(R.id.img_touxiang_stroke_bg);
        }
    }

    private class MyListViewAdapter extends BaseAdapter {
        List<ReportResultBean.Data> dataList;

        MyListViewAdapter(List<ReportResultBean.Data> dataList) {
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View inflate = View.inflate(ReportFormActivity.this, R.layout.report_item, null);
            TextView tv_name = inflate.findViewById(R.id.tv_name);
            TextView tv_today = inflate.findViewById(R.id.tv_today);
            TextView tv_thisMonth = inflate.findViewById(R.id.tv_thisMonth);
            TextView tv_total = inflate.findViewById(R.id.tv_total);
            TextView tv_thisMonthUnit = inflate.findViewById(R.id.tv_thisMonthUnit);
            ReportResultBean.Data data = dataList.get(position);
            tv_name.setText(data.getTitle());
            tv_today.setText(data.getToday() + data.getUnit());
            tv_thisMonth.setText(data.getEndOfToDay());
            tv_total.setText(data.getAllOfMonth() + data.getUnit());
            tv_thisMonthUnit.setVisibility(View.VISIBLE);
            tv_thisMonthUnit.setText(data.getUnit());

            return inflate;
        }
    }
}

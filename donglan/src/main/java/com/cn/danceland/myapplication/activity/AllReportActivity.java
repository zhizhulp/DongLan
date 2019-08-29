package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ReportResultBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyListView;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.XCRoundRectImageView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by feng on 2018/4/24.
 */

public class AllReportActivity extends BaseActivity {

    MyListView mv_01,mv_02,mv_03,mv_04;
    String role,target_role_type;
    Gson gson;
    LinearLayout btn_date;
    String nowDate,selectDate;
    TextView tv_date;
    DongLanTitleView report_all_title;
    private Time time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_all);
        initHost();
        initView();
    }

    private void initView() {
        mv_01 = findViewById(R.id.mv_01);
        mv_02 = findViewById(R.id.mv_02);
        mv_03 = findViewById(R.id.mv_03);
        mv_04 = findViewById(R.id.mv_04);
        report_all_title = findViewById(R.id.report_all_title);
        report_all_title.setTitle("全店报表");

        btn_date= findViewById(R.id.btn_date);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
        tv_date = findViewById(R.id.tv_date);
        tv_date.setText(nowDate);

        initScoreData(selectDate,role,target_role_type,null);
        initBusData(selectDate,"1",null);
        initBusData(selectDate,"2",null);
        initBusData(selectDate,"3",null);

    }

    private String getCurrentroleType(String role) {
        HashMap<String,String> roleMap = new HashMap<>();
        roleMap.put("会籍顾问", "1");
        roleMap.put("教练", "2");
        roleMap.put("前台", "3");
        roleMap.put("店长", "4");
        roleMap.put("会籍主管", "5");
        roleMap.put("教练主管", "6");
        roleMap.put("前台主管", "7");
        roleMap.put("操教", "8");
        roleMap.put("出纳", "9");
        roleMap.put("收银", "10");
        roleMap.put("兼职教练", "11");

        return roleMap.get(role);
    }

    private void showDate() {

        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, "选择日期");
        customDateAndTimePicker.setGoneHourAndMinute();
        customDateAndTimePicker.setMax((time.month + 1),time.monthDay);
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                selectDate = customDateAndTimePicker.getDateStringF();
                tv_date.setText(selectDate);
                initScoreData(selectDate,role,target_role_type,null);
                initBusData(selectDate,"1",null);
                initBusData(selectDate,"2",null);
                initBusData(selectDate,"3",null);

            }
        });

        customDateAndTimePicker.showWindow();

    }

    private void initHost() {

        time = new Time();
        time.setToNow();
        nowDate = time.year +"-"+(time.month+1)+"-"+ time.monthDay;
        selectDate = nowDate;
        gson = new Gson();
        String role_type = getIntent().getStringExtra("role_type");
        role = getCurrentroleType(role_type);
        target_role_type = getIntent().getStringExtra("target_role_type");


    }

    //业绩报表
    private void initScoreData(final String date, final String current_role_type, final String target_role_type, final String employee_id) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.SCORESTATISTICSREPORT), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                ReportResultBean reportResultBean = gson.fromJson(s, ReportResultBean.class);
                if(reportResultBean!=null){
                    mv_01.setAdapter(new MyListViewAdapter(reportResultBean.getData()));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("mDate",date);
                map.put("current_role_type",current_role_type);
                map.put("target_role_type",target_role_type);
                if(employee_id!=null){
                    map.put("employee_id",employee_id);
                }

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
                    if(reportResultBean!=null){
                        if("1".equals(current_role_type)){
                            mv_02.setAdapter(new MyListViewAdapter(reportResultBean.getData()));
                        }else if("2".equals(current_role_type)){
                            mv_03.setAdapter(new MyListViewAdapter(reportResultBean.getData()));
                        }else if("3".equals(current_role_type)){
                            mv_04.setAdapter(new MyListViewAdapter(reportResultBean.getData()));
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    LogUtil.i(volleyError.toString());
                }
            }){

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> map = new HashMap<>();
                    map.put("mDate",date);
                    map.put("current_role_type",current_role_type);
                    //map.put("target_role_type",target_role_type);
                    if(employee_id!=null){
                        map.put("employee_id",employee_id);
                    }

                    return map;
                }


            };

            MyApplication.getHttpQueues().add(stringRequest);

        }





    public  static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        XCRoundRectImageView img_touxiang;
        public ViewHolder(View itemView) {
            super(itemView);
            img_touxiang = itemView.findViewById(R.id.img_touxiang);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    private class MyListViewAdapter extends BaseAdapter {
        List<ReportResultBean.Data> dataList;

        MyListViewAdapter(List<ReportResultBean.Data> dataList){
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList==null? 0:dataList.size();
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

            View inflate = View.inflate(AllReportActivity.this, R.layout.report_item, null);
            TextView tv_name = inflate.findViewById(R.id.tv_name);
            TextView tv_today = inflate.findViewById(R.id.tv_today);
            TextView tv_thisMonth = inflate.findViewById(R.id.tv_thisMonth);
            TextView tv_total = inflate.findViewById(R.id.tv_total);
            TextView tv_thisMonthUnit = inflate.findViewById(R.id.tv_thisMonthUnit);
            ReportResultBean.Data data = dataList.get(position);
            tv_name.setText(data.getTitle());
            tv_today.setText(data.getToday()+data.getUnit());
            tv_thisMonth.setText(data.getEndOfToDay());
            tv_total.setText(data.getAllOfMonth()+data.getUnit());
            tv_thisMonthUnit.setVisibility(View.VISIBLE);
            tv_thisMonthUnit.setText(data.getUnit());
            return inflate;
        }
    }
}

package com.cn.danceland.myapplication.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shy on 2018/11/2 16:56
 * Email:644563767@qq.com
 */


public class AlertDialogSelectTimeActivity extends BaseActivity {

    String strSex = "1", strBirthday = "1990-12-10";
    TextView
            button;
    EditText et_nickname;

    View inflate;
    private final static int DATE_DIALOG = 0;
    private Calendar c = null;
    RequestQueue requestQueue;
    String id, strName, gender = "0", syear, smonth, sdate;//性别:1、男，2、女，3、未知，4、保密
    Data mData;
    Gson gson;
    LoopView loopview, lp_year, lp_month, lp_date;
    int year;
    String isleapyear;
    int daysByYearMonth;
    private ImageView iv_sex;
    private ArrayList<String> yearList;
    private ArrayList<String> monthList;
    private ArrayList<String> dateList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alertdialog_select_time);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//需要添加的语句
        initHost();
        intiView();
        setClick();
    }

    private void setClick() {
        findViewById(R.id.iv_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.iv_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strBirthday = syear + "-" + smonth + "-" + sdate;
                LogUtil.i(strBirthday);
                ToastUtils.showToastShort(strBirthday);
            }
        });
    }

    private void initHost() {

        Time time = new Time();
        time.setToNow();
        year = time.year;

        gson = new Gson();


        initDate();
    }

    private void intiView() {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateformat.format(System.currentTimeMillis());
        LogUtil.e(dateStr);
        //默认值
        setdate(dateStr);



    }

    private void setdate(String date) {
        String[] dates = date.split("-");

        int yearpos = yearList.indexOf(dates[0] + "年");
        lp_year.setCurrentPosition(yearpos);
        syear = yearList.get(yearpos).replace("年", "");

        int monthpos = monthList.indexOf(Integer.valueOf(dates[1]) + "月");
        lp_month.setCurrentPosition(monthpos);
        smonth = monthList.get(monthpos).replace("月", "");
        int datepos = dateList.indexOf(Integer.valueOf(dates[2]) + "日");
        lp_date.setCurrentPosition(datepos);
    //    LogUtil.e(datepos+"");
        sdate = dateList.get(datepos).replace("日", "");
    }


    private void initDate() {

//        inflate1 = LayoutInflater.from(SetRegisterInfoActivity.this).inflate(R.layout.birthdayselect, null);

        lp_year = findViewById(R.id.lp_year);
        lp_month = findViewById(R.id.lp_month);
        lp_date = findViewById(R.id.lp_date);


        yearList = new ArrayList<String>();
        monthList = new ArrayList<String>();
        dateList = new ArrayList<String>();
        int n = 1900;
        int len = year - n;
        for (int i = 0; i <= len; i++) {
            yearList.add((n + i) + "年");
        }
        for (int j = 0; j < 12; j++) {
            monthList.add((1 + j) + "月");
        }
        lp_year.setNotLoop();
        lp_date.setNotLoop();
        lp_month.setNotLoop();
        lp_year.setItems(yearList);
        lp_month.setItems(monthList);

        lp_year.setInitPosition(yearList.size() - 20);
        syear = yearList.get(yearList.size() - 20).replace("年", "");
        lp_month.setInitPosition(0);
        smonth = monthList.get(0).replace("月", "");
        sdate = "1";

        daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
        dateList.clear();
        for (int z = 1; z <= daysByYearMonth; z++) {
            dateList.add(z + "日");
        }
     //   LogUtil.e(dateList.toString());
        lp_date.setItems(dateList);

        //设置字体大小
        lp_year.setTextSize(24);
        lp_month.setTextSize(24);
        lp_date.setTextSize(24);
        lp_year.setCenterTextColor(ContextCompat.getColor(this,R.color.color_dl_black));
        lp_month.setCenterTextColor(ContextCompat.getColor(this,R.color.color_dl_black));
        lp_date.setCenterTextColor(ContextCompat.getColor(this,R.color.color_dl_black));
        lp_year.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_month.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_date.setOuterTextColor(Color.parseColor("#6d819c"));
        lp_year.setLineSpacingMultiplier(2f);
        lp_month.setLineSpacingMultiplier(2f);
        lp_date.setLineSpacingMultiplier(2f);
        lp_year.setItemsVisibleCount(5);
        lp_month.setItemsVisibleCount(5);
        lp_date.setItemsVisibleCount(5);
        lp_year.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                syear = yearList.get(index).replace("年", "");
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    dateList.add(z + "日");
                }
                lp_date.setItems(dateList);
            }
        });

        lp_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                smonth = monthList.get(index).replace("月", "");
                daysByYearMonth = TimeUtils.getDaysByYearMonth(Integer.valueOf(syear), Integer.valueOf(smonth));
                dateList.clear();
                for (int z = 1; z <= daysByYearMonth; z++) {
                    dateList.add(z + "日");
                }
                lp_date.setItems(dateList);
            }
        });

        lp_date.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                sdate = dateList.get(index).replace("日", "");
            }
        });
     //   LogUtil.e(dateList.toString());
    }

}

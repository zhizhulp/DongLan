package com.cn.danceland.myapplication.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by shy on 2018/10/17 15:18
 * Email:644563767@qq.com
 */


public class SetRegisterInfoActivity extends BaseActivity implements View.OnClickListener {
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
        setContentView(R.layout.activity_set_register_info);
        initHost();
        intiView();
        setClick();
    }

    private void initHost() {

        Time time = new Time();
        time.setToNow();
        year = time.year;

        gson = new Gson();


        mData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        id = SPUtils.getString(Constants.MY_USERID, null);
        initDate();
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
            if (j<9){
                monthList.add("0"+(1 + j) + "月");
            }else {
                monthList.add((1 + j) + "月");
            }

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
            if (z<10){
                dateList.add("0"+z + "日");
            }else  {
                dateList.add(z + "日");
            }
        }
        lp_date.setItems(dateList);

        //设置字体大小
        lp_year.setTextSize(24);

        lp_month.setTextSize(24);
        lp_date.setTextSize(24);
        lp_year.setCenterTextColor( ContextCompat.getColor(this,R.color.color_dl_yellow) );
        lp_month.setCenterTextColor(ContextCompat.getColor(this,R.color.color_dl_yellow));
        lp_date.setCenterTextColor(ContextCompat.getColor(this,R.color.color_dl_yellow));
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
                    if (z<10){
                        dateList.add("0"+z + "日");
                    }else  {
                        dateList.add(z + "日");
                    }

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
                    if (z<10){
                        dateList.add("0"+z + "日");
                    }else  {
                        dateList.add(z + "日");
                    }
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
    }

    private void intiView() {
        et_nickname = findViewById(R.id.et_nickname);
        iv_sex = findViewById(R.id.iv_sex);
        if (!TextUtils.isEmpty(mData.getPerson().getNick_name())) {
            et_nickname.setText(mData.getPerson().getNick_name());
        }
        if (TextUtils.equals(mData.getPerson().getGender(), "2")) {
            iv_sex.setBackgroundResource(R.drawable.img_sex_info_woman);
            gender = "2";
        }

        if (TextUtils.equals(mData.getPerson().getGender(), "1")) {
            iv_sex.setBackgroundResource(R.drawable.img_sex_info_man_);
            gender = "1";
        }


        if (!TextUtils.isEmpty(mData.getPerson().getBirthday())) {
            setdate(mData.getPerson().getBirthday());
        } else {
            //默认值
            setdate("2018-10-15");
        }

    }

    private void setdate(String date) {
        try {
            String[] dates = date.split("-");

            int yearpos = yearList.indexOf(dates[0] + "年");
            lp_year.setCurrentPosition(yearpos);
            syear = yearList.get(yearpos).replace("年", "");

            int monthpos = monthList.indexOf(dates[1] + "月");
            lp_month.setCurrentPosition(monthpos);
            smonth = monthList.get(monthpos).replace("月", "");
            int datepos = dateList.indexOf(dates[2] + "日");
            lp_date.setCurrentPosition(datepos);
            sdate = dateList.get(datepos).replace("日", "");
            LogUtil.i(yearpos + "AAA" + monthpos + "AAA" + datepos);
        }catch (Exception e){
            setdate("2018-10-15");
        }

    }

    private void setClick() {
        findViewById(R.id.dlbtn_commit).setOnClickListener(this);
        iv_sex.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==202){
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dlbtn_commit:
                if (TextUtils.isEmpty(et_nickname.getText().toString())) {
                    ToastUtils.showToastShort("请输入您的昵称");
                    return;
                }
                if (TextUtils.equals(gender, "0")) {
                    ToastUtils.showToastShort("请选择您的性别");
                    return;
                }
                strBirthday = syear + "-" + smonth + "-" + sdate;
                LogUtil.i(strBirthday);
                LogUtil.i(mData.getPerson().getBirthday());

                if (TextUtils.equals(strBirthday, "2018-10-15")) {
                    ToastUtils.showToastShort("请选择您生日");
                    return;
                }

                startActivityForResult(new Intent(SetRegisterInfoActivity.this, SetRegisterInfoSaveActivity.class)
                        .putExtra("gender", gender)
                        .putExtra("name", et_nickname.getText().toString())
                        .putExtra("birthday", strBirthday),201);


                break;
            case R.id.iv_sex:
                if (TextUtils.equals("1", gender)) {
                    iv_sex.setBackgroundResource(R.drawable.img_sex_info_woman);
                    gender = "2";
                } else if (TextUtils.equals("2", gender)) {
                    iv_sex.setBackgroundResource(R.drawable.img_sex_info_man_);
                    gender = "1";
                } else if (TextUtils.equals("0", gender)) {
                    iv_sex.setBackgroundResource(R.drawable.img_sex_info_man_);
                    gender = "1";
                }

                break;
            default:
                break;
        }
    }
}

package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ReadyTestBean;
import com.cn.danceland.myapplication.bean.RequsetFindUserBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.CustomDateAndTimePicker;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * 体测分析-搜索结果
 * Created by yxx on 2018-09-26.
 */

public class FitnessTestSearchResultActivity extends BaseActivity {
    private Context context;
    private DongLanTitleView title;
    private RoundImageView iv_avatar;
    private TextView name_tv;
    private TextView tel_tv;
    private TextView real_name_ev;
    private EditText birthday_ev;
    private EditText height_ev;
    private LinearLayout history_btn;
    private LinearLayout finess_btn;
    //    private TextView text_male;
//    private TextView text_female;
    private ImageView iv_sex;
    private TextView age_tv;

    private View inflate;
    private LoopView loopview;
    private AlertDialog.Builder alertdialog;
    private RequsetFindUserBean.Data requsetInfo;//前面搜索到的对象

    private Gson gson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_search_results);
        context = this;
        gson = new Gson();
        requsetInfo = (RequsetFindUserBean.Data) getIntent().getSerializableExtra("requsetInfo");//前面搜索到的对象
        initView();
        initData();
    }

    private void initView() {
        title = findViewById(R.id.title);
        title.setTitle("体测分析");
        iv_avatar = findViewById(R.id.iv_avatar);
        name_tv = findViewById(R.id.name_tv);
        tel_tv = findViewById(R.id.tel_tv);
        real_name_ev = findViewById(R.id.real_name_ev);
        birthday_ev = findViewById(R.id.birthday_ev);
        height_ev = findViewById(R.id.height_ev);
        history_btn = findViewById(R.id.history_btn);
        finess_btn = findViewById(R.id.finess_btn);
//        text_male = findViewById(R.id.text_male);
//        text_female = findViewById(R.id.text_female);
        iv_sex = findViewById(R.id.iv_sex);
        age_tv = findViewById(R.id.age_tv);

        inflate = LayoutInflater.from(this).inflate(R.layout.timeselect, null);
        alertdialog = new AlertDialog.Builder(this);
        loopview = inflate.findViewById(R.id.loopview);

        history_btn.setOnClickListener(onClickListener);
        finess_btn.setOnClickListener(onClickListener);
        birthday_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate();
            }
        });
        height_ev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWH();
            }
        });
    }

    private void initData() {
        RequestOptions options2 = new RequestOptions().placeholder(R.drawable.img_my_avatar);
        if (requsetInfo.getAvatar_url() != null && requsetInfo.getAvatar_url().length() > 0) {//头像
            Glide.with(context).load(requsetInfo.getAvatar_url()).apply(options2).into(iv_avatar);
        }
        name_tv.setText(requsetInfo.getNick_name());
        tel_tv.setText(requsetInfo.getPhone_no());
        real_name_ev.setText(requsetInfo.getCname());
        birthday_ev.setText(requsetInfo.getBirthday());//年龄
        height_ev.setText(requsetInfo.getHeight());
        if (requsetInfo.getBirthday() != null) {
            int age = TimeUtils.getAgeFromBirthTime(new Date(TimeUtils.date2TimeStamp(requsetInfo.getBirthday(), "yyyy-MM-dd")));
            age_tv.setText(age + "岁");//年龄
        }
        if (requsetInfo.getGender() == 1) {//性别 1男  2女
            iv_sex.setImageResource(R.drawable.img_sex1);
//            text_male.setBackgroundResource(R.drawable.male_blue);
//            text_female.setBackgroundResource(R.drawable.female_gray);
        } else {
            iv_sex.setImageResource(R.drawable.img_sex2);
//            text_male.setBackgroundResource(R.drawable.male_gray);
//            text_female.setBackgroundResource(R.drawable.female_blue);
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.history_btn://历史记录
                    startActivity(new Intent(context, FitnessTestHistoryActivity.class).putExtra("requsetInfo", requsetInfo));
                    break;
                case R.id.finess_btn://体测分析
                    commit();
                    break;
            }
        }
    };

    private void commit() {
        ReadyTestBean readyTestBean = new ReadyTestBean();
        readyTestBean.setCname(name_tv.getText().toString());
        readyTestBean.setGender(requsetInfo.getGender());
        if (birthday_ev.getText().toString().isEmpty()) {
            ToastUtils.showToastShort("请选择生日");
            return;
        } else {
            readyTestBean.setBirthday(birthday_ev.getText().toString());
        }

        if (height_ev.getText().toString().isEmpty()) {
            ToastUtils.showToastShort("请选择身高");
            return;
        } else {
            readyTestBean.setHeight(Float.valueOf(height_ev.getText().toString()));
        }
        readyTestBean.setId(Integer.valueOf(requsetInfo.getPerson_id()));
        String strbean = gson.toJson(readyTestBean);

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.REAYTEST), strbean, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject.toString().contains("true")) {
                    startActivity(new Intent(context, FitnessTestNoticeActivity.class).putExtra("requsetInfo", requsetInfo));
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

    private void showDate() {

        final CustomDateAndTimePicker customDateAndTimePicker = new CustomDateAndTimePicker(this, "请选择生日");
        customDateAndTimePicker.setGoneHourAndMinute();//
        customDateAndTimePicker.showWindow();
        customDateAndTimePicker.setDialogOnClickListener(new CustomDateAndTimePicker.OnClickEnter() {
            @Override
            public void onClick() {
                String dateString = customDateAndTimePicker.getDateStringF();
                birthday_ev.setText(dateString);
            }
        });

    }

    private void showWH() {
        int n;
        final ArrayList<String> arrayList = new ArrayList<String>();
        ViewGroup parent = (ViewGroup) inflate.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        for (int i = 0; i < 71; i++) {
            n = 150 + i;
            arrayList.add(n + "");
        }

        loopview.setNotLoop();
        loopview.setItems(arrayList);
        //设置初始位置
        loopview.setInitPosition(0);
        loopview.setTextSize(14);
        loopview.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                height_ev.setText(arrayList.get(index));
            }
        });
        alertdialog.setTitle("选择身高");

        alertdialog.setView(inflate);
        alertdialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertdialog.show();
    }

}

package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.MotionTotalBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.RoundImageView;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的 运动数据-数据总览
 * Created by yxx on 2018-12-04.
 */

public class DataScreenActivity extends BaseActivity {
    private Context context;
    private RoundImageView circle_image;
    private TextView tv_nick_name, tv_male_age, tv_phone;
    private ImageView iv_sex;
    private RelativeLayout rl_error;

    private TextView tv_movement_time;//运动时长
    private TextView tv_movement_distance;//运动距离
    private TextView tv_average_velocity;//平均速度
    private TextView tv_average_heart1;//平均心率 有氧
    private TextView tv_number;//运动次数
    private TextView tv_group_number;//运动组数
    private TextView tv_average_weight;//平均重量
    private TextView tv_average_heart2;//平均心率 无氧

    private Data myInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_screen);
        context = this;
        myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        initView();
        initHeaderData();
        initData();
    }

    private void initView() {
        circle_image = findViewById(R.id.circle_image);
        tv_nick_name = findViewById(R.id.tv_nick_name);
        tv_male_age = findViewById(R.id.tv_male_age);
        tv_phone = findViewById(R.id.tv_phone);
        iv_sex = findViewById(R.id.iv_sex);
        rl_error = findViewById(R.id.rl_no_info);

        tv_movement_time = findViewById(R.id.tv_movement_time);
        tv_movement_distance = findViewById(R.id.tv_movement_distance);
        tv_average_velocity = findViewById(R.id.tv_average_velocity);
        tv_average_heart1 = findViewById(R.id.tv_average_heart1);
        tv_number = findViewById(R.id.tv_number);
        tv_group_number = findViewById(R.id.tv_group_number);
        tv_average_weight = findViewById(R.id.tv_average_weight);
        tv_average_heart2 = findViewById(R.id.tv_average_heart2);
    }

    private void initHeaderData() {
        if (myInfo != null) {
            Glide.with(context).load(myInfo.getPerson().getSelf_avatar_path()).into(circle_image);
            tv_nick_name.setText(myInfo.getPerson().getNick_name());
            if (TextUtils.equals(myInfo.getPerson().getGender(), "1")) {
                iv_sex.setImageResource(R.drawable.img_sex1);
            } else if (TextUtils.equals(myInfo.getPerson().getGender(), "2")) {
                iv_sex.setImageResource(R.drawable.img_sex2);
            } else {
                iv_sex.setVisibility(View.INVISIBLE);
            }

            if (myInfo.getPerson().getBirthday() != null) {
                Time time = new Time();
                time.setToNow();
                int age = time.year - Integer.valueOf(myInfo.getPerson().getBirthday().split("-")[0]);
                tv_male_age.setText(age + " 岁");
            }
            tv_phone.setText(myInfo.getPerson().getPhone_no());
        }
    }

    private void initData() {
        MyStringRequest request = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.QUERY_SH_TOTAL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s.toString());
                MotionTotalBean datainfo = new Gson().fromJson(s.toString(), MotionTotalBean.class);
                if (datainfo.getSuccess() && datainfo.getCode().equals("0")) {
                    tv_movement_time.setText((Integer.valueOf(datainfo.getData().getTime()) / 60) + "分" + (Integer.valueOf(datainfo.getData().getTime()) % 60) + "秒");//运动时长 秒
                    float kmf = (Float.valueOf(datainfo.getData().getDistance())) / (float) 1000.00;//100步长  1000km
                    DecimalFormat fnum = new DecimalFormat("##0.00");
                    DecimalFormat fnum2 = new DecimalFormat("##0");
                    String km = fnum.format(kmf);
                    tv_movement_distance.setText(km + "km");//运动距离 km
                    tv_average_velocity.setText(fnum2.format(Float.valueOf(datainfo.getData().getAvg_speed())) + "km/h");//平均速度
                    tv_average_heart1.setText(datainfo.getData().getAerobic_heart() + "bpm");//平均心率 有氧
                    tv_number.setText(datainfo.getData().getTimes() + "次");//运动次数
                    tv_group_number.setText(datainfo.getData().getGroup_count() + "组");//运动组数
                    if (datainfo.getData().getAvg_heavy() != null) {
                        tv_average_weight.setText(fnum2.format(Float.valueOf(datainfo.getData().getAvg_heavy())) + "kg");//平均重量
                    } else {
                        tv_average_weight.setText(0 + "kg");//平均重量
                    }
                    tv_average_heart2.setText(datainfo.getData().getAnaerobic_heart() + "bpm");//平均心率 无氧
                    rl_error.setVisibility(View.GONE);
                } else {
                    rl_error.setVisibility(View.VISIBLE);
                    ToastUtils.showToastLong(datainfo.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                rl_error.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("person_id", myInfo.getPerson().getId());
                LogUtil.i(map.toString());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);
    }
}

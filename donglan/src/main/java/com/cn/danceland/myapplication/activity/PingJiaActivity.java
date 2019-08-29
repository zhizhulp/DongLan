package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
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
import com.cn.danceland.myapplication.bean.PingJiaCon;
import com.cn.danceland.myapplication.bean.PingJiaResultBean;
import com.cn.danceland.myapplication.bean.SiJiaoRecordBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.GlideRoundTransform;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by feng on 2018/3/19.
 */

public class PingJiaActivity extends BaseActivity {

    ImageView img_user;
    TextView tv_user, tv_role, tv_status, tv_time;
    LinearLayout ll_detail, ll_sixin, ll_phone, ll_commmit;
    ScaleRatingBar jiaolian_ratingbar, kecheng_ratingbar, changdi_ratingbar;
    float jiaolianscore, kechengscore, changdiscore;
    SiJiaoRecordBean.Content item;
    Gson gson;
    ImageView pingjia_back;
    String currentTime;
    EditText my_pingjia;
    String course_category;
    int evaluate_id;
    private TextView tv_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pingjia_activity);
        initHost();
        initView();

    }

    private void initHost() {

        item = (SiJiaoRecordBean.Content) getIntent().getSerializableExtra("item");
        course_category = getIntent().getStringExtra("course_category");
        evaluate_id = getIntent().getIntExtra("evaluate_id", 0);

        currentTime = TimeUtils.timeStamp2Date(System.currentTimeMillis() + "", "yyyy-MM-dd HH:mm:ss");

        gson = new Gson();
    }

    private void initView() {

        img_user = findViewById(R.id.img_user);

        RequestOptions options = new RequestOptions().transform(new GlideRoundTransform(this, 10)).placeholder(R.drawable.img_avatar1).error(R.drawable.img_avatar1);
        Glide.with(this).load(item.getEmployee_avatar_url()).apply(options).into(img_user);

        tv_user = findViewById(R.id.tv_user);
        //tv_role = findViewById(R.id.tv_role);
        ll_detail = findViewById(R.id.ll_detail);
        ll_sixin = findViewById(R.id.ll_sixin);
        ll_phone = findViewById(R.id.ll_phone);
        tv_status = findViewById(R.id.tv_status);
        tv_time = findViewById(R.id.tv_time);
        tv_phone = findViewById(R.id.tv_phone);
        jiaolian_ratingbar = findViewById(R.id.jiaolian_ratingbar);
        kecheng_ratingbar = findViewById(R.id.kecheng_ratingbar);
        changdi_ratingbar = findViewById(R.id.changdi_ratingbar);
        ll_commmit = findViewById(R.id.ll_commmit);
        my_pingjia = findViewById(R.id.my_pingjia);

        tv_user.setText(item.getEmployee_name());
        if ("2".equals(item.getEmployee_gender())) {
            ImageView iv_sex=findViewById(R.id.iv_sex);

            iv_sex.setImageResource(R.drawable.img_sex2);
        }
        if (!TextUtils.isEmpty(item.getEmployee_birthday())){
            TextView tv_age=findViewById(R.id.tv_age);
            LogUtil.i(item.getEmployee_birthday());
            if (System.currentTimeMillis()<TimeUtils.date2TimeStamp(item.getEmployee_birthday(),"yyyy-MM-dd")){
                tv_age.setText(0+"岁");
            }else {
                tv_age.setText(TimeUtils.getAgeFromBirthTime(new Date(TimeUtils.date2TimeStamp(item.getEmployee_birthday(),"yyyy-MM-dd")))+"岁");
            }


        }
        tv_phone.setText("联系电话："+item.getPhone_no());
        setClick();

    }

    private void getPingJiaData(final int evaluate_id) {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.FINDPINGJIA) + evaluate_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                PingJiaResultBean pingJiaResultBean = gson.fromJson(s, PingJiaResultBean.class);
                if (pingJiaResultBean.getSuccess()) {
                    PingJiaResultBean.Data data = pingJiaResultBean.getData();
                    my_pingjia.setText(data.getContent());
                    tv_time.setText(TimeUtils.timeStamp2Date(data.getCreate_date(), "yyyy-MM-dd HH:mm:ss"));
                    jiaolian_ratingbar.setRating(data.getEmployee_score());
                    kecheng_ratingbar.setRating(data.getCourse_type_score());
                    if (data.getRoom_score() != null) {
                        changdi_ratingbar.setRating(data.getRoom_score());
                    }
                } else {
                    ToastUtils.showToastShort("获取评价结果失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
            }
        }) {


        };

        MyApplication.getHttpQueues().add(stringRequest);

    }

    private void setClick() {

        jiaolian_ratingbar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                jiaolianscore = v;
            }
        });
        kecheng_ratingbar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                kechengscore = v;
            }
        });
        changdi_ratingbar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                changdiscore = v;
            }
        });

        ll_commmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();
            }
        });
        if (evaluate_id != 0) {
            ll_commmit.setVisibility(View.GONE);
            tv_status.setText("已评价");
            //查询评价记录
            getPingJiaData(evaluate_id);
            jiaolian_ratingbar.setFocusable(false);
            jiaolian_ratingbar.setClickable(false);
            jiaolian_ratingbar.setScrollable(false);
            kecheng_ratingbar.setFocusable(false);
            kecheng_ratingbar.setScrollable(false);
            kecheng_ratingbar.setClickable(false);
            changdi_ratingbar.setFocusable(false);
            changdi_ratingbar.setClickable(false);
            changdi_ratingbar.setScrollable(false);
            my_pingjia.setFocusable(false);

        } else {
            tv_time.setText(currentTime);
            ll_commmit.setVisibility(View.VISIBLE);
        }

    }

    private void commit() {

        String text = my_pingjia.getText().toString();

        PingJiaCon pingJiaCon = new PingJiaCon();

        pingJiaCon.setBranch_id(item.getBranch_id());
        pingJiaCon.setCourse_type_id(item.getCourse_type_id());
        pingJiaCon.setCourse_type_score(kechengscore);
        pingJiaCon.setEmployee_id(item.getEmployee_id());
        pingJiaCon.setEmployee_score(jiaolianscore);
        pingJiaCon.setContent(text);
        pingJiaCon.setBus_id(item.getId());
        if ("1".equals(course_category)) {
            pingJiaCon.setType("1");
        } else if ("2".equals(course_category)) {
            pingJiaCon.setType("2");
        }


        String s = gson.toJson(pingJiaCon);

        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.PINGJIA), s, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (jsonObject.toString().contains("true")) {
                    ToastUtils.showToastShort("评价成功！");
                    finish();
                } else {
                    ToastUtils.showToastShort("评价失败！请检查手机网络");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("zzf", volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }


}

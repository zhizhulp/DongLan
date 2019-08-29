package com.cn.danceland.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.ConsultBean;
import com.cn.danceland.myapplication.bean.PostDataBean;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyJsonObjectRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

/**
 * AlertDialog  有×的
 * Created by ${yxx} on 2018/8/31.
 */

public class AlertDialogActivity extends BaseActivity {
    private Context context;
    private TextView dialog_title_tv;
    private TextView gym_join_tv;
    private TextView gym_jteamwork_tv;
    private TextView gym_admin_tv;
    private TextView btn_consult;
    private LinearLayout btn_consult_layout;
    private ImageView close_img;//关闭
    private LinearLayout dialog_consult_layout;//咨询
    private LinearLayout dialog_recommend_layout;//推荐

    private TextView text_male;
    private TextView text_female;
    private EditText name_ev;
    private EditText tel_ev;
    private EditText remark_ev;

    private String from = "咨询列表";//来自哪页 咨询列表 推荐列表
    private String type = "1";//意向类型
    private String sub_type = "-1";//意向子类型

    private String strSex = "男";//男女

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teamwork_type);
        context = this;
        type = getIntent().getStringExtra("type");
        from = getIntent().getStringExtra("from");

        initView();
    }

    private void initView() {
        dialog_consult_layout = findViewById(R.id.dialog_consult_layout);
        dialog_recommend_layout = findViewById(R.id.dialog_recommend_layout);
        dialog_title_tv = findViewById(R.id.dialog_title_tv);
        gym_join_tv = findViewById(R.id.gym_join_tv);
        gym_jteamwork_tv = findViewById(R.id.gym_jteamwork_tv);
        gym_admin_tv = findViewById(R.id.gym_admin_tv);
        btn_consult = findViewById(R.id.btn_consult);
        btn_consult_layout = findViewById(R.id.btn_consult_layout);
        close_img = findViewById(R.id.close_img);

        text_male = findViewById(R.id.text_male);
        text_female = findViewById(R.id.text_female);
        name_ev = findViewById(R.id.name_ev);
        tel_ev = findViewById(R.id.tel_ev);
        remark_ev = findViewById(R.id.remark_ev);

        if (!TextUtils.isEmpty(type) && type.equals("1")) {
            dialog_title_tv.setText(context.getResources().getString(R.string.consult_join_type_text));//加盟 合作类型
            gym_join_tv .setText(context.getString(R.string.consult_gym_join_text));
            gym_jteamwork_tv .setText(context.getString(R.string.consult_gym_teamwork_text));
            gym_admin_tv .setText(context.getString(R.string.consult_gym_admin_text));
        } else if (!TextUtils.isEmpty(type) && type.equals("3")) {
            dialog_title_tv.setText(context.getResources().getString(R.string.consult_buy_type_text));//购买
            gym_join_tv .setText(context.getString(R.string.consult_buy_join_text));
            gym_jteamwork_tv .setText(context.getString(R.string.consult_buy_teamwork_text));
            gym_admin_tv .setText(context.getString(R.string.consult_buy_admin_text));
        } else if (!TextUtils.isEmpty(type) && type.equals("2")) {
            dialog_title_tv.setText(context.getResources().getString(R.string.consult_train_type_text));//培训
            gym_join_tv .setText(context.getString(R.string.consult_train_join_text));
            gym_jteamwork_tv .setText(context.getString(R.string.consult_train_teamwork_text));
            gym_admin_tv .setText(context.getString(R.string.consult_train_admin_text));
        }
        LogUtil.i("type" + type);

        if (!TextUtils.isEmpty(from) && from.equals("咨询列表")) {
            dialog_consult_layout.setVisibility(View.VISIBLE);
            dialog_recommend_layout.setVisibility(View.GONE);
        } else {
            dialog_consult_layout.setVisibility(View.GONE);
            dialog_recommend_layout.setVisibility(View.VISIBLE);

            dialog_title_tv.setText(context.getResources().getString(R.string.recommend_data_text));
            btn_consult.setText(context.getResources().getString(R.string.recommend_text));
        }
        LogUtil.i("from" + from);

        gym_join_tv.setOnClickListener(onClickListener);
        gym_jteamwork_tv.setOnClickListener(onClickListener);
        gym_admin_tv.setOnClickListener(onClickListener);
        btn_consult_layout.setOnClickListener(onClickListener);
        close_img.setOnClickListener(onClickListener);

        text_male.setOnClickListener(onClickListener);
        text_female.setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                // 意向子类型
                case R.id.gym_join_tv:
                    sub_type = "1";
                    gym_join_tv.setTextColor(context.getResources().getColor(R.color.white));
                    gym_jteamwork_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_admin_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_join_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_shade_pink_bg));
                    gym_jteamwork_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    gym_admin_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    break;
                case R.id.gym_jteamwork_tv:
                    sub_type = "2";
                    gym_join_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_jteamwork_tv.setTextColor(context.getResources().getColor(R.color.white));
                    gym_admin_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_join_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    gym_jteamwork_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_shade_pink_bg));
                    gym_admin_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    break;
                case R.id.gym_admin_tv:
                    sub_type = "3";
                    gym_join_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_jteamwork_tv.setTextColor(context.getResources().getColor(R.color.colorGray22));
                    gym_admin_tv.setTextColor(context.getResources().getColor(R.color.white));
                    gym_join_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    gym_jteamwork_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_bg_gray_empty_deep));
                    gym_admin_tv.setBackground(context.getResources().getDrawable(R.drawable.btn_shade_pink_bg));
                    break;
                case R.id.btn_consult_layout:
                    if (!TextUtils.isEmpty(from) && from.equals("咨询列表")) {
                        if (sub_type.equals("-1")) {
                            Toast.makeText(context, "请选择", Toast.LENGTH_SHORT).show();
                        } else {
                            postConsultData();
                        }
                    } else {
                        if (TextUtils.isEmpty(name_ev.getText().toString())) {//姓名不能空
                            ToastUtils.showToastShort(context.getResources().getString(R.string.name_hint_text));
                            return;
                        }
                        if (StringUtils.isAllNumeric(name_ev.getText().toString())) {//姓名不能全数字
                            ToastUtils.showToastShort(context.getResources().getString(R.string.name_no_numeric_text));
                            return;
                        }
                        if (StringUtils.isFirstNumeric(name_ev.getText().toString())) {//姓名不能以数字开头
                            ToastUtils.showToastShort(context.getResources().getString(R.string.name_no_numeric_first_text));
                            return;
                        }
                        if (TextUtils.isEmpty(tel_ev.getText().toString())) {//电话不能空
                            ToastUtils.showToastShort(context.getResources().getString(R.string.tel_hint_text));
                            return;
                        }
                        if (!PhoneFormatCheckUtils.isPhoneLegal(tel_ev.getText().toString())) {//判断电话号码是否合法
                            ToastUtils.showToastShort(context.getResources().getString(R.string.tel_error_hint_text));
                            return;
                        }
                        if (TextUtils.isEmpty(remark_ev.getText().toString())) {//备注不能空
                            ToastUtils.showToastShort(context.getResources().getString(R.string.remark_hint_text));
                            return;
                        }
                        postRecommendData();
                    }
                case R.id.close_img:
                    AlertDialogActivity.this.finish();
                    break;
                case R.id.text_male:
                    text_male.setBackgroundResource(R.drawable.male_blue);
                    text_female.setBackgroundResource(R.drawable.img_sex2);
                    strSex = "男";
                    break;
                case R.id.text_female:
                    text_male.setBackgroundResource(R.drawable.img_sex1);
                    text_female.setBackgroundResource(R.drawable.female_blue);
                    strSex = "女";
                    break;
            }
        }
    };

    private void postConsultData() {
        ConsultBean consultBean = new ConsultBean();
        consultBean.setType(type);
        consultBean.setSub_type(sub_type);
        LogUtil.i("提交参数：" + consultBean.toString());
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_SAVE_CONSULT)
                , new Gson().toJson(consultBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("提交返回" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    PostDataBean bean = new Gson().fromJson(jsonObject.toString(), PostDataBean.class);
                    if (bean.getCode().equals("0")) {
                        LogUtil.i("提交成功");
                        Toast.makeText(context, "提交成功！工作人员将会在1-2工作日内联系您，请保持电话畅通。", Toast.LENGTH_SHORT).show();
                        setResult(1, new Intent(context, ConsultListActivity.class));
                        finish();
                    } else {
                        LogUtil.i("提交失败");
                        Toast.makeText(context, bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    LogUtil.i("提交失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }

    private void postRecommendData() {
        ConsultBean consultBean = new ConsultBean();
        consultBean.setType(type);//后面icon展示用
        consultBean.setCname(name_ev.getText().toString());
        consultBean.setGender(strSex);
        consultBean.setPhone_no(tel_ev.getText().toString());
        consultBean.setRemark(remark_ev.getText().toString());
        LogUtil.i("提交参数：" + consultBean.toString());
        MyJsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, Constants.plus(Constants.QUERY_SAVE_RECOMMEND)
                , new Gson().toJson(consultBean), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                LogUtil.i("提交返回" + jsonObject.toString());
                if (jsonObject.toString().contains("true")) {
                    PostDataBean bean = new Gson().fromJson(jsonObject.toString(), PostDataBean.class);
                    if (bean.getCode().equals("0")) {
                        LogUtil.i("提交成功");
                        Toast.makeText(context, "提交成功！工作人员将会在1-2工作日内联系该用户。", Toast.LENGTH_SHORT).show();
                        setResult(1, new Intent(context, RecommendListActivity.class));
                        finish();
                    } else {
                        LogUtil.i("提交失败");
                        Toast.makeText(context, bean.getErrorMsg(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    LogUtil.i("提交失败");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e("onErrorResponse", volleyError.toString());
            }
        });
        MyApplication.getHttpQueues().add(jsonObjectRequest);
    }
}

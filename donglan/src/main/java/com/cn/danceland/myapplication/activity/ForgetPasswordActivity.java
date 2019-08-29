package com.cn.danceland.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.RequestBindBean;
import com.cn.danceland.myapplication.bean.RequestInfoBean;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.view.ContainsEmojiEditText;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2018/8/1 11:43
 * Email:644563767@qq.com
 * 忘记密码
 */


public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Spinner mSpinner;
    private TextView mTvGetsms, tv_sex, tv_zhengjian;
    private EditText mEtSms, et_zhengjian;
    private ContainsEmojiEditText et_name;
    int sex = 0;
    String zhengjian;
    private EditText mEtPhone;
    private int recLen = 30;//倒计时时长
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            recLen--;
            mTvGetsms.setText("" + recLen + "秒后重试");
            handler.postDelayed(this, 1000);

            if (recLen <= 0) {
                //倒计时结束后设置可以点击
                mTvGetsms.setFocusable(true);
                mTvGetsms.setClickable(true);
                mTvGetsms.setTextColor(Color.WHITE);
                mTvGetsms.setText("获取验证码");
                handler.removeCallbacks(runnable);

            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_device_no_pswd);
        initView();
    }


    private void initView() {
        DongLanTitleView titleView = findViewById(R.id.dl_title);
        titleView.setTitle("忘记密码");
        mTvGetsms = findViewById(R.id.tv_getsms);
        et_name = findViewById(R.id.et_name);
        et_zhengjian = findViewById(R.id.et_zhengjian);
        mTvGetsms.setOnClickListener(this);
        mEtPhone = findViewById(R.id.et_phone);
        tv_sex = findViewById(R.id.tv_sex);
        tv_sex.setOnClickListener(this);
        tv_zhengjian = findViewById(R.id.tv_zhengjian);
        tv_zhengjian.setOnClickListener(this);
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //    修改手机号后清空验证码
                mEtSms.setText("");
                smsCode = "";
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEtSms = findViewById(R.id.et_sms);
        findViewById(R.id.dlbtn_commit).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_getsms:


                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断电话号码是否合法
                if (!PhoneFormatCheckUtils.isPhoneLegal(mEtPhone.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                //设置不能点击
                mTvGetsms.setFocusable(false);
                mTvGetsms.setClickable(false);
                mTvGetsms.setTextColor(Color.parseColor("#e9e9e9"));
                recLen = 30;
                mTvGetsms.setText("" + recLen + "秒后重试");
                //设置倒计时
                handler.postDelayed(runnable, 1000);

                getSMS();
                break;
            case R.id.dlbtn_commit:
                //判断验证码是否为空
                if (TextUtils.isEmpty(mEtSms.getText().toString().trim())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(et_name.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(zhengjian)) {
                    Toast.makeText(ForgetPasswordActivity.this, "选择证件类型", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(et_zhengjian.getText().toString())) {
                    Toast.makeText(ForgetPasswordActivity.this, "请输入证件号码", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (TextUtils.equals(zhengjian, "身份证") && !TextUtils.isEmpty(et_zhengjian.getText().toString())) {

                        try {
                            LogUtil.i(et_zhengjian.getText().toString());
                            if (!PhoneFormatCheckUtils.isIDNumber(et_zhengjian.getText().toString())) {
                                ToastUtils.showToastShort("身份证不合法");
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                bindDevice();
                break;
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_sex://选择性别
                showListDialog();
                break;
            case R.id.tv_zhengjian://选择证件
                showCertificate_type();
                break;
            default:
                break;
        }
    }

    private String smsCode = "";

    /**
     * 获取短信验证码
     */
    private void getSMS() {

        String params = mEtPhone.getText().toString().trim();

        String url = Constants.plus(Constants.GET_SMS_URL) + params;

        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = new RequestInfoBean();
                requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    smsCode = requestInfoBean.getData().getVerCode();

                        if (Constants.DEV_CONFIG){
                    ToastUtils.showToastLong("验证码是："
                            + smsCode);
                         }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        });
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("get_sms");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }

    private void showCertificate_type() {
        final String[] items = {"身份证", "军官证", "驾驶证"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                zhengjian = items[which];
                tv_zhengjian.setText(items[which]);
            }
        });
        listDialog.show();
    }


    private void showListDialog() {
        final String[] items = {"男", "女"};
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        //listDialog.setTitle("我是一个列表Dialog");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:

                        tv_sex.setText("男");
                        sex = 1;
                        break;
                    case 1:

                        tv_sex.setText("女");
                        sex = 2;
                        break;
                    default:
                        break;
                }
            }
        });
        listDialog.show();
    }

    private void bindDevice() {

        String url = Constants.plus(Constants.FORGET_PWD_URL);
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);

                Gson gson = new Gson();
                RequestBindBean requestInfoBean = gson.fromJson(s, RequestBindBean.class);
                if (requestInfoBean.getSuccess()) {
                    //成功
                    finish();
                    startActivity(new Intent(ForgetPasswordActivity.this, SetPswdActivity.class).putExtra("id",requestInfoBean.getData().getId()));
                } else {
                    //失败

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
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", mEtPhone.getText().toString().trim());
                //         map.put("pwd", MD5Utils.encode(mEtPsw.getText().toString().trim()));
                map.put("validateCode", mEtSms.getText().toString());
                map.put("name", et_name.getText().toString());
                map.put("gender", sex + "");
                map.put("certificateType", zhengjian);
                map.put("identityCard", et_zhengjian.getText().toString());
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
                return map;
            }

//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//
//                map.put("Authorization", "Bearer+"+ SPUtils.getString(Constants.MY_TOKEN,null));
//                return map;
//            }
        };

        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("resetPsw");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }
}

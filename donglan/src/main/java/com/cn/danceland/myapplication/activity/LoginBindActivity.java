package com.cn.danceland.myapplication.activity;

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
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestInfoBean;
import com.cn.danceland.myapplication.bean.RequestLoginInfoBean;
import com.cn.danceland.myapplication.bean.RequestSimpleBean;
import com.cn.danceland.myapplication.bean.RequsetUserDynInfoBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2017/9/22.
 */

public class LoginBindActivity extends BaseActivity implements View.OnClickListener {
    private Spinner mSpinner;
    private TextView mTvGetsms;
    private EditText mEtSms;
    private String smsCode = "";
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
        setContentView(R.layout.activity_bind_device);
        initView();
    }

    private void initView() {
        String phone=getIntent().getStringExtra("phone");

        mTvGetsms = findViewById(R.id.tv_getsms);
        mTvGetsms.setOnClickListener(this);
        mEtPhone = findViewById(R.id.et_phone);
        if (!TextUtils.isEmpty(phone)){
            mEtPhone.setText(phone);
            mEtPhone.setClickable(false);
            mEtPhone.setFocusable(false);
        }

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
        findViewById(R.id.iv_back).setOnClickListener(this);


    }

    //返回
    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getsms:
                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    Toast.makeText(LoginBindActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断电话号码是否合法
                if (!PhoneFormatCheckUtils.isPhoneLegal(mEtPhone.getText().toString())) {
                    Toast.makeText(LoginBindActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }

                getSMS();

                //设置不能点击
                mTvGetsms.setFocusable(false);
                mTvGetsms.setClickable(false);
                mTvGetsms.setTextColor(Color.parseColor("#e9e9e9"));
                recLen = 30;
                mTvGetsms.setText("" + recLen + "秒后重试");
                //设置倒计时
                handler.postDelayed(runnable, 1000);


                break;
            case R.id.dlbtn_commit:
                //判断验证码是否为空
                if (TextUtils.isEmpty(mEtSms.getText().toString().trim())) {
                    Toast.makeText(LoginBindActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                //  login_by_phone_url(mEtPhone.getText().toString().trim());
                bindDevice(mEtPhone.getText().toString().trim(), mEtSms.getText().toString().trim());
                break;
            case R.id.iv_back://返回
                startActivity(new Intent(LoginBindActivity.this, LoginActivity.class));
                finish();
                break;
            default:
                break;


        }
    }


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


    private void bindDevice(final String phone, final String validateCode) {
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.BIND_DEVICE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean simpleBean = new Gson().fromJson(s, RequestSimpleBean.class);
                if (simpleBean.getSuccess()){
                  //  ToastUtils.showToastShort("绑定成功");
                    EventBus.getDefault().post(new StringEvent("绑定成功",1011));
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("validateCode", validateCode);
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);

    }

    /***
     *
     * @param phone
     */
    private void login_by_phone_url(final String phone) {


        String url = Constants.plus(Constants.LOGIN_BY_PHONE_URL);

        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
//                dialog.dismiss();
                LogUtil.i(s);

                Gson gson = new Gson();

                RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);
                LogUtil.i(loginInfoBean.toString());
                if (loginInfoBean.getSuccess()) {

                    SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id

                    SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                    //   SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                    if (loginInfoBean.getData().getMember() != null) {
                        SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                    }
                    Data data = loginInfoBean.getData();
                    DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                    ToastUtils.showToastShort("登录成功");
                    //查询信息
                    queryUserInfo(loginInfoBean.getData().getPerson().getId());

                    EventBus.getDefault().post(new StringEvent("", 1010));

                } else {


                    if (loginInfoBean.getCode() == 3 || loginInfoBean.getCode() == 4) {
                        ToastUtils.showToastShort("该用户未注册");
                        startActivity(new Intent(LoginBindActivity.this, RegisterActivity.class));
                        finish();

                    } else {
                        ToastUtils.showToastShort("登录失败");
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("validateCode", mEtSms.getText().toString());
                map.put("terminal", "1");
                return map;
            }
        };

        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }


    private void queryUserInfo(String id) {

        String params = id;

        String url = Constants.plus(Constants.QUERY_USER_DYN_INFO_URL) + params;

        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequsetUserDynInfoBean requestInfoBean = gson.fromJson(s, RequsetUserDynInfoBean.class);

                //      LogUtil.i(requestInfoBean.toString());
//                ArrayList<Data> mInfoBean = new ArrayList<>();
//                mInfoBean.add(requestInfoBean.getData());
//                DataInfoCache.saveListCache(mInfoBean, Constants.MY_INFO);
                //保存个人信息
//                Data data = requestInfoBean.getData();
//                DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                //    ToastUtils.showToastShort("登录成功");
                if (requestInfoBean.getSuccess()) {
                    SPUtils.setInt(Constants.MY_DYN, requestInfoBean.getData().getDyn_no());
                    SPUtils.setInt(Constants.MY_FANS, requestInfoBean.getData().getFanse_no());
                    SPUtils.setInt(Constants.MY_FOLLOWS, requestInfoBean.getData().getFollow_no());


                } else {
                    ToastUtils.showToastShort(requestInfoBean.getErrorMsg());
                }


                SPUtils.setBoolean(Constants.ISLOGINED, true);//保存登录状态
                Data myinfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);

                if (TextUtils.isEmpty(myinfo.getPerson().getNick_name())) {
                    ToastUtils.showToastShort("您补全您的资料");
                    startActivity(new Intent(LoginBindActivity.this, SetRegisterInfoActivity.class));
                } else {
                    startActivity(new Intent(LoginBindActivity.this, HomeActivity.class));
                }

                setMipushId();
                finish();


                // LogUtil.i(DataInfoCache.loadOneCache(Constants.MY_INFO).toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());

            }

        }
        ) {

        };
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("queryUserInfo");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }


    /**
     * 设置mipusid
     */
    private void setMipushId() {

        MyStringRequest request = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.SET_MIPUSH_ID), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //   LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    //   LogUtil.i("设置mipush成功");
                } else {
                    // LogUtil.i("设置mipush失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                //  LogUtil.i("设置mipush失败"+volleyError.toString());

            }

        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("regId", SPUtils.getString(Constants.MY_MIPUSH_ID, null));
                map.put("terminal", "1");
                return map;
            }


        };

        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);

    }
}

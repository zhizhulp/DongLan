package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
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
import com.cn.danceland.myapplication.bean.RequsetUserDynInfoBean;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MD5Utils;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.PhoneFormatCheckUtils;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.tlslibrary.service.TLSService;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method.GET;

/**
 * 绑定新手机号
 * form 1 设置-手机号过来的
 */
public class ResetPhoneActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvGetsms;
    private EditText mEtSms;
    private String smsCode = "";
    private String password = "";
    private EditText mEtPhone;
    private int form = 0;//form
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        password = getIntent().getStringExtra("password");
        form = getIntent().getIntExtra("form", 0);
        initView();
    }

    private void initView() {
        mTvGetsms = findViewById(R.id.tv_getsms);
        mTvGetsms.setOnClickListener(this);
        mEtPhone = findViewById(R.id.et_phone);

        mEtSms = findViewById(R.id.et_sms);
        findViewById(R.id.btn_commit).setOnClickListener(this);


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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getsms:
                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    Toast.makeText(ResetPhoneActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断电话号码是否合法
                if (!PhoneFormatCheckUtils.isPhoneLegal(mEtPhone.getText().toString())) {
                    Toast.makeText(ResetPhoneActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.equals(getIntent().getStringExtra("phone"), mEtPhone.getText().toString().trim())) {

                    ToastUtils.showToastShort("请输入新的手机号");
                    return;
                }
                //设置不能点击
                mTvGetsms.setFocusable(false);
                mTvGetsms.setClickable(false);
                mTvGetsms.setTextColor(Color.WHITE);
                recLen = 30;
                mTvGetsms.setText("" + recLen + "秒后重试");
                //设置倒计时
                handler.postDelayed(runnable, 1000);

                getSMS();
                break;
            case R.id.btn_commit:
                //判断验证码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString().trim())) {
                    ToastUtils.showToastShort("请输入要绑定的新手机号");
                    return;
                }
                //判断验证码是否为空
                if (TextUtils.isEmpty(mEtSms.getText().toString().trim())) {
                    ToastUtils.showToastShort("请输入验证码");
                    return;
                }
                if (form == 1) {
                    resetPhoneToOKToken();//重置手机号
                } else {
                    resetPhone();//重置手机号
                }

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

        MyStringNoTokenRequest request = new MyStringNoTokenRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = new RequestInfoBean();
                requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    smsCode = requestInfoBean.getData().getVerCode();
                    if (Constants.DEV_CONFIG) {
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

    private void resetPhone() {
        MyStringNoTokenRequest jsonRequest = new MyStringNoTokenRequest(Request.Method.PUT, Constants.plus(Constants.RESET_PHONE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean infoBean = new RequestInfoBean();
                infoBean = gson.fromJson(s, RequestInfoBean.class);
                if (infoBean.getSuccess()) {

                    ToastUtils.showToastShort("手机号修改成功");
                    login(mEtPhone.getText().toString().trim());
//                    //发送事件
//                    EventBus.getDefault().post(new StringEvent(mEtPhone.getText().toString().trim(),111));
//                    Data data= (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
//                    data.getPerson().setPhone_no(mEtPhone.getText().toString().trim());
//                    DataInfoCache.saveOneCache(data,Constants.MY_INFO);
//                    finish();
                } else {
                    ToastUtils.showToastShort("修改失败，原因：" + infoBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                //   map.put("id", SPUtils.getString(Constants.MY_USERID, ""));
                map.put("phone", mEtPhone.getText().toString().trim());
                map.put("validateCode", mEtSms.getText().toString().trim());
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("Authorization", SPUtils.getString("tempTokenToCode", null));
                map.put("version", Constants.getVersion());
                map.put("platform", Constants.getPlatform());
                map.put("channel", AppUtils.getChannelCode());
                return map;
            }
        };

        MyApplication.getHttpQueues().add(jsonRequest);
    }

    //有效token请求
    private void resetPhoneToOKToken() {
        MyStringRequest jsonRequest = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.RESET_PHONE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean infoBean = new RequestInfoBean();
                infoBean = gson.fromJson(s, RequestInfoBean.class);
                if (infoBean.getSuccess()) {
                    ToastUtils.showToastShort("手机号修改成功");
                    Data infoData = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
                    infoData.getPerson().setPhone_no(mEtPhone.getText().toString().trim());
                    DataInfoCache.saveOneCache(infoData, Constants.MY_INFO);
                    EventBus.getDefault().post(new StringEvent(mEtPhone.getText().toString().trim(), 99)); //发送事件
                    ResetPhoneActivity.this.finish();
                } else {
                    ToastUtils.showToastShort("修改失败，原因：" + infoBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", mEtPhone.getText().toString().trim());
                map.put("validateCode", mEtSms.getText().toString().trim());
                return map;
            }
        };
        MyApplication.getHttpQueues().add(jsonRequest);
    }

    /**
     * 登录
     */
    private void login(final String phoneNum) {
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);

                Gson gson = new Gson();
                RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);
                LogUtil.i(loginInfoBean.toString());
                LogUtil.i(loginInfoBean.getCode() + "");
                if (loginInfoBean.getCode() == 6) {//如手机号被解绑,立刻绑定手机号
                    SPUtils.setString("tempTokenToCode", "Bearer+" + loginInfoBean.getData().getToken());//为了绑手机号的临时touken
                    startActivity(new Intent(ResetPhoneActivity.this, ResetPhoneActivity.class));
                } else {
                    if (loginInfoBean.getSuccess()) {
                        SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id

                        SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                        SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(password.trim()));//保存id\
                        if (loginInfoBean.getData().getMember() != null) {
                            SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                        }
                        Data data = loginInfoBean.getData();
                        DataInfoCache.saveOneCache(data, Constants.MY_INFO);

//                        //查询信息
                        queryUserInfo(loginInfoBean.getData().getPerson().getId(), loginInfoBean.getCode() + "");
                        if (Constants.DEV_CONFIG) {
                            login_txim("dev" + data.getPerson().getMember_no(), data.getSig());
                        } else {
                            login_txim(data.getPerson().getMember_no(), data.getSig());
                        }
                        setMipushId();
                        SPUtils.setBoolean(Constants.ISLOGINED, true);//保存登录状态
                        // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        //        finish();
                        ToastUtils.showToastShort("登录成功");
                        //    login_hx(data.getPerson().getMember_no(),"QWE",data);

                    } else {
                        if (loginInfoBean.getCode() == 5) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResetPhoneActivity.this);
                            builder.setMessage("您未在此设备登录，请绑定设备");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(ResetPhoneActivity.this, LoginBindActivity.class).putExtra("phone", mEtPhone.getText().toString()));
                                }
                            });
                            builder.show();
                        } else {
                            ToastUtils.showToastShort("用户名或密码错误");
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.i("onErrorResponse-" + volleyError);
                ToastUtils.showToastShort("请求失败，请查看网络连接");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone", mEtPhone.getText().toString().trim());
                map.put("password", password);
                map.put("terminal", "1");
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
                LogUtil.i(map.toString());
                return map;
            }
        };
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("login");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    private void queryUserInfo(String id, final String loginCode) {
        String params = id;
        String url = Constants.plus(Constants.QUERY_USER_DYN_INFO_URL) + params;
        MyStringRequest request = new MyStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                Gson gson = new Gson();
                RequsetUserDynInfoBean requestInfoBean = gson.fromJson(s, RequsetUserDynInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    SPUtils.setInt(Constants.MY_DYN, requestInfoBean.getData().getDyn_no());
                    SPUtils.setInt(Constants.MY_FANS, requestInfoBean.getData().getFanse_no());
                    SPUtils.setInt(Constants.MY_FOLLOWS, requestInfoBean.getData().getFollow_no());
                    startActivity(new Intent(ResetPhoneActivity.this, HomeActivity.class).putExtra("loginCode", loginCode));
                    finish();
                } else {
                    ToastUtils.showToastShort(requestInfoBean.getErrorMsg());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i(volleyError.toString());
            }
        });
        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("queryUserInfo");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }

    /**
     * 登录腾讯im
     *
     * @param identifier 账号
     * @param userSig
     */
    private void login_txim(final String identifier, final String userSig) {
        SPUtils.setString(Constants.MY_TXIM_ADMIN, identifier);
        LogUtil.i(identifier + "/n" + userSig);
        LoginBusiness.loginIm(identifier, userSig, new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                LogUtil.i("login failed. code: " + code + " errmsg: " + desc);
                TLSService.getInstance().setLastErrno(-1);
            }

            @Override
            public void onSuccess() {
                LogUtil.i("login succ 登录成功");
                TLSService.getInstance().setLastErrno(0);
                SPUtils.setString("sig", userSig);
            }
        });
    }

    /**
     * 设置mipusid
     */
    private void setMipushId() {
        LogUtil.e(Constants.plus(Constants.SET_MIPUSH_ID));
        MyStringRequest request = new MyStringRequest(Request.Method.PUT, Constants.plus(Constants.SET_MIPUSH_ID), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //   LogUtil.i(s);
                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {
                    LogUtil.i("设置mipush成功");
                } else {
                    LogUtil.i("设置mipush失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                LogUtil.i("设置mipush失败" + volleyError.toString());

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

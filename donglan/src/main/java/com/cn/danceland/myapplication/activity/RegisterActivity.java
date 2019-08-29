package com.cn.danceland.myapplication.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
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

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 64456 on 2017/9/22.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private Spinner mSpinner;
    private TextView mTvGetsms;
    private EditText mEtSms;
    private EditText mEtPsw;
    private EditText mEtConfirmPsd;
    private String smsCode = "";
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
    private EditText mEtPhone;
    ProgressDialog dialog;
    private CheckBox cb_agreement;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();


    }

    private void initView() {
        dialog = new ProgressDialog(this);
//        mSpinner = findViewById(R.id.sp_phone);
//        mSpinner.setSelection(0, true);
        mTvGetsms = findViewById(R.id.tv_getsms);
        mTvGetsms.setOnClickListener(this);
        mEtPhone = findViewById(R.id.et_phone);
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
        findViewById(R.id.btn_commit).setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);
        mEtPsw = findViewById(R.id.et_password);
        mEtConfirmPsd = findViewById(R.id.et_confirm_password);
        cb_agreement = findViewById(R.id.cb_agreement);
        findViewById(R.id.tv_agreemnet).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_agreemnet:
                startActivity(new Intent(RegisterActivity.this, NewsDetailsActivity.class).putExtra("url", Constants.REGISTER_AGREEMENT_URL).putExtra("title", "用户协议"));

                break;
            case R.id.tv_getsms:
                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断电话号码是否合法
                if (!PhoneFormatCheckUtils.isPhoneLegal(mEtPhone.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
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

                //  getSMS();//获取短信验证码

                findPhoneIsExist(mEtPhone.getText().toString());
                break;
            case R.id.btn_commit:
                //判断验证码是否为空
                if (TextUtils.isEmpty(mEtSms.getText().toString().trim())) {
                    Toast.makeText(RegisterActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!TextUtils.equals(smsCode, mEtSms.getText().toString().trim())) {
//                    Toast.makeText(RegisterActivity.this, "验证码有误，请重新输入", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                //判断密码是否为空
                if (TextUtils.isEmpty(mEtPsw.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码是否包含空格
                if (mEtPsw.getText().toString().contains(" ")) {
                    Toast.makeText(RegisterActivity.this, "密码不能包含空格，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码位数是否符合6-20
                if (mEtPsw.getText().toString().length() < 6) {
                    Toast.makeText(RegisterActivity.this, "设置密码要大于等于6位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtPsw.getText().toString().length() > 20) {
                    Toast.makeText(RegisterActivity.this, "设置密码要小于等于20位，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码输入是否一致
                if (!TextUtils.equals(mEtConfirmPsd.getText().toString(), mEtPsw.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "密码输入不一致，请重新输入", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!cb_agreement.isChecked()) {
                    ToastUtils.showToastShort("请阅读用户协议，并勾选");
                    return;
                }
                userRegister();//注册账户

                break;
            case R.id.iv_back://返回
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


    private void findPhoneIsExist(final String phone) {
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.FIND_PHONE_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                RequestSimpleBean simpleBean=new Gson().fromJson(s, RequestSimpleBean.class);
                if (simpleBean.getSuccess()){
                    if (TextUtils.equals("0",simpleBean.getData())){
                        getSMS();
//                        startActivity(new Intent(RegisterActivity.this, SetPswdActivity.class));
                    }else {
                        showDialogComfrim();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map=new HashMap<>();
                map.put("phone",phone);
                return map;
            }
        };
        MyApplication.getHttpQueues().add(request);

    }

    private  void showDialogComfrim(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("该手机号已经注册");
        builder.setPositiveButton("直接登录",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        builder.setNegativeButton("找回密码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                startActivity(new Intent(RegisterActivity.this,ForgetPasswordActivity.class));
                finish();

            }
        });
        builder.show();
    }
    /**
     * 注册用户
     */
    private void userRegister() {

        String url = Constants.plus(Constants.REGISTER_URL);
        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);

                Gson gson = new Gson();
                RequestInfoBean requestInfoBean = new RequestInfoBean();
                requestInfoBean = gson.fromJson(s, RequestInfoBean.class);
                if (requestInfoBean.getSuccess()) {

                    ToastUtils.showToastShort("注册成功");

                    login();//直接登录


                } else {
                    //注册失败
                    ToastUtils.showToastShort(requestInfoBean.getErrorMsg());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                ToastUtils.showToastShort("请求失败，请查看网络连接");
//                LogUtil.i(volleyError.toString() + "Error: " + volleyError
//                        + ">>" + volleyError.networkResponse.statusCode
//                        + ">>" + volleyError.networkResponse.data
//                        + ">>" + volleyError.getCause()
//                        + ">>" + volleyError.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", mEtPhone.getText().toString().trim());
                map.put("password", MD5Utils.encode(mEtPsw.getText().toString().trim()));
                map.put("platform", "1");
                map.put("validateCode", mEtSms.getText().toString());
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));

                LogUtil.i(map.toString());
                return map;
            }
        };

        // 设置请求的Tag标签，可以在全局请求队列中通过Tag标签进行请求的查找
        request.setTag("userRegister");
        // 设置超时时间
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // 将请求加入全局队列中
        MyApplication.getHttpQueues().add(request);
    }


    /**
     * 登录
     */
    private void login() {


        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                LogUtil.i(s);

                Gson gson = new Gson();

                RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);
                LogUtil.i(loginInfoBean.toString());


                if (loginInfoBean.getSuccess()) {

                    SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id

                    SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                    SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                    if (loginInfoBean.getData().getMember() != null) {
                        SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                    }
                    Data data = loginInfoBean.getData();
                    DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                    if (Constants.DEV_CONFIG) {
                        login_txim("dev" + data.getPerson().getMember_no(), data.getSig());
                    } else {
                        login_txim(data.getPerson().getMember_no(), data.getSig());
                    }

                    //查询信息
                    queryUserInfo(loginInfoBean.getData().getPerson().getId());

                } else {

                    ToastUtils.showToastShort(loginInfoBean.getErrorMsg());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                LogUtil.i(volleyError.toString());
                ToastUtils.showToastShort("请求失败，请查看网络连接");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", mEtPhone.getText().toString().trim());
                map.put("password", MD5Utils.encode(mEtPsw.getText().toString().trim()));
                map.put("terminal", "1");
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
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


    /**
     * 登录腾讯im
     *
     * @param identifier 账号
     * @param userSig
     */
    private void login_txim(final String identifier, final String userSig) {
        SPUtils.setString(Constants.MY_TXIM_ADMIN,identifier);
        LogUtil.i(identifier + "/n" + userSig);
        // identifier为用户名，userSig 为用户登录凭证
        //     LogUtil.i("isServiceRunning  " + ServiceUtils.isServiceRunning(getApplicationContext(), "com.tencent.qalsdk.service.QalService"));


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

                //  TLSHelper.getInstance().setLocalId(UserInfo.ge);
            }
        });
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

                if (requestInfoBean.getSuccess()) {
                    SPUtils.setInt(Constants.MY_DYN, requestInfoBean.getData().getDyn_no());
                    SPUtils.setInt(Constants.MY_FANS, requestInfoBean.getData().getFanse_no());
                    SPUtils.setInt(Constants.MY_FOLLOWS, requestInfoBean.getData().getFollow_no());
                    SPUtils.setBoolean(Constants.ISLOGINED, true);//保存登录状态
                    startActivity(new Intent(RegisterActivity.this, SetRegisterInfoActivity.class));
                    setMipushId();
                    finish();

                } else {
                    ToastUtils.showToastShort(requestInfoBean.getErrorMsg());
                }


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
                    //    LogUtil.i("设置mipush成功");
                } else {
                    //       LogUtil.i("设置mipush失败");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError volleyError) {
                //      LogUtil.i("设置mipush失败"+volleyError.toString());

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

package com.cn.danceland.myapplication.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.cn.danceland.myapplication.view.CommitButton;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.sdk.Constant;
import com.tencent.qcloud.tlslibrary.service.TLSService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shy on 2017/9/22.
 */

public class LoginSMSActivity extends BaseActivity implements View.OnClickListener {
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
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_login_sms);

        initTXIM();
        initView();
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 1010:
                finish();
                break;
            case 1015:
                finish();
                break;
            case 1012://短信
                login_by_phone_url(event.getMsg());
                break;

            default:
                break;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);


    }

    private void initView() {

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
        CommitButton commitButton = findViewById(R.id.dlbtn_commit);


        commitButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_getsms:
                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    Toast.makeText(LoginSMSActivity.this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断电话号码是否合法
                if (!PhoneFormatCheckUtils.isPhoneLegal(mEtPhone.getText().toString())) {
                    Toast.makeText(LoginSMSActivity.this, "电话号码有误，请重新输入", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LoginSMSActivity.this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                login_by_phone_url(mEtPhone.getText().toString().trim());

                break;
            case R.id.iv_back://返回
                startActivity(new Intent(LoginSMSActivity.this, LoginActivity.class));
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

    /***
     *
     * @param phone
     */
    private void login_by_phone_url(final String phone) {

        String params = phone;

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
                    SPUtils.setBoolean(Constants.ISLOGINED, true);
                    //   SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                    if (loginInfoBean.getData().getMember() != null) {
                        SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                    }

                    Data data = loginInfoBean.getData();
                    DataInfoCache.saveOneCache(data, Constants.MY_INFO);
                    ToastUtils.showToastShort("登录成功");
                    //查询信息
                    queryUserInfo(loginInfoBean.getData().getPerson().getId());
                    //登录腾讯IM
                    if (Constants.DEV_CONFIG) {
                        login_txim("dev" + data.getPerson().getMember_no(), data.getSig());
                    } else {
                        login_txim(data.getPerson().getMember_no(), data.getSig());
                    }


                } else {


                    if (loginInfoBean.getCode() == 3 || loginInfoBean.getCode() == 4) {
                        ToastUtils.showToastShort("该用户未注册");
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginSMSActivity.this);
                        builder.setMessage("该用户未注册，请先注册账号");
                        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(LoginSMSActivity.this, RegisterActivity.class));
                                finish();
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();


                    } else {
                        if (loginInfoBean.getCode() == 5) {//绑定设备
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginSMSActivity.this);
                            builder.setMessage("您未在此设备登录，请绑定设备");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(LoginSMSActivity.this, VerifyPSWDActivty.class).putExtra("phone", mEtPhone.getText().toString()).putExtra("smscode", smsCode));
                                    dialog.dismiss();
                                }
                            });
                            builder.show();
                        } else {
                            ToastUtils.showToastShort(loginInfoBean.getErrorMsg());
                        }


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
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
                LogUtil.i(map.toString());
                return map;
            }
        };

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

            }
        });


    }

    private TLSService tlsService;

    private void initTXIM() {
        tlsService = TLSService.getInstance();
        TIMSdkConfig config = new TIMSdkConfig(Constant.SDK_APPID).enableCrashReport(false).enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/donglan/log");
        boolean b = TIMManager.getInstance().init(getApplicationContext(), config);
        LogUtil.i(b + "");

        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        LogUtil.i("onForceOffline");
//                        App.TOKEN = "";
//                        UserControl.getInstance().clear();
//                        DataCleanManager.clearAllCache(getContext());
//                        PageRouter.startLogin(getContext());
//                        finish();
                    }

                    @Override
                    public void onUserSigExpired() {
                        //用户签名过期了，需要刷新userSig重新登录SDK
                        LogUtil.i("onUserSigExpired");
                    }
                })
                //设置连接状态事件监听器
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        LogUtil.i("onConnected连接聊天服务器");
                    }

                    @Override
                    public void onDisconnected(int code, String desc) {
                        LogUtil.i("onDisconnected");
                    }

                    @Override
                    public void onWifiNeedAuth(String name) {
                        LogUtil.i("onWifiNeedAuth");
                    }
                });
        RefreshEvent.getInstance().init(userConfig);
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        //将用户配置与通讯管理器进行绑定
        TIMManager.getInstance().setUserConfig(userConfig);


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
                setMipushId();
                if (TextUtils.isEmpty(myinfo.getPerson().getNick_name()) || TextUtils.isEmpty(myinfo.getPerson().getBirthday()) || TextUtils.isEmpty(myinfo.getPerson().getHeight())) {


                    builder = new AlertDialog.Builder(LoginSMSActivity.this);
                    builder.setMessage("您的资料不全，请补全您的资料");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(LoginSMSActivity.this, SetRegisterInfoActivity.class));

                            dialog.dismiss();

                            EventBus.getDefault().post(new StringEvent("", 1010));
                        }
                    });
                    builder.create().show();


                } else {
                    startActivity(new Intent(LoginSMSActivity.this, HomeActivity.class));

                    EventBus.getDefault().post(new StringEvent("", 1010));
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

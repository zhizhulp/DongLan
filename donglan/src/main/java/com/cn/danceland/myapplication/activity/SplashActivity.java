package com.cn.danceland.myapplication.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.db.DBData;
import com.cn.danceland.myapplication.im.model.FriendshipInfo;
import com.cn.danceland.myapplication.im.model.GroupInfo;
import com.cn.danceland.myapplication.im.model.UserInfo;
import com.cn.danceland.myapplication.utils.AppUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringNoTokenRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.qalsdk.QALSDKManager;
import com.tencent.qcloud.presentation.business.InitBusiness;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.SplashPresenter;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.qcloud.tlslibrary.service.TlsBusiness;
import com.tencent.qcloud.ui.NotifyDialog;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import tencent.tls.platform.TLSHelper;

import static com.android.volley.Request.Method.GET;


/**
 * 开屏页
 */
public class SplashActivity extends BaseFragmentActivity implements TIMCallBack {

    private static final int sleepTime = 2000;
    private SplashPresenter presenter;
    @SuppressLint("HandlerLeak")
    private Handler mHander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100001:
                    LogUtil.i(msg.getData().getString("apiurl", ""));
                    if (!TextUtils.isEmpty(msg.getData().getString("apiurl", ""))) {
                        Constants.HOST = msg.getData().getString("apiurl", "")+"/";
                        //Log.d(Constants.TAG_DEF, "handleMessage_SplashActivity: " + Constants.HOST);
                    }
                    next();
                    break;
                case 100002:
                    Log.d(Constants.TAG_DEF, "handleMessage_SplashActivity: 请求host遇到错误");
                    showdialog();
                    break;
                default:
                    break;
            }

        }
    };
    private long start;


    @Override
    protected void onCreate(Bundle arg0) {
        setTheme(R.style.MyTheme);
        super.onCreate(arg0);
        setContentView(R.layout.activity_splash);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.splash_root);
        TextView versionText = (TextView) findViewById(R.id.tv_version);

        versionText.setText("版本号：" + AppUtils.getVersionName(this));


//        AlphaAnimation animation = new AlphaAnimation(1.0f, 1.0f);
//        animation.setDuration(1500);
//        rootLayout.startAnimation(animation);
        init();
        if (Constants.DEV_CONFIG) {

            if (!TextUtils.isEmpty(SPUtils.getString("hosturl", null))) {
                Constants.HOST = SPUtils.getString("hosturl", null);
                //Log.d(Constants.TAG_DEF, "onCreate_SplashActivity: " + Constants.HOST);
            }
            next();


        } else {
            sethost();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.i(AppUtils.getDeviceId(this));
        start = System.currentTimeMillis();


    }

    public void copyDb() {

        try {
            boolean bl = DBData.copyRawDBToApkDb(SplashActivity.this, R.raw.donglan, "/data/data/com.cn.danceland.myapplication/databases/", "donglan.db", false);

            if (bl) {
                SPUtils.setBoolean("iscopy", bl);
                LogUtil.i("拷贝成功");
            } else {
                SPUtils.setBoolean("iscopy", false);
                LogUtil.i("拷贝不成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void next() {
        new Thread(new Runnable() {

            public void run() {

                //判断是否登录
                if (SPUtils.getBoolean(Constants.ISLOGINED, false)) {


                    long costTime = System.currentTimeMillis() - start;
                    //wait
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    //已经登录，进入主界面
                    if (DataInfoCache.loadOneCache(Constants.MY_INFO) == null) {
                        SPUtils.setBoolean(Constants.ISLOGINED, false);
                        LogUtil.i("MY_INFO=NULL");
//                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                        finish();
                        navToLogin();
                    } else {
//                        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

//                        finish();
                        navToHome();
                    }


                } else {


                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
//
//                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                    finish();

                    navToLogin();
                }
            }
        }).start();
    }

    private void init() {
//        // 务必检查IMSDK已做以下初始化
        QALSDKManager.getInstance().setEnv(0);
        QALSDKManager.getInstance().init(getApplicationContext(), 1400090939);
// 初始化TLSSDK
        TLSHelper tlsHelper = TLSHelper.getInstance().init(getApplicationContext(), 1400090939);

        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
        int loglvl = pref.getInt("loglvl", TIMLogLevel.DEBUG.ordinal());
        //初始化IMSDK
        InitBusiness.start(getApplicationContext(), loglvl);
        //初始化TLS
        TlsBusiness.init(getApplicationContext());
        LogUtil.i(UserInfo.getInstance().getId() + UserInfo.getInstance().getUserSig());
        String id = TLSService.getInstance().getLastUserIdentifier();
        UserInfo.getInstance().setId(id);
        UserInfo.getInstance().setUserSig(TLSService.getInstance().getUserSig(id));

        LogUtil.i(UserInfo.getInstance().getId() + UserInfo.getInstance().getUserSig());
        LogUtil.i((UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()))) + "@@@@" + (UserInfo.getInstance().getId() + (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()))));
//        LogUtil.i(UserInfo.getInstance().getId() + TLSService.getInstance().getLastUserInfo().accountType);
//        presenter = new SplashPresenter(this);
//        presenter.start();

//        tlsHelper.LOGIN
    }

    private void init_txim() {
        //登录之前要初始化群和好友关系链缓存
        TIMUserConfig userConfig = new TIMUserConfig();
        userConfig.setUserStatusListener(new TIMUserStatusListener() {
            @Override
            public void onForceOffline() {
                LogUtil.i("receive force offline message");
//                   Intent intent = new Intent(this, DialogActivity.class);
//                 startActivity(intent);
            }

            @Override
            public void onUserSigExpired() {
                //票据过期，需要重新登录
//                new NotifyDialog().show(getString(R.string.tls_expire), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                            logout();
//                        LogUtil.i("过期");
//                    }
//                });

            }
        })
                .setConnectionListener(new TIMConnListener() {
                    @Override
                    public void onConnected() {
                        LogUtil.i("onConnected");
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

        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);
        TIMManager.getInstance().setUserConfig(userConfig);

        LogUtil.i(UserInfo.getInstance().getId() + SPUtils.getString("sig", null));
//        LoginBusiness.loginIm(UserInfo.getInstance().getId(), SPUtils.getString("sig", null), this);
        LoginBusiness.loginIm(SPUtils.getString(Constants.MY_TXIM_ADMIN, null), SPUtils.getString("sig", null), this);
//        int sdkAppid = 开发者申请的SDK Appid;
//Constants.DEV_CONFIG?"dev"+UserInfo.getInstance().getId():UserInfo.getInstance().getId()
//
//
//// 务必检查IMSDK已做以下初始化
//        QALSDKManager.getInstance().setEnv(0);
//        QALSDKManager.getInstance().init(getApplicationContext(), sdkAppid, 0);
//
//// 初始化TLSSDK
//        TLSHelper tlsHelper = TLSHelper.getInstance().init(getApplicationContext(), sdkAppid);
    }

    @Override
    public void onError(int i, String s) {
        LogUtil.e("login error : code " + i + " " + s);
        TLSService.getInstance().setLastErrno(-1);
        switch (i) {
            case 6208:
                //离线状态下被其他终端踢下线
                NotifyDialog dialog = new NotifyDialog();
                dialog.show(getString(R.string.kick_logout), getSupportFragmentManager(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        navToLogin();
                    }
                });
                break;
            case 6200:
                Toast.makeText(this, getString(R.string.login_error_timeout), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
            case 70013:
                TlsBusiness.logout(UserInfo.getInstance().getId());
                UserInfo.getInstance().setId(null);
                MessageEvent.getInstance().clear();
                FriendshipInfo.getInstance().clear();
                GroupInfo.getInstance().clear();

                navToLogin();
                break;
            default:
                Toast.makeText(this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
                navToLogin();
                break;
        }
    }

    @Override
    public void onSuccess() {
        LogUtil.i("连接成功");
        TLSService.getInstance().setLastErrno(0);
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }


    public void navToHome() {
        LogUtil.i("tx进入主页面");
        //    startActivity(new Intent(SplashActivity.this, TXIMHomeActivity.class));


        if (!TextUtils.isEmpty(SPUtils.getString("sig", null))) {
            init_txim();
        } else {
            LogUtil.i("sig=null");
            navToLogin();
        }
    }


    public void navToLogin() {
        LogUtil.i("tx去登录");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    public boolean isUserLogin() {
        LogUtil.i("tx已有登录");
        LogUtil.i((UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()))) + "@@@@" + (UserInfo.getInstance().getId() + (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()))));

        return UserInfo.getInstance().getId() != null && (!TLSService.getInstance().needLogin(UserInfo.getInstance().getId()));


    }

    /**
     * get sdk version
     */
//    private String getVersion() {
//        return "3.4";
//

    public class APIUrlBean {

        private String api_url;
        private int status;

        public void setApi_url(String api_url) {
            this.api_url = api_url;
        }

        public String getApi_url() {
            return api_url;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

    }

    private void sethost() {
        MyStringNoTokenRequest noTokenRequest = new MyStringNoTokenRequest(GET, Constants.HOST + "/httpDNS/ipaddr", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i(s);
                APIUrlBean apiUrlBean = new Gson().fromJson(s, APIUrlBean.class);
                Message message = Message.obtain();
                message.what = 100001;
                Bundle bundle = new Bundle();
                bundle.putString("apiurl", apiUrlBean.getApi_url());
                message.setData(bundle);
                mHander.sendMessage(message);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.toString());

                Message message = Message.obtain();
                message.what = 100002;
                mHander.sendMessage(message);

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("platform", "1");
                map.put("version", AppUtils.getVersionName(MyApplication.getContext()));

                return map;
            }
        };
        MyApplication.getHttpQueues().add(noTokenRequest);
    }

    private void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("网络连接失败请重试");
        builder.setPositiveButton("重试", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sethost();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

}

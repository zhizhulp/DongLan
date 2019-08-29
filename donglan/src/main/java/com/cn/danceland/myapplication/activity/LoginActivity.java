package com.cn.danceland.myapplication.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
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
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.qalsdk.QALSDKManager;
import com.tencent.qcloud.presentation.business.LoginBusiness;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.sdk.Constant;
import com.tencent.qcloud.tlslibrary.service.TLSService;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements OnClickListener {


    private EditText mEtPhone;
    private EditText mEtPsw;
    private boolean isPswdChecked = false;
    //    private ImageView iv_pswd_see;
    ProgressDialog dialog;
    public static final String PERMISSION_RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;
    public static final String PERMISSION_CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    public static final String PERMISSION_ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final String PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            permissions();

        }
    };
    private CheckBox cb_agreement;
    private TLSService tlsService;
    private LinearLayout btn_login;
    private boolean isPermission;
    private ImageView iv_login_wx;//第三方登录 微信
    private ImageView iv_login_qq;//第三方登录 QQ
    private Tencent mTencent;//第三方登录 QQ
    private IWXAPI wxApi;//第三方登录 微信

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        //注册event事件
        EventBus.getDefault().register(this);

        initTXIM();
        mTencent = Tencent.createInstance(Constants.APP_ID_QQ_ZONE, getApplicationContext());//自己的AppID
        //通过WXAPIFactory工厂获取IWXApI的示例
        wxApi = WXAPIFactory.createWXAPI(this, Constants.APP_ID_WEIXIN, true);
        //将应用的appid注册到微信
        wxApi.registerApp(Constants.APP_ID_WEIXIN);
        intView();
        dialog = new ProgressDialog(this);
        dialog.setMessage("登录中……");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 1010:
                finish();
                break;
            case 1011://登录
                login();
                break;
            case 1015://返回密码登录
                login(event.getmMsgs().get(0), event.getmMsgs().get(1));
                LogUtil.i(event.getmMsgs().toString());
                break;
            default:
                break;
        }
    }

    private void intView() {

        findViewById(R.id.tv_register).setOnClickListener(this);
        findViewById(R.id.tv_login_sms).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
        findViewById(R.id.tv_forgetpsw).setOnClickListener(this);
        btn_login = (LinearLayout) findViewById(R.id.btn_login);
        findViewById(R.id.logo).setOnClickListener(this);
//        iv_pswd_see = findViewById(R.id.iv_pswd_see);
//        iv_pswd_see.setOnClickListener(this);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtPsw = (EditText) findViewById(R.id.et_password);
        try {
            Data data = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
            String phoneNo = data.getPerson().getPhone_no();
            mEtPhone.setText(phoneNo);
            mEtPsw.setFocusable(true);
            mEtPsw.setFocusableInTouchMode(true);
            mEtPsw.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cb_agreement = (CheckBox) findViewById(R.id.cb_agreement);
        findViewById(R.id.tv_agreemnet).setOnClickListener(this);
        //腾讯云im
        tlsService = TLSService.getInstance();
//        tlsService.initAccountLoginService(this,mEtPhone,mEtPsw,btn_login);
//        tlsService.init
        iv_login_wx = (ImageView) findViewById(R.id.iv_login_wx);
        iv_login_qq = (ImageView) findViewById(R.id.iv_login_qq);
        iv_login_wx.setOnClickListener(this);
        iv_login_qq.setOnClickListener(this);

        isAvilible();//是否安装 显示隐藏按钮
    }

    /**
     * 是否安装 显示隐藏按钮
     */
    private void isAvilible() {
        if (AppUtils.isApplicationAvilible(LoginActivity.this, "com.tencent.mobileqq")) {//QQ 包名：com.tencent.mobileqq
            iv_login_qq.setVisibility(View.VISIBLE);
        } else {
            iv_login_qq.setVisibility(View.GONE);
        }
        if (AppUtils.isApplicationAvilible(LoginActivity.this, "com.tencent.mm")) {//微信  包名：com.tencent.mm
            iv_login_wx.setVisibility(View.VISIBLE);
        } else {
            iv_login_wx.setVisibility(View.GONE);
        }
    }

    private void initTXIM() {

        //        // 务必检查IMSDK已做以下初始化
        QALSDKManager.getInstance().setEnv(0);
        QALSDKManager.getInstance().init(getApplicationContext(), 1400090939);
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

    @Override
    protected void onResume() {
        super.onResume();
        permissions();
    }


    public void permissions() {
        PermissionsUtil.TipInfo tip = new PermissionsUtil.TipInfo("注意:", "未授予位置和文件权限，应用将无法使用", "不了，谢谢", "打开权限");
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE)) {
            //有权限
            isPermission = true;
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                    //用户授予了权限
                    isPermission = true;
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                    //用户拒绝了申请
                    isPermission = false;

                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE}, true, tip);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register://注册
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.tv_login_sms://短信登录
                startActivity(new Intent(LoginActivity.this, LoginSMSActivity.class));
//                finish();
                break;
            case R.id.btn_login://登录
                //判断电话号码是否为空
                if (TextUtils.isEmpty(mEtPhone.getText().toString())) {
                    ToastUtils.showToastShort("请输入账号或手机号");
                    return;
                }
                if (!(mEtPhone.getText().toString().length() >= 8 && mEtPhone.getText().toString().length() <= 11)) {//（验证：大于等于8位小于等于11位）
                    ToastUtils.showToastShort("请输入账号或手机号");
                    return;
                }
                //判断密码是否为空
                if (TextUtils.isEmpty(mEtPsw.getText().toString())) {
                    ToastUtils.showToastShort("请输入密码");
                    return;
                }
                //判断密码是否包含空格
                if (mEtPsw.getText().toString().contains(" ")) {
                    ToastUtils.showToastShort("密码不能包含空格，请重新输入");
                    return;
                }

                if (!cb_agreement.isChecked()) {
                    ToastUtils.showToastShort("请阅读用户协议，并勾选");
                    return;
                }
                if (!isPermission) {
                    ToastUtils.showToastShort("请开启权限后登录");
                    return;
                }
                //   dialog.show();
                login();
                LogUtil.i(MD5Utils.encode(mEtPsw.getText().toString().trim()));
                break;
            case R.id.tv_forgetpsw://忘记密码


                startActivity(new Intent(LoginActivity.this, NewForgetPasswordActivity.class));
                break;

            case R.id.tv_agreemnet:

                startActivity(new Intent(LoginActivity.this, NewsDetailsActivity.class).putExtra("url", Constants.REGISTER_AGREEMENT_URL).putExtra("title", "用户协议"));

                break;
//            case R.id.iv_pswd_see://设置密码可见
//                if (isPswdChecked) {
//                    //密码不可见
//                    mEtPsw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//                    isPswdChecked = false;
//                    iv_pswd_see.setImageResource(R.drawable.img_unlook);
//
//                } else {
//                    //密码可见
//                    mEtPsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    isPswdChecked = true;
//                    iv_pswd_see.setImageResource(R.drawable.img_look);
//                }
//
//                break;
            case R.id.logo:
                // tlsService.initAccountLoginService();
                if (Constants.DEV_CONFIG) {
                    showHostDialog();
                }

                break;
            case R.id.iv_login_wx://第三方登录 微信
                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_login_qq://第三方登录 QQ
//                authorization(SHARE_MEDIA.QQ);
                //QQ第三方登录  
                mTencent.login(LoginActivity.this, "all", new BaseUiListener());
                break;
            default:
                break;
        }
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(this).setShareConfig(config);
        UMShareAPI.get(this).getPlatformInfo(LoginActivity.this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                LogUtil.i("onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                LogUtil.i("onComplete " + "授权完成");

                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");

//                LogUtil.i("uid--" + uid);
//                LogUtil.i("openid--" + openid);
//                LogUtil.i("unionid--" + unionid);
//                LogUtil.i("access_token--" + access_token);
//                LogUtil.i("name--" + name);
//                LogUtil.i("gender--" + gender);
//                LogUtil.i("iconurl--" + iconurl);

                //拿到信息去请求登录接口。。。
                sendOtherLogin(share_media, map);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                LogUtil.i("onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                LogUtil.i("onCancel " + "授权取消");
            }
        });
    }

    private class BaseUiListener implements IUiListener {
        public void onComplete(Object response) {
            /*
             *  下面隐藏的是用户登录成功后  登录用户数据的获取的方法
             *  共分为两种    一种是简单的信息的获取,另一种是通过UserInfo类获取用户较为详细的信息
             *有需要看看
             *  */
            final Map<String, String> map = new HashMap<String, String>();
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                LogUtil.i(response.toString());
                String openidString = ((JSONObject) response).getString("openid");
                mTencent.setOpenId(openidString);
                mTencent.setAccessToken(((JSONObject) response).getString("access_token"), ((JSONObject) response).getString("expires_in"));

                map.put("openid", ((JSONObject) response).getString("openid"));
                map.put("access_token", ((JSONObject) response).getString("access_token"));
                //拿到信息去请求登录接口。。。
                sendOtherLogin(SHARE_MEDIA.QQ, map);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /**到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？    */

//            QQToken qqToken = mTencent.getQQToken();
//            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
//
//            //        info.getUserInfo(new  BaseUIListener(this,"get_simple_userinfo"));
//            info.getUserInfo(new IUiListener() {
//                @Override
//                public void onComplete(Object o) {
//                    //用户信息获取到了
//                    try {
//
////                        Toast.makeText(getApplicationContext(), ((JSONObject) o).getString("nickname") + ((JSONObject) o).getString("gender"), Toast.LENGTH_SHORT).show();
//                        LogUtil.i("UserInfo--" + o.toString());
//
////                        map.put("uid",((JSONObject) o).getString("uid"));
////                        map.put("openid",((JSONObject) o).getString("openid"));
////                        map.put("unionid",((JSONObject) o).getString("unionid"));
////                        map.put("access_token",((JSONObject) o).getString("access_token"));
////                        map.put("refresh_token",((JSONObject) o).getString("refresh_token"));
////                        map.put("expires_in",((JSONObject) o).getString("expires_in"));
//                        map.put("name",((JSONObject) o).getString("nickname"));
//                        map.put("gender",((JSONObject) o).getString("gender"));
//                        map.put("iconurl",((JSONObject) o).getString("iconurl"));
//                        String uid = map.get("uid");
//                        String openid = map.get("openid");//微博没有
//                        String unionid = map.get("unionid");//微博没有
//                        String access_token = map.get("access_token");
//                        String name = map.get("name");
//                        String gender = map.get("gender");
//                        String iconurl = map.get("iconurl");
//                        LogUtil.i("uid--" + uid);
//                        LogUtil.i("openid--" + openid);
//                        LogUtil.i("unionid--" + unionid);
//                        LogUtil.i("access_token--" + access_token);
//                        LogUtil.i("name--" + name);
//                        LogUtil.i("gender--" + gender);
//                        LogUtil.i("iconurl--" + iconurl);
//                        //拿到信息去请求登录接口。。。
//                        sendOtherLogin(SHARE_MEDIA.QQ, map);
////                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
////                        startActivity(intent1);
////                        finish();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onError(UiError uiError) {
//                    LogUtil.i("UserInfo--" + "onError");
//                }
//
//                @Override
//                public void onCancel() {
//                    LogUtil.i("UserInfo--" + "onCancel");
//                }
//            });

        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendOtherLogin(final SHARE_MEDIA share_media, final Map<String, String> requesMap) {
        String url = Constants.plus(Constants.SEND_LOGIN_WEIXIN);
        HashMap<String, String> map = new HashMap<>();
        String from = "1";
        switch (share_media) {
            case WEIXIN:
                url = Constants.plus(Constants.SEND_LOGIN_WEIXIN);
                from = "1";
                map = new HashMap<>();
                map.put("access_token", requesMap.get("access_token"));
                map.put("openid", requesMap.get("openid"));
                map.put("terminal", "1");//Android 1  iOS 2
                break;
            case QQ:
                url = Constants.plus(Constants.SEND_LOGIN_QQ);
                from = "2";
                map = new HashMap<>();
                map.put("access_token", requesMap.get("access_token"));
                map.put("openid", requesMap.get("openid"));
                map.put("terminal", "1");//Android 1  iOS 2
                break;
        }
        LogUtil.i("url--" + url);
        final HashMap<String, String> finalMap = map;
        final String finalFrom = from;
        MyStringNoTokenRequest stringRequest = new MyStringNoTokenRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                Gson gson = new Gson();
                RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);
                LogUtil.i(loginInfoBean.toString());
                LogUtil.i("code--" + loginInfoBean.getCode());
                if (loginInfoBean.getSuccess() && loginInfoBean.getCode() == 0) {
                    SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id
                    SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                    SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                    if (loginInfoBean.getData().getMember() != null) {
                        SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                    }
                    Data data = loginInfoBean.getData();
                    DataInfoCache.saveOneCache(data, Constants.MY_INFO);

                    //查询信息
                    queryUserInfo(loginInfoBean.getData().getPerson().getId());
                    if (Constants.DEV_CONFIG) {
                        login_txim("dev" + data.getPerson().getMember_no(), data.getSig());
                    } else {
                        login_txim(data.getPerson().getMember_no(), data.getSig());
                    }
                    setMipushId();
                    SPUtils.setBoolean(Constants.ISLOGINED, true);//保存登录状态
                    ToastUtils.showToastShort("登录成功");
                } else {
                    switch (loginInfoBean.getCode()) {//0:登录成功  1:微信登录认证失败或者token 超时  2:认证成功，请先绑定手机号  4:资料不全，需要补全资料
                        case 2:// 2:认证成功，请先绑定手机号
                            LogUtil.i("form--" + finalFrom);
                            startActivity(new Intent(LoginActivity.this, RegisterToOtherActivity.class)
                                    .putExtra("access_token", finalMap.get("access_token"))
                                    .putExtra("openid", finalMap.get("openid"))
                                    .putExtra("from", finalFrom)//区分第三方登录的类型 1：微信   2：QQ
                            );
                            break;
                        case 4:// 4:资料不全，需要补全资料
                            startActivity(new Intent(LoginActivity.this, SetRegisterInfoActivity.class));
                            break;
                        default:
                            ToastUtils.showToastShort("用户名或密码错误");
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null) {
                    LogUtil.i(volleyError.toString());
                } else {
                    LogUtil.i("NULL");
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                LogUtil.i("finalMap--" + finalMap.toString());
                return finalMap;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, new BaseUiListener());
        if (requestCode == com.tencent.connect.common.Constants.REQUEST_API) {
            if (resultCode == com.tencent.connect.common.Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, new BaseUiListener());
            }
        }
    }


    private final String[] HostList = new String[]{"http://192.168.1.114:8003/", "http://192.168.1.66:8003/", "http://192.168.1.189:8003/", "http://wx.dljsgw.com/"};
    private final String[] HostListName = new String[]{"李平服务器", "李佳楠服务器", "杨爱鑫服务器", "正式服务器"};

    private void showHostDialog() {

        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(this);
        listDialog.setTitle("请选择您要切换的服务器地址");
        listDialog.setItems(HostListName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // which 下标从0开始
                // ...To-do
                Toast.makeText(LoginActivity.this, "已切换" + which + HostListName[which], Toast.LENGTH_SHORT).show();
                Constants.HOST = HostList[which];
                SPUtils.setString("hosturl", HostList[which]);
            }
        });
        listDialog.show();

    }

//    public void motifyfiled(String oldHost, String newHost) {
//        String updateOldHost = newHost;
//        Class clazz = null;
//        try {
//            clazz = Class.forName("com.cn.danceland.myapplication.utils.Constants");
//            //通过反射拿到变量名
//            Field[] fields = clazz.getDeclaredFields();
//            for (Field f : fields) {
//                LogUtil.i(f.getType().toString());
//                if (f.getType().toString().equals("class java.lang.String")) {
//                    String address = (String) f.get(clazz);
//                    if (address.contains(oldHost) && !address.equals(oldHost)) {
//                        address = address.replace(oldHost, updateOldHost);
//                    }
//                    f.set(clazz, address);
//                }
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//    }

    private boolean getServerVersion() {
        String urlStr = "http://192.168.1.93/test.txt";
        //long a = System.currentTimeMillis();
        try {
            /*
             * 通过URL取得HttpURLConnection 要网络连接成功，需在AndroidMainfest.xml中进行权限配置
             * <uses-permission android:name="android.permission.INTERNET" />
             */
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(60 * 1000);
            conn.setReadTimeout(60 * 1000);
            // 取得inputStream，并进行读取
            InputStream input = conn.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = in.readLine()) != null) {
                sb.append(line);

            }

            LogUtil.e("zzf", sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 登录
     */
    private void login(final String phone, final String pwd) {
        Log.d(Constants.TAG_DEF, "login_LoginActivity: " + Constants.plus(Constants.LOGIN_URL));

        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //           dialog.dismiss();
                LogUtil.i(s);

                Gson gson = new Gson();

                final RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);

                LogUtil.i(loginInfoBean.toString());
                LogUtil.i(loginInfoBean.getCode() + "");

                if (loginInfoBean.getCode() == 6) {//如手机号被解绑,立刻绑定手机号
                    SPUtils.setString("tempTokenToCode", "Bearer+" + loginInfoBean.getData().getToken());//为了绑手机号的临时touken
                    startActivity(new Intent(LoginActivity.this, ResetPhoneActivity.class).putExtra("password", MD5Utils.encode(mEtPsw.getText().toString().trim())));
                } else {
                    if (loginInfoBean.getSuccess()) {
                        SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id

                        SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                        SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                        if (loginInfoBean.getData().getMember() != null) {
                            SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                        }
                        Data data = loginInfoBean.getData();
                        DataInfoCache.saveOneCache(data, Constants.MY_INFO);

                        //查询信息
                        queryUserInfo(loginInfoBean.getData().getPerson().getId());
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("您未在此设备登录，请绑定设备");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(LoginActivity.this, VerifyPSWDActivty.class).putExtra("phone", loginInfoBean.getData().getPhone()));
                                    //  startActivity(new Intent(LoginSMSActivity.this, VerifyPSWDActivty.class).putExtra("phone", mEtPhone.getText().toString()).putExtra("smscode", smsCode));

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
                //       dialog.dismiss();
                LogUtil.e(volleyError.toString());
                ToastUtils.showToastShort("请求失败，请查看网络连接");

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("phone", phone);
                map.put("password", MD5Utils.encode(pwd));
                map.put("terminal", "1");
                map.put("deviceNo", AppUtils.getDeviceId(MyApplication.getContext()));
                LogUtil.i(AppUtils.getDeviceId(MyApplication.getContext()));

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
     * 登录
     */
    private void login() {
        Log.d(Constants.TAG_DEF, "login: " + Constants.plus(Constants.LOGIN_URL));

        MyStringNoTokenRequest request = new MyStringNoTokenRequest(Request.Method.POST, Constants.plus(Constants.LOGIN_URL), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //           dialog.dismiss();
                LogUtil.i(s);

                Gson gson = new Gson();

                final RequestLoginInfoBean loginInfoBean = gson.fromJson(s, RequestLoginInfoBean.class);

                LogUtil.i(loginInfoBean.toString());
                LogUtil.i(loginInfoBean.getCode() + "");

                if (loginInfoBean.getCode() == 6) {//如手机号被解绑,立刻绑定手机号
                    SPUtils.setString("tempTokenToCode", "Bearer+" + loginInfoBean.getData().getToken());//为了绑手机号的临时touken
                    startActivity(new Intent(LoginActivity.this, ResetPhoneActivity.class).putExtra("password", MD5Utils.encode(mEtPsw.getText().toString().trim())));
                } else {
                    if (loginInfoBean.getSuccess()) {
                        SPUtils.setString(Constants.MY_USERID, loginInfoBean.getData().getPerson().getId());//保存id

                        SPUtils.setString(Constants.MY_TOKEN, "Bearer+" + loginInfoBean.getData().getToken());
                        SPUtils.setString(Constants.MY_PSWD, MD5Utils.encode(mEtPsw.getText().toString().trim()));//保存id\
                        if (loginInfoBean.getData().getMember() != null) {
                            SPUtils.setString(Constants.MY_MEMBER_ID, loginInfoBean.getData().getMember().getId());
                        }
                        Data data = loginInfoBean.getData();
                        DataInfoCache.saveOneCache(data, Constants.MY_INFO);

                        //查询信息
                        queryUserInfo(loginInfoBean.getData().getPerson().getId());
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("您未在此设备登录，请绑定设备");
                            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(LoginActivity.this, LoginBindActivity.class).putExtra("phone", loginInfoBean.getData().getPhone()));
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
                //       dialog.dismiss();
                LogUtil.e(volleyError.toString());
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
                LogUtil.i(AppUtils.getDeviceId(MyApplication.getContext()));

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
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
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

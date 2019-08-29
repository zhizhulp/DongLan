package com.cn.danceland.myapplication.activity;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.cn.danceland.myapplication.MyApplication;
import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.app.AppManager2;
import com.cn.danceland.myapplication.bean.CheckUpdateBean;
import com.cn.danceland.myapplication.bean.CornerMarkMessageBean;
import com.cn.danceland.myapplication.bean.Data;
import com.cn.danceland.myapplication.bean.RequestInfoBean;
import com.cn.danceland.myapplication.db.HeartRate;
import com.cn.danceland.myapplication.db.HeartRateHelper;
import com.cn.danceland.myapplication.db.WearFitSleepBean;
import com.cn.danceland.myapplication.db.WearFitSleepHelper;
import com.cn.danceland.myapplication.db.WearFitStepBean;
import com.cn.danceland.myapplication.db.WearFitStepHelper;
import com.cn.danceland.myapplication.evntbus.StringEvent;
import com.cn.danceland.myapplication.fragment.DiscoverFragment;
import com.cn.danceland.myapplication.fragment.HomeFragment;
import com.cn.danceland.myapplication.fragment.MeFragment;
import com.cn.danceland.myapplication.fragment.ShopFragment;
import com.cn.danceland.myapplication.fragment.ShopListFragment;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.shouhuan.utils.DataHandlerUtils;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.DataInfoCache;
import com.cn.danceland.myapplication.utils.LocationService;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.cn.danceland.myapplication.utils.MyStringRequest;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.utils.TimeUtils;
import com.cn.danceland.myapplication.utils.ToastUtils;
import com.cn.danceland.myapplication.utils.runtimepermissions.PermissionsManager;
import com.cn.danceland.myapplication.utils.runtimepermissions.PermissionsResultAction;
import com.google.gson.Gson;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMConversationExt;
import com.tencent.imsdk.ext.message.TIMManagerExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.qcloud.presentation.event.FriendshipEvent;
import com.tencent.qcloud.presentation.event.GroupEvent;
import com.tencent.qcloud.presentation.event.MessageEvent;
import com.tencent.qcloud.presentation.event.RefreshEvent;
import com.tencent.qcloud.presentation.presenter.ConversationPresenter;
import com.tencent.qcloud.presentation.viewfeatures.ConversationView;
import com.xiaomi.mipush.sdk.MiPushClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JZVideoPlayer;
import me.leolin.shortcutbadger.ShortcutBadger;

import static android.view.animation.Animation.REVERSE;

public class HomeActivity extends BaseFragmentActivity implements View.OnClickListener {


    private TextView[] mTabs;
    private ImageView[] mmTabsImgs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
    private HomeFragment homeFragment;
    private ShopFragment shopFragment;
    private ShopListFragment shopListFragment;
    //    private NewHomeFragment discoverFragment;
    private DiscoverFragment discoverFragment;
    private MeFragment meFragment;
    public static HomeActivity instance = null;
    private static final String[] FRAGMENT_TAG = {"homeFragment", "shopFragment", "discoverFragment", "meFragment"};
    public LocationService mLocationClient;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    double jingdu, weidu;
    Data myInfo;

    private ConversationPresenter presenter;
    private ImageView msgUnread;
    private Animation animation;

    private CommandManager commandManager;//手环
    private String address1 = "";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //不处理崩溃时页面保存信息
        // super.onSaveInstanceState(outState);
    }


    //even事件处理
    @Subscribe
    public void onEventMainThread(StringEvent event) {
        switch (event.getEventCode()) {
            case 20001:

                if (shopFragment != null) {
                    shopFragment.refresh();
                }
                //setMsgUnread(getTotalUnreadNum() == 0);
                break;
            case 20002://切换门店重新加载门店页
//LogUtil.i("收到消息");
                if (shopFragment != null) {
                    shopFragment = new ShopFragment();
                    fragments = new Fragment[]{homeFragment, shopFragment, discoverFragment, meFragment};
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();

        unregisterReceiver(mGattUpdateReceiver);//手环
    }

    @Override
    protected void onResume() {
        super.onResume();
        //EMClient.getInstance().chatManager().addMessageListener(messageListener);
        address1 = SPUtils.getString(Constants.ADDRESS, "");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());//手环
        initConnWearFit();
    }

    private AnimationSet mAnimationSet;

    private void buildAnima() {
        //加载动画资源
        animation = AnimationUtils.loadAnimation(this, R.anim.tab_anim);


        ScaleAnimation mScaleAnimation = new ScaleAnimation(0.8f, 1.4f, 0.8f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation.setDuration(300);
        mScaleAnimation.setInterpolator(new AccelerateInterpolator());
        mScaleAnimation.setFillAfter(true);
        mScaleAnimation.setRepeatCount(1);
        mScaleAnimation.setRepeatMode(REVERSE);

        ScaleAnimation mScaleAnimation1 = new ScaleAnimation(1.5f, 0.8f, 1.5f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation1.setDuration(1000);
        mScaleAnimation1.setInterpolator(new AccelerateDecelerateInterpolator());
        //    mScaleAnimation.setFillAfter(true);
        ScaleAnimation mScaleAnimation2 = new ScaleAnimation(0.8f, 1.2f, 0.8f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mScaleAnimation2.setDuration(1000);
        //    mScaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mScaleAnimation2.setFillAfter(true);

//        AlphaAnimation mAlphaAnimation = new AlphaAnimation(1, .2f);
//        mAlphaAnimation.setDuration(300);
//        mAlphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
//        mAlphaAnimation.setFillAfter(false);

        mAnimationSet = new AnimationSet(true);


        mAnimationSet.addAnimation(mScaleAnimation);
//        mAnimationSet.addAnimation(mScaleAnimation1);
//        mAnimationSet.addAnimation(mScaleAnimation2);
        mAnimationSet.setRepeatCount(1);
        mAnimationSet.setRepeatMode(REVERSE);
        //   mAnimationSet.addAnimation(mAlphaAnimation);
        mAnimationSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(Constants.TAG_DEF, "onCreate_HomeActivity: "+Constants.HOST);
        EventBus.getDefault().register(this);
        View view = View.inflate(this, R.layout.activity_home, null);
        setContentView(view);

        //    requestPermissions();//请求权限
        instance = this;
        initView();
        checkUpdate();

        buildAnima();
        homeFragment = new HomeFragment();
        shopFragment = new ShopFragment();

        shopListFragment = new ShopListFragment();

        discoverFragment = new DiscoverFragment();
        meFragment = new MeFragment();
        msgUnread = (ImageView) findViewById(R.id.tabUnread);
        presenter = new ConversationPresenter(new ConversationView() {
            @Override
            public void initView(List<TIMConversation> conversationList) {

            }

            @Override
            public void updateMessage(TIMMessage message) {
//                LogUtil.i("shuaxin xiaoxi"+message.getElementCount());
//                getTotalUnreadNum();
//                LogUtil.i( "未读数"+  getTotalUnreadNum());

                EventBus.getDefault().post(new StringEvent("刷新消息", 20001));
            }

            @Override
            public void updateFriendshipMessage() {

            }

            @Override
            public void removeConversation(String identify) {

            }

            @Override
            public void updateGroupInfo(TIMGroupCacheInfo info) {

            }

            @Override
            public void refresh() {
                LogUtil.i("shuaxin");
            }
        });
        presenter.getConversation();

        initTimUser();
//        if (savedInstanceState != null) {
//            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG[0]);
//            shopFragment = (ShopFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG[1]);
//            discoverFragment = (DiscoverFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG[2]);
//            meFragment = (MeFragment) getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG[3]);
//        }
        try {
            myInfo = (Data) DataInfoCache.loadOneCache(Constants.MY_INFO);
        } catch (ClassCastException e) {
            LogUtil.i(e.toString());
            startActivity(new Intent(this, LoginActivity.class));
            if (SPUtils.getBoolean(Constants.ISLOGINED, false)) {
                SPUtils.setBoolean(Constants.ISLOGINED, false);
                ToastUtils.showToastShort("请重新登录您的账号");
            }

            //退出主页面
            finish();
        }

//        startActivity(new Intent(this, SetRegisterInfoActivity.class));

        if (myInfo != null) {//判断资料是否全
            if (myInfo.getHasPwd() != null && !myInfo.getHasPwd()) {
                ToastUtils.showToastShort("请您设置密码");
                startActivity(new Intent(HomeActivity.this, SetPswdActivity.class).putExtra("id", myInfo.getPerson().getId()));

            }

            LogUtil.i(myInfo.toString());
            if (TextUtils.isEmpty(myInfo.getPerson().getNick_name()) || TextUtils.isEmpty(myInfo.getPerson().getBirthday()) || TextUtils.isEmpty(myInfo.getPerson().getHeight()) || TextUtils.isEmpty(myInfo.getPerson().getWeight())) {
                startActivity(new Intent(this, SetRegisterInfoActivity.class));
                ToastUtils.showToastShort("请您填写个人信息");
                finish();
            }


            if (myInfo.getPerson().getDefault_branch() != null) {
                fragments = new Fragment[]{homeFragment, shopFragment, discoverFragment, meFragment};
            } else {
                fragments = new Fragment[]{homeFragment, shopListFragment, discoverFragment, meFragment};
            }
        } else {
            fragments = new Fragment[]{homeFragment, shopListFragment, discoverFragment, meFragment};
        }


//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, homeFragment)
//                .add(R.id.fragment_container, discoverFragment).hide(discoverFragment).show(homeFragment)
//                .commit();

        if (getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG[0]) == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, meFragment, FRAGMENT_TAG[3])
                    .hide(meFragment)
                    .add(R.id.fragment_container, homeFragment, FRAGMENT_TAG[0])
                    .show(homeFragment)
                    .commit();
        }
        //   getFragmentManager().findFragmentByTag()
        LogUtil.i(MiPushClient.getRegId(this));
        if (SPUtils.getBoolean(Constants.UPDATE_MIPUSH_CONFIG, false)) {
            setMipushId();
        }

        commandManager = CommandManager.getInstance(this);//手环
        initConnWearFit();
        initCornerMark();
    }
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //有按下时
//                break;
//            case MotionEvent.ACTION_UP:
//                //抬起时
//                if (!isSendWearFit) {
//                    isSendWearFit = true;
//                    initWearFitData();
//                }
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//
//    }

    public void initTimUser() {
        //基本用户配置
        TIMUserConfig userConfig = new TIMUserConfig()
                //设置群组资料拉取字段
                //.setGroupSettings(initGroupSettings())
                //设置资料关系链拉取字段
                //.setFriendshipSettings(initFriendshipSettings())
                //设置用户状态变更事件监听器
                .setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //被其他终端踢下线
                        LogUtil.i("onForceOffline: "+AppManager2.getAppManager().currentActivity());
                        AlertDialog.Builder builder = new AlertDialog.Builder(AppManager2.getAppManager().currentActivity());
                        builder.setMessage("您的账号已从从其他设备登录，请重新登录");
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppManager2.getAppManager().finishAllActivity();
                                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                                SPUtils.setBoolean(Constants.ISLOGINED, false);
                                finish();
                            }
                        });
                        builder.setCancelable(false);
                        builder.show();
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
                })
                //设置群组事件监听器
                .setGroupEventListener(new TIMGroupEventListener() {
                    @Override
                    public void onGroupTipsEvent(TIMGroupTipsElem elem) {
                        LogUtil.i("onGroupTipsEvent, type: " + elem.getTipsType());
                    }
                })
                //设置会话刷新监听器
                .setRefreshListener(new TIMRefreshListener() {
                    @Override
                    public void onRefresh() {
                        LogUtil.i("onRefresh");
                    }

                    @Override
                    public void onRefreshConversation(List<TIMConversation> conversations) {
                        LogUtil.i("onRefreshConversation, conversation size: " + conversations.size());
                    }
                });

        userConfig = new TIMUserConfigMsgExt(userConfig)
                .enableStorage(false)
                .enableReadReceipt(true);
        userConfig = new TIMUserConfigSnsExt(userConfig)
                .enableFriendshipStorage(true)
                .setFriendshipProxyListener(new TIMFriendshipProxyListener() {
                    @Override
                    public void OnAddFriends(List<TIMUserProfile> users) {
                        LogUtil.i("OnAddFriends");
                    }

                    @Override
                    public void OnDelFriends(List<String> identifiers) {
                        LogUtil.i("OnDelFriends");
                    }

                    @Override
                    public void OnFriendProfileUpdate(List<TIMUserProfile> profiles) {
                        LogUtil.i("OnFriendProfileUpdate");
                    }

                    @Override
                    public void OnAddFriendReqs(List<TIMSNSChangeInfo> reqs) {
                        LogUtil.i("OnAddFriendReqs");
                    }
                });
        //设置刷新监听
        RefreshEvent.getInstance().init(userConfig);
        userConfig = FriendshipEvent.getInstance().init(userConfig);
        userConfig = GroupEvent.getInstance().init(userConfig);
        userConfig = MessageEvent.getInstance().init(userConfig);

        userConfig = new TIMUserConfigGroupExt(userConfig)
                .enableGroupStorage(true)
                .setGroupAssistantListener(new TIMGroupAssistantListener() {
                    @Override
                    public void onMemberJoin(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        LogUtil.i("onMemberJoin");
                    }

                    @Override
                    public void onMemberQuit(String groupId, List<String> members) {
                        LogUtil.i("onMemberQuit");
                    }

                    @Override
                    public void onMemberUpdate(String groupId, List<TIMGroupMemberInfo> memberInfos) {
                        LogUtil.i("onMemberUpdate");
                    }

                    @Override
                    public void onGroupAdd(TIMGroupCacheInfo groupCacheInfo) {
                        LogUtil.i("onGroupAdd");

                    }

                    @Override
                    public void onGroupDelete(String groupId) {
                        LogUtil.i("onGroupDelete");
                    }

                    @Override
                    public void onGroupUpdate(TIMGroupCacheInfo groupCacheInfo) {
                        LogUtil.i("onGroupUpdate");
                    }
                });

        TIMManager.getInstance().setUserConfig(userConfig);
    }

    private void initCornerMark() {
        MyStringRequest stringRequest = new MyStringRequest(Request.Method.POST, Constants.plus(Constants.PUSH_RECORD_QUERY_BADGE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                LogUtil.i("Response--" + s);
                CornerMarkMessageBean responseBean = new Gson().fromJson(s, CornerMarkMessageBean.class);
                if (responseBean.getCode() != null && responseBean.getCode().equals("0")) {
                    int message_sum = Integer.valueOf(responseBean.getData() + "");
                    SPUtils.setString(Constants.MY_APP_MESSAGE_SUM, message_sum + "");//应用消息总数 用于桌面icon显示
                    ShortcutBadger.applyCount(HomeActivity.this, message_sum); //for 1.1.4+
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
                Map<String, String> map = new HashMap<String, String>();
                return map;
            }
        };
        MyApplication.getHttpQueues().add(stringRequest);
    }

    /**
     * 设置未读tab显示
     */
    public void setMsgUnread(boolean noUnread) {
        msgUnread.setVisibility(noUnread ? View.GONE : View.VISIBLE);
    }

    public long getTotalUnreadNum() {

//        //获取会话扩展实例
//        TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, peer);
//        TIMConversationExt conExt = new TIMConversationExt(con);
//        //获取会话未读数
//        long num = conExt.getUnreadMessageNum();

        List<TIMConversation> conversationList = TIMManagerExt.getInstance().getConversationList();
        long num = 0;
        for (TIMConversation conversation : conversationList) {
            if (conversation.getType() == TIMConversationType.System) {
                new TIMConversationExt(conversation).setReadMessage(null, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {

                    }

                    @Override
                    public void onSuccess() {

                    }
                });
            }

            num += new TIMConversationExt(conversation).getUnreadMessageNum();
        }
        return num;
    }


    private void checkUpdate() {

        MyStringRequest stringRequest = new MyStringRequest(Request.Method.GET, Constants.plus(Constants.CHECKUPDATE), new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

//                LogUtil.i("!!!!!!!!!!!!!!!!!!!"+s);
                CheckUpdateBean checkUpdateBean = new Gson().fromJson(s, CheckUpdateBean.class);
                if (checkUpdateBean != null && checkUpdateBean.getData() != null) {
                    String status = checkUpdateBean.getData().getStatus();
                    LogUtil.i(status);
                    if (TextUtils.equals("2", status) && checkUpdateBean.getData().getUrl() != null) {
                        // LogUtil.i(status+"!!!!"+checkUpdateBean.getData().getUrl());

                        showDialog(checkUpdateBean.getData().getUrl());
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {

//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                HashMap<String, String> map = new HashMap<>();
//                map.put("version", Constants.getVersion());
//                map.put("platform", Constants.getPlatform());
//                LogUtil.i(map.toString());
//                return map;
//            }


        };

        MyApplication.getHttpQueues().add(stringRequest);
    }

    private void showDialog(final String url) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MyApplication.getCurrentActivity());
        builder.setMessage("发现新版本，是否需要升级");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                Uri uri;
//                if (HttpUtils.IsUrl(url)) {
//                    uri = Uri.parse(url);
//                } else {
//                    uri = Uri.parse("https://www.baidu.com/");
//                }
//                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                ForceUpdateUtil forceUpdateUtil=new ForceUpdateUtil("",HomeActivity.this);
//                forceUpdateUtil.showDownloadProgressDialog(HomeActivity.this,url);
                showDownloadProgressDialog(HomeActivity.this,url);
            }
        });
        builder.setNegativeButton("取消",null);
        builder.show();
    }
    private AlertDialog progressDialog;
    private Button btn_ok;
    private TextView tv_message;


    private void showDownloadProgressDialog(Context context, String downloadUrl) {
        progressDialog = new AlertDialog.Builder(context).create();
        progressDialog.setTitle("提示");
        View view=View.inflate(context, R.layout.download_progressbar,null);
        ProgressBar progressBar=view.findViewById(R.id.progress_bar);
        btn_ok = view.findViewById(R.id.btn_ok);
        tv_message = view.findViewById(R.id.tv_message);
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);

        progressDialog.setView(view);
        progressDialog.setCancelable(false);                    //设置不可点击界面之外的区域让对话框小时
        //     progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);         //进度条类型
        progressDialog.show();
        // String downloadUrl = "http://ac-edNxPKqQ.clouddn.com/800exxxxxxx68ebcefda.apk"; //这里写你的apk url地址
        new DownloadAPK(progressBar).execute(downloadUrl);
    }

    /**
     * 下载APK的异步任务
     */

    private class DownloadAPK extends AsyncTask<String, Integer, String> {
        ProgressBar progressbar;
        File file;

        public DownloadAPK(ProgressBar progressDialog) {
            this.progressbar = progressDialog;
        }

        @Override
        protected String doInBackground(String... params) {
            URL url;
            HttpURLConnection conn;
            BufferedInputStream bis = null;
            FileOutputStream fos = null;

            try {
                url = new URL(params[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);

                int fileLength = conn.getContentLength();
                bis = new BufferedInputStream(conn.getInputStream());
                String fileName = Environment.getExternalStorageDirectory().getPath() + "/donglan/update.apk";
                LogUtil.i(fileName);
                file = new File(fileName);
                if (!file.exists()) {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    file.createNewFile();
                }
                fos = new FileOutputStream(file);
                byte data[] = new byte[4 * 1024];
                long total = 0;
                int count;
                while ((count = bis.read(data)) != -1) {
                    total += count;
                    publishProgress((int) (total * 100 / fileLength));
                    fos.write(data, 0, count);
                    fos.flush();
                }
                fos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            progressbar.setProgress(progress[0]);
            tv_message.setText("正在下载安装文件..."+progress[0]+"%");

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            tv_message.setText("下载完成，请点击安装");
            btn_ok.setVisibility(View.VISIBLE);
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if (Build.VERSION.SDK_INT >= 24) {
                        //参数1 上下文；参数2 Provider主机地址 authorities 和配置文件中保持一致 ；参数3  共享的文件
                        Uri apkUri = FileProvider.getUriForFile(MyApplication.getCurrentActivity(), "com.cn.danceland.myapplication.fileprovider", file);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    } else {
                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    }
                    MyApplication.getCurrentActivity().startActivity(intent);


//
//                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//                        MyApplication.getCurrentActivity().startActivity(intent);
                }
            });

        }

//        private void openFile(File file) {
//            if (file != null) {
//
//
//            }
//
//        }
    }






    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {

        mTabs = new TextView[4];
        mTabs[0] = findViewById(R.id.tv_home);
        mTabs[1] = findViewById(R.id.tv_shop);
        mTabs[2] = findViewById(R.id.tv_discover);
        mTabs[3] = findViewById(R.id.tv_me);
        mmTabsImgs = new ImageView[4];
        mmTabsImgs[0] = findViewById(R.id.iv_home);
        mmTabsImgs[1] = findViewById(R.id.iv_shop);
        mmTabsImgs[2] = findViewById(R.id.iv_discover);
        mmTabsImgs[3] = findViewById(R.id.iv_me);

//        for (int i = 0; i < mTabs.length; i++) {
//            mTabs[i].setOnClickListener(this);
//            mmTabsImgs[i].setOnClickListener(this);
//
//        }
        findViewById(R.id.ll_home).setOnClickListener(this);
        findViewById(R.id.ll_shop).setOnClickListener(this);
        findViewById(R.id.ll_discover).setOnClickListener(this);
        findViewById(R.id.ll_me).setOnClickListener(this);


        // 默认首页
        mTabs[0].setSelected(true);
        mmTabsImgs[0].setSelected(true);


        SharedPreferences bus_type = getSharedPreferences("bus_type", MODE_PRIVATE);
        SharedPreferences.Editor edit = bus_type.edit();
        edit.putString(11 + "", "PC买定金");
        edit.putString(13 + "", "PC退定金");
        edit.putString(14 + "", "PC储值卡充值");
        edit.putString(15 + "", "PC储值卡退钱");
        edit.putString(16 + "", "App储值卡充值");
        edit.putString(31 + "", "App买定金");
        edit.putString(32 + "", "App买卡");
        edit.putString(33 + "", "App为他人买定金");
        edit.putString(34 + "", "App为他人买卡");
        edit.putString(21 + "", "开卡");
        edit.putString(22 + "", "卡升级");
        edit.putString(23 + "", "续卡");
        edit.putString(24 + "", "补卡");
        edit.putString(25 + "", "转卡");
        edit.putString(26 + "", "退卡");
        edit.putString(27 + "", "停卡");
        edit.putString(28 + "", "延期");
        edit.putString(29 + "", "挂失");
        edit.putString(41 + "", "租柜");
        edit.putString(42 + "", "续柜");
        edit.putString(43 + "", "退柜");
        edit.putString(44 + "", "转柜");
        edit.putString(45 + "", "换柜");
        edit.putString(51 + "", "购买私教");
        edit.putString(52 + "", "私教转会员");
        edit.putString(53 + "", "私教转教练");
        edit.putString(54 + "", "购买小团课");
        edit.putString(55 + "", "小团课转会员");
        edit.putString(56 + "", "App购买私教");
        edit.putString(57 + "", "为他人购买私教");

        edit.apply();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mLocationClient = new LocationService(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        //mLocationClient = ((MyApplication) getApplication()).locationClient;
//        mLocationClient = new LocationService(getApplicationContext());
        mLocationClient.registerListener(myListener);


        mLocationClient.start();
        LogUtil.i("mLocationClient_start");

    }

    public String getlocationString() {
        return jingdu + "," + weidu;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocationClient.unregisterListener(myListener);
        mLocationClient.stop();
        LogUtil.i("mLocationClient_stop");
        //   EMClient.getInstance().chatManager().removeMessageListener(messageListener);
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取周边POI信息
            //POI信息包括POI ID、名称等，具体信息请参照类参考中POI类的相关说明
            if (location != null) {
                //   LogUtil.i(location.getLocType() + "");
            }
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                weidu = location.getLatitude();
                jingdu = location.getLongitude();
                //  LogUtil.i(weidu + "----" + jingdu);
                SPUtils.setString("jingdu", jingdu + "");
                SPUtils.setString("weidu", weidu + "");
                if (shopListFragment != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("jingdu", jingdu + "");
                    bundle.putString("weidu", weidu + "");
                    shopListFragment.setArguments(bundle);
                }
//                mLocationClient.unregisterListener(myListener);
//                mLocationClient.stop();
//                LogUtil.i("mLocationClient_stop");
            } else {
                ToastUtils.showToastShort("定位失败!");
            }
        }

    }

    public void setShopFragment(ShopFragment shopFragment, ShopListFragment shopListFragment) {

        if (shopFragment != null) {
            fragments = new Fragment[]{homeFragment, shopFragment, discoverFragment, meFragment};
        } else if (shopListFragment != null) {
            fragments = new Fragment[]{homeFragment, shopListFragment, discoverFragment, meFragment};
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 111) {
            fragments = new Fragment[]{homeFragment, shopFragment, discoverFragment, meFragment};
            FragmentTransaction trx =
                    getSupportFragmentManager().beginTransaction();

            index = 1;
            currentTabIndex = 1;

            //trx.hide(fragments[currentTabIndex]);
//            if(fragments[1].equals(shopListFragment)){
//                trx.remove(fragments[1]);
//            }
//            if (!fragments[1].isAdded()) {
//                trx.remove(fragments[1]);
//                trx.add(R.id.fragment_container, fragments[1], FRAGMENT_TAG[1]);
//            }
            //  trx.replace(R.id.fragment_container, shopFragment);
            trx.hide(shopListFragment);
            trx.add(R.id.fragment_container, shopFragment);
            trx.commit();
            //判断当前页
//            if (currentTabIndex != index) {
//
//            }else{
//                if (!fragments[2].isAdded()) {
//                    trx.add(R.id.fragment_container, fragments[index], FRAGMENT_TAG[index]);
//                }
//                trx.show(fragments[2]);
//            }
            //mTabs[1].setSelected(false);
            // set current tab selected
            mTabs[1].setSelected(true);
            mmTabsImgs[1].setSelected(true);
        }
    }

    private void clearAnim(){
        for (int i=0;i<mmTabsImgs.length;i++){
            mmTabsImgs[i].clearAnimation();
        }

    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_home:
                clearAnim();
                mmTabsImgs[0].startAnimation(mAnimationSet);
                //   mAnimationSet.start();
                index = 0;
                break;

            case R.id.ll_shop:
                index = 1;
                clearAnim();
                mmTabsImgs[1].startAnimation(mAnimationSet);
                break;
            case R.id.ll_discover:
             //   LogUtil.i("ll_discover");
                index = 2;
                clearAnim();
                mmTabsImgs[2].startAnimation(mAnimationSet);
                break;
            case R.id.ll_me:
                index = 3;
                clearAnim();
                mmTabsImgs[3].startAnimation(mAnimationSet);
                break;


        }
        //判断当前页
        if (currentTabIndex != index) {
            FragmentTransaction trx =
                    getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index], FRAGMENT_TAG[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        mmTabsImgs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        mmTabsImgs[index].setSelected(true);
        currentTabIndex = index;


    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //主页面返回两次退出
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (JZVideoPlayer.backPress()) {
                return true;
            }

            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                System.exit(0);
                return super.onKeyDown(keyCode, event);
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // unregisterBroadcastReceiver();
        EventBus.getDefault().unregister(this);
    }


//    private void refreshUIWithMessage() {
//        runOnUiThread(new Runnable() {
//            public void run() {
//                // refresh unread count
//                //updateUnreadLabel();
//
//                // refresh conversation list
//                if (shopFragment != null) {
//                    shopFragment.refresh();
//                }
//
//            }
//        });
//    }


//    private BroadcastReceiver broadcastReceiver;
//    private LocalBroadcastManager broadcastManager;
////
//    private void registerBroadcastReceiver() {
//        broadcastManager = LocalBroadcastManager.getInstance(this);
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Constants.ACTION_CONTACT_CHANAGED);
//        intentFilter.addAction(Constants.ACTION_GROUP_CHANAGED);
//        broadcastReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                LogUtil.i("收到广播");
//                //  updateUnreadLabel();
//                //   updateUnreadAddressLable();
//
//                // refresh conversation list
//                if (shopFragment != null) {
//                    shopFragment.refresh();
//                }
//
////                else if (currentTabIndex == 1) {
////                    if(contactListFragment != null) {
////                        contactListFragment.refresh();
////                    }
////                }
////                String action = intent.getAction();
////                if(action.equals(Constants.ACTION_GROUP_CHANAGED)){
////                    if (EaseCommonUtils.getTopActivity(MainActivity.this).equals(GroupsActivity.class.getName())) {
////                        GroupsActivity.instance.onResume();
////                    }
////                }
//            }
//        };
//        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
//    }
//
//    private void unregisterBroadcastReceiver() {
//        broadcastManager.unregisterReceiver(broadcastReceiver);
//    }

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
                    SPUtils.setBoolean(Constants.UPDATE_MIPUSH_CONFIG, false);

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
        MyApplication.getHttpQueues().add(request);
    }

    private IntentFilter makeGattUpdateIntentFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    //连接手环
    private void initConnWearFit() {
        address1 = SPUtils.getString(Constants.ADDRESS, "");
        LogUtil.i("ADDRESS" + address1);

        if (!MyApplication.mBluetoothConnected && !StringUtils.isNullorEmpty(address1) && address1.length() > 0) {
            try {
                if (MyApplication.mBluetoothLeService.connect(address1)) {
                    LogUtil.i("正在连接...");
                } else {
                    ToastUtils.showToastShort("连接失败");
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            MyApplication.isBluetoothConnecting = true;
            invalidateOptionsMenu();
        }
    }

    private void initWearFitData() {
        long time = TimeUtils.getPeriodTopDate(new SimpleDateFormat("yyyy-MM-dd"), 6);
//        LogUtil.i("获取这个之后的手环数据" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
        commandManager.setSyncData(time, time);//心率  计步
        commandManager.setSyncSleepData(time);//睡眠
        commandManager.setTimeSync();//同步时间给手环
    }

    private HeartRateHelper heartRateHelper = new HeartRateHelper();//手环 心率
    private WearFitSleepHelper sleepHelper = new WearFitSleepHelper();//手环 睡眠
    private WearFitStepHelper stepHelper = new WearFitStepHelper();//手环 计步
    private boolean isSendWearFit = false;//是否拉取手环数据  首次触摸加载
    //接收蓝牙状态改变的广播
    private BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();


            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = true;
                MyApplication.isBluetoothConnecting = false;
                invalidateOptionsMenu();//更新菜单栏
                LogUtil.i("连接成功...");
                isSendWearFit = false;
//                ToastUtils.showToastShort("连接成功");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                MyApplication.mBluetoothConnected = false;
                invalidateOptionsMenu();//更新菜单栏
                try {
                    MyApplication.mBluetoothLeService.close();//断开更彻底(没有这一句，在某些机型，重连会连不上)
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
//                ToastUtils.showToastShort("已断开");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                final byte[] txValue = intent
                        .getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                List<Integer> datas = DataHandlerUtils.bytesToArrayList(txValue);
                LogUtil.i("接收的数据：" + datas.toString());
                //心率传感器
                if (datas.get(4) == 0XB4) {//[171, 0, 4, 255, 180, 128, 1]
                    Integer integer = datas.get(6);
                    if (integer == 0) {
                        Toast.makeText(context, "手环通信不正常", Toast.LENGTH_SHORT).show();
                    } else if (integer == 1) {
                        Toast.makeText(context, "手环通信正常", Toast.LENGTH_SHORT).show();
                    }
                }
                //拉取心率数据
                if (datas.get(4) == 0x51 && datas.size() == 13) {//[171, 0, 10, 255, 81, 17, 18, 5, 19, 4, 35, 62, 62]
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + datas.get(10) + ":" + "00";
                    HeartRate heartRate = new HeartRate();
                    heartRate.setDate(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    heartRate.setHeartRate(datas.get(11));
                    heartRateHelper.insert(heartRate);//心率
                    SharedPreferences bus_type = getSharedPreferences("wear_fit_home_data", MODE_PRIVATE);
                    SharedPreferences.Editor edit = bus_type.edit();
                    edit.putInt("heart_rate", datas.get(11));//item 心率
                    edit.apply();
                }
                //拉取睡眠数据
                if (datas.get(4) == 0x52 && datas.size() == 14) {//[171, 0, 11, 255, 82, 128, 18, 7, 31, 0, 49, 2, 0, 29]  11位state 12位*256+13位
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + datas.get(10) + ":" + "00";
                    WearFitSleepBean sleepBean = new WearFitSleepBean();
                    sleepBean.setTimestamp(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    sleepBean.setState(datas.get(11) + "");//11位state
                    sleepBean.setContinuoustime(datas.get(12) * 256 + datas.get(13));//睡了多久 12位*256+13位
                    sleepHelper.insert(sleepBean);//睡眠
                }
                //拉取计步数据
                if (datas.get(4) == 0x51 && datas.size() == 20) {
                    String date = "20" + datas.get(6) + "-" + datas.get(7) + "-" + datas.get(8) + " " + datas.get(9) + ":" + "00" + ":" + "00";
                    int step = datas.get(10) * 256 * 256 + datas.get(11) * 256 + datas.get(12);
                    int cal = datas.get(13) * 256 * 256 + datas.get(14) * 256 + datas.get(15);
                    WearFitStepBean wearFitStepBean = new WearFitStepBean();
                    wearFitStepBean.setTimestamp(TimeUtils.date2TimeStamp(date, "yyyy-MM-dd HH:mm:ss"));
                    wearFitStepBean.setStep(step);
                    wearFitStepBean.setCal(cal);
                    int fatigue = 0;
                    int hour = datas.get(9);
                    if (hour >= 6 && hour < 11) {
                        fatigue = 0;
                    } else if (hour >= 11 && hour < 18) {
                        fatigue = 10;
                    } else if (hour >= 18 && hour < 24) {
                        fatigue = 20;
                    } else {
                        fatigue = 30;
                    }
                    fatigue = (int) (fatigue + Math.sqrt(step) / 2);
                    wearFitStepBean.setFatigue(fatigue);//疲劳
                    stepHelper.insert(wearFitStepBean);// 计步
                }
                if (datas.get(4) == 0x51 && datas.size() == 17) {//首页数据
                    int step = (datas.get(6) << 16) + (datas.get(7) << 8) + datas.get(8);//计步
                    int cal = (datas.get(9) << 16) + (datas.get(10) << 8) + datas.get(11);//卡路里
                    int ligthSleep = datas.get(12) * 60 + datas.get(13);//浅睡
                    int deepSleep = datas.get(14) * 60 + datas.get(15);//深睡
                    int wakeupTime = datas.get(16);//醒来次数

                    SharedPreferences bus_type = getSharedPreferences("wear_fit_home_data", MODE_PRIVATE);
                    SharedPreferences.Editor edit = bus_type.edit();
                    edit.putInt("step", step);
                    edit.putInt("kcal", cal);
//                    edit.putInt("heart_rate",  datas.get(11));//item 心率  上面有心率
                    edit.putInt("sleep", deepSleep + ligthSleep);//item 睡眠
                    edit.apply();
                }
            }
        }
    };

    public interface MyTouchListener {
        public void onTouchEvent(MotionEvent event);
    }

    // 保存MyTouchListener接口的列表
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<HomeActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //有按下时
                break;
            case MotionEvent.ACTION_UP:
                //抬起时
                if (!isSendWearFit) {
                    isSendWearFit = true;
                    initWearFitData();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}

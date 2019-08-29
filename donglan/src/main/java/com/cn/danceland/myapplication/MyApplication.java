package com.cn.danceland.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cn.danceland.myapplication.activity.HomeActivity;
import com.cn.danceland.myapplication.db.DaoMaster;
import com.cn.danceland.myapplication.db.DaoSession;
import com.cn.danceland.myapplication.im.utils.Foreground;
import com.cn.danceland.myapplication.shouhuan.service.BluetoothLeService;
import com.cn.danceland.myapplication.utils.Constants;
import com.cn.danceland.myapplication.utils.LocationService;
import com.cn.danceland.myapplication.utils.LogUtil;
import com.danikula.videocache.HttpProxyCacheServer;
import com.tencent.imsdk.TIMConnListener;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMGroupEventListener;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMGroupReceiveMessageOpt;
import com.tencent.imsdk.TIMGroupTipsElem;
import com.tencent.imsdk.TIMLogLevel;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMOfflinePushListener;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMRefreshListener;
import com.tencent.imsdk.TIMSNSChangeInfo;
import com.tencent.imsdk.TIMSdkConfig;
import com.tencent.imsdk.TIMUserConfig;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMUserStatusListener;
import com.tencent.imsdk.ext.group.TIMGroupAssistantListener;
import com.tencent.imsdk.ext.group.TIMGroupCacheInfo;
import com.tencent.imsdk.ext.group.TIMUserConfigGroupExt;
import com.tencent.imsdk.ext.message.TIMUserConfigMsgExt;
import com.tencent.imsdk.ext.sns.TIMFriendshipProxyListener;
import com.tencent.imsdk.ext.sns.TIMUserConfigSnsExt;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.tencent.qcloud.sdk.Constant;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shy on 2017/9/30 09:27
 * Email:644563767@qq.com
 */


public class MyApplication extends android.support.multidex.MultiDexApplication {
    private static RequestQueue requestQueue;
    public static Context applicationContext;
    private static MyApplication instance;
    public static Map<String,String> channelMap=new HashMap<>();
    //获取到主线程的handler
    private static Handler mMainThreadHandler = null;
    //获取到主线程的looper
    private static Looper mMainThreadLooper = null;
    //获取到主线程
    private static Thread mMainThead = null;
    //获取到主线程的id
    private static int mMainTheadId ;

    //public LocationService locationClient;
    private DaoMaster.DevOpenHelper donglan, message, heartRate,wearFitSleep,wearFitStep;
    private SQLiteDatabase db, messagedb, heartRatedb,wearFitSleepdb,wearFitStepdb;
    private DaoMaster daoMaster, messageMaster, heartRateMaster,wearFitSleepMaster,wearFitStepMaster;
    private DaoSession daoSession, messageSession, heartRateSession,wearFitSleepSession,wearFitStepSession;
    // 小米推送ID.
    private static final String APP_ID = "2882303761517681383";
    // 小米推送KEY.
    private static final String APP_KEY = "5681768120383";
    //private static DemoHandler sHandler = null;
    private static HomeActivity sMainActivity = null;

    private HttpProxyCacheServer proxy;
    private static Activity currentActivity;

    public static BluetoothInterface mBluetoothLeService;//蓝牙连接服务
    public static boolean mBluetoothConnected = false;
    public static boolean isBluetoothConnecting = false;
    public LocationService locationClient;

    public static HttpProxyCacheServer getProxy(Context context) {
        MyApplication app = (MyApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {

        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 1G for cache
                .build();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();

initchannel();

        Foreground.init(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
//        locationClient = new LocationService(getApplicationContext());
//        SDKInitializer.initialize(getApplicationContext());
        applicationContext = this;

        this.mMainThreadHandler = new Handler();
        this.mMainThreadLooper = getMainLooper();
        this.mMainThead = Thread.currentThread();
        //android.os.Process.myUid()获取到用户id
        //android.os.Process.myPid();//获取到进程id
        //android.os.Process.myTid()获取到调用线程的id
        this.mMainTheadId = android.os.Process.myTid();//主線程id

        instance = this;
        setUpDb();
        // 注册push服务，注册成功后会向DemoMessageReceiver发送广播
        // 可以从DemoMessageReceiver的onCommandResult方法中MiPushCommandMessage对象参数中获取注册信息
        if (shouldInit()) {
            MiPushClient.registerPush(this, APP_ID, APP_KEY);
        }
////        if (sHandler == null) {
////            sHandler = new DemoHandler(getApplicationContext());
////        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            bindBleService();
        }

        this.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {


            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        initTXIM();//初始化腾讯IM
        if (!Constants.DEV_CONFIG){
            initYouMeng();//初始化友盟
        }

        //友盟分享初始化
        UMConfigure.init(this,Constants.APP_ID_UMENG
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//5ba1ee11f1f5569f370000f7
        PlatformConfig.setWeixin(Constants.APP_ID_WEIXIN, Constants.APP_SECRET_WEIXIN);
        PlatformConfig.setQQZone(Constants.APP_ID_QQ_ZONE, Constants.APP_SECRET_QQ_ZONE);

    }

public void initchannel(){
    channelMap.put("guanwang","1");
    channelMap.put("360","3");
    channelMap.put("yingyongbao","4");
    channelMap.put("91","5");
    channelMap.put("wandoujia","6");
    channelMap.put("androidmarket","7");
    channelMap.put("baidu","8");
    channelMap.put("sogou","9");
    channelMap.put("uc","10");
    channelMap.put("pp","11");
    channelMap.put("vivo","12");
    channelMap.put("xiaomi","13");
    channelMap.put("meizu","14");
    channelMap.put("huawei","15");
    channelMap.put("lenovo","16");


}

    private void   initYouMeng(){
      /*
     * 初始化common库
     * 参数1:上下文，不能为空
     * 参数2:【友盟+】 AppKey(第一步从官网获取到的)
     * 参数3:【友盟+】 Channel（多渠道打包时用的到）
     * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
     * 参数5:Push推送业务的secret,需要集成Push功能时必须传入Push的secret，否则传空。
     */
      UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, null);
      MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

      //禁止默认的页面统计功能，这样将不会再自动统计Activity页面。（包含Activity、Fragment或View的应用）
      MobclickAgent.openActivityDurationTrack(false);

      // 打开统计SDK调试模式（上线时记得关闭）

      UMConfigure.setLogEnabled(Constants.DEV_CONFIG);


  }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public static RequestQueue getHttpQueues() {
        return requestQueue;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return applicationContext;
    }

    private void setUpDb() {

        donglan = new DaoMaster.DevOpenHelper(this, "donglan", null);
        message = new DaoMaster.DevOpenHelper(this, "message", null);
        heartRate = new DaoMaster.DevOpenHelper(this, "heartRate", null);
        wearFitSleep = new DaoMaster.DevOpenHelper(this, "wearFitSleep", null);
        wearFitStep = new DaoMaster.DevOpenHelper(this, "wearFitStep", null);

        db = donglan.getWritableDatabase();
        messagedb = message.getWritableDatabase();
        heartRatedb = message.getWritableDatabase();
        wearFitSleepdb = message.getWritableDatabase();
        wearFitStepdb = message.getWritableDatabase();

        daoMaster = new DaoMaster(db);
        messageMaster = new DaoMaster(messagedb);
        heartRateMaster = new DaoMaster(heartRatedb);
        wearFitSleepMaster = new DaoMaster(wearFitSleepdb);
        wearFitStepMaster = new DaoMaster(wearFitStepdb);

        daoSession = daoMaster.newSession();
        messageSession = messageMaster.newSession();
        heartRateSession = heartRateMaster.newSession();
        wearFitSleepSession = wearFitSleepMaster.newSession();
        wearFitStepSession = wearFitStepMaster.newSession();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public SQLiteDatabase getMessageDb() {
        return messagedb;
    }

    public DaoSession getDaoSession() {

        return daoSession;
    }

    public DaoSession getMessageDaoSession() {

        return messageSession;
    }

    public DaoSession getHeartRateSessionSession() {
        return heartRateSession;
    }

    public DaoSession getWearFitSleepSession() {
        return wearFitSleepSession;
    }

    public DaoSession getWearFitStepSession() {
        return wearFitStepSession;
    }

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {







            mBluetoothLeService = BluetoothInterface.Stub.asInterface(service);
//            BluetoothInterface bluetoothInterface = BluetoothInterface.Stub.asInterface(service);
//            mBluetoothLeService = ((BluetoothInterface.Stub) BluetoothInterface.Stub.asInterface(service)).getService();
//            LogUtil.i("mBluetoothLeService");
////            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            try {

                if (!mBluetoothLeService.initialize()) {
                    LogUtil.i("Unable to initialize Bluetooth");

                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            // Automatically connects to the device upon successful start-up initialization.
//            mBluetoothLeService.connect(mDeviceAddress);


        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };

    private void bindBleService() {
        Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
        bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private void initTXIM() {
        initTim();
        initTimUser();

        if (MsfSdkUtils.isMainProcess(this)) {
            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification timOfflinePushNotification) {
                    if (timOfflinePushNotification.getGroupReceiveMsgOpt() == TIMGroupReceiveMessageOpt.ReceiveAndNotify) {
                        //消息被设置为需要需要提醒
                        timOfflinePushNotification.doNotify(getApplicationContext(), R.mipmap.ic_launcher);
                        //注册推送服务
                    }
                }
            });
        }
//        TIMSdkConfig config = new TIMSdkConfig(Constant.SDK_APPID).enableCrashReport(false).enableLogPrint(true)
//                .setLogLevel(TIMLogLevel.DEBUG)
//                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/donglan/log");
//        boolean b = TIMManager.getInstance().init(this, config);
    }

    public void initTim() {
        //初始化SDK基本配置
        TIMSdkConfig config = new TIMSdkConfig(Constant.SDK_APPID)
                .setAccoutType(String.valueOf(Constant.ACCOUNT_TYPE))
                .enableCrashReport(false)
                .enableLogPrint(true)
                .setLogLevel(TIMLogLevel.DEBUG)
                .setLogPath(Environment.getExternalStorageDirectory().getPath() + "/donglan/log");
        //初始化SDK
        boolean init = TIMManager.getInstance().init(getApplicationContext(), config);
        if (init) {
            LogUtil.i("init success");
        } else {
            LogUtil.i("init fail");
        }
    }

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
                        LogUtil.i("onForceOffline");
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

    public static Handler getMainThreadHandler(){
        return mMainThreadHandler;
    }
    public static Looper getMainThreadLooper(){
        return mMainThreadLooper;
    }
    public static Thread getMainThread(){
        return mMainThead;
    }
    public static int getMainThreadId(){
        return mMainTheadId;
    }

}

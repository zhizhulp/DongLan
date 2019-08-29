package com.cn.danceland.myapplication.shouhuan.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.shouhuan.bean.AppNotify;
import com.cn.danceland.myapplication.shouhuan.bean.LongSit;
import com.cn.danceland.myapplication.shouhuan.command.CommandManager;
import com.cn.danceland.myapplication.utils.SPUtils;
import com.cn.danceland.myapplication.utils.StringUtils;
import com.cn.danceland.myapplication.view.DongLanTitleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/** APP提醒
 * Created by feng on 2018/6/22.
 */

public class WearFitRemindActivity extends Activity {
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private Context context;
    private DongLanTitleView shouhuan_remind_title;
    private Switch sw_wechart;
    private Switch sw_qq;
    private Switch sw_weibo;
    private Gson gson;
    private AppNotify appNotify;
    private CommandManager commandManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wearfitremind);
        context = this;
        gson = new Gson();
        appNotify = new AppNotify();
        commandManager = CommandManager.getInstance(getApplicationContext());
        initView();
        getHistory();
        initPermission();//6.0权限
    }

    private void initView() {
        shouhuan_remind_title = findViewById(R.id.shouhuan_remind_title);
        shouhuan_remind_title.setTitle("APP提醒");
        sw_wechart = findViewById(R.id.sw_wechart);
        sw_qq = findViewById(R.id.sw_qq);
        sw_weibo = findViewById(R.id.sw_weibo);
        sw_wechart.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_qq.setOnCheckedChangeListener(onCheckedChangeListener);
        sw_weibo.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            switch (compoundButton.getId()) {
                case R.id.sw_wechart:
                    appNotify.setNotifyid(9);//微信：9   QQ：7   微博：19
                    break;
                case R.id.sw_qq:
                    appNotify.setNotifyid(7);//微信：9   QQ：7   微博：19
                    break;
                case R.id.sw_weibo:
                    appNotify.setNotifyid(19);//微信：9   QQ：7   微博：19
                    break;
            }
            if (b) {
                appNotify.setIsOn(1);
            } else {
                appNotify.setIsOn(0);
            }
            SPUtils.setString("IgnoreLongSit", gson.toJson(appNotify));
            commandManager.sendNotify(appNotify.getNotifyid(), appNotify.getIsOn());
        }
    };

    private void getHistory() {
        String appNotifyGson = SPUtils.getString("AppNotify", "");
        if (!StringUtils.isNullorEmpty(appNotifyGson)) {
            Type listType = new TypeToken<AppNotify>() {
            }.getType();
            appNotify = gson.fromJson(appNotifyGson, listType);
        }
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.notification_location);
                builder.setMessage(R.string.location_notification_warn);
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }
}

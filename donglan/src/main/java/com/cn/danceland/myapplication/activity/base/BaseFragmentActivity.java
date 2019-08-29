package com.cn.danceland.myapplication.activity.base;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.cn.danceland.myapplication.app.AppManager2;
import com.umeng.analytics.MobclickAgent;


public class BaseFragmentActivity extends FragmentActivity {

    // BaseActivity中统一调用MobclickAgent 类的 onResume/onPause 接口
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager2.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager2.getAppManager().finishActivity(this);
    }
}

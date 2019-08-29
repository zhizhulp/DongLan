package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;
import com.cn.danceland.myapplication.bean.SiJiaoYuYueBean;
import com.cn.danceland.myapplication.view.CustomLine2;
import com.cn.danceland.myapplication.view.StepArcView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shy on 2018/8/21 09:18
 * Email:644563767@qq.com
 */


public class TestActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        CustomLine2 customLine2 = (CustomLine2) findViewById(R.id.cus);
        List<SiJiaoYuYueBean.Data> data = new ArrayList<>();
        SiJiaoYuYueBean sjb = new SiJiaoYuYueBean();
        SiJiaoYuYueBean.Data data1 = new SiJiaoYuYueBean.Data(490, 550, 2);
        SiJiaoYuYueBean.Data data2 = new SiJiaoYuYueBean.Data(550, 610, 1);
        SiJiaoYuYueBean.Data data3 = new SiJiaoYuYueBean.Data(770, 830, 3);
        SiJiaoYuYueBean.Data data4 = new SiJiaoYuYueBean.Data(1000, 1060, 4);
        data.add(data1);
        data.add(data2);
        data.add(data3);
        data.add(data4);
        customLine2.setData(data);
    }
}

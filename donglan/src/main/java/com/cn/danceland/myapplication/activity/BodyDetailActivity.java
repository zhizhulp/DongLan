package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseActivity;

/**
 * Created by feng on 2018/3/30.
 */

public class BodyDetailActivity extends BaseActivity {


    ListView lv_bodybase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodybase);

        initView();

    }

    private void initView() {

        lv_bodybase = findViewById(R.id.lv_bodybase);

    }
}

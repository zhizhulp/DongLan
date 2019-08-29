package com.cn.danceland.myapplication.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.cn.danceland.myapplication.R;
import com.cn.danceland.myapplication.activity.base.BaseFragmentActivity;
import com.cn.danceland.myapplication.pictureviewer.ImageDetailFragment2;

/**
 * Created by shy on 2017/12/23 14:41
 * Email:644563767@qq.com
 */


public class AvatarActivity extends BaseFragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        String url = getIntent().getStringExtra("url");
        FrameLayout frameLayout = findViewById(R.id.fl_image);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_image, ImageDetailFragment2.newInstance(url,true)).commit();

    }
}

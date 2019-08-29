package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cn.danceland.myapplication.R;

/**
 * Created by feng on 2018/5/29.
 */

public class MyErrorLayout extends FrameLayout {

    ImageView img_error;
    TextView tv_error;
    Context context;
    public MyErrorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.error_layout, this);
        img_error = inflate.findViewById(R.id.img_error);
        tv_error = inflate.findViewById(R.id.tv_error);
    }

    public void setImg_error(int resId){
        Glide.with(context).load(resId).into(img_error);
    }

    public void setTv_error(String tv){
        tv_error.setText(tv);
    }

}

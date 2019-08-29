package com.cn.danceland.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;

/**
 * Created by yxx on 2018/10/23.
 */

public class DongLanTransparentTitleView extends RelativeLayout {

    private ImageView donglan_back;
    private ImageView donglan_more;
    private TextView donglan_title, donglan_right_tv;
    private View inflate;
    private String titleText;
    private boolean cannotBack;

    public DongLanTransparentTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate = LayoutInflater.from(context).inflate(R.layout.donglan_transparent_title, this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DongLanTitleView, 0, 0);
        try {
            titleText = ta.getString(R.styleable.DongLanTitleView_titleText);
            cannotBack = ta.getBoolean(R.styleable.DongLanTitleView_cannotBack, false);
//            backText = ta.getString(R.styleable.TemplateTitle_backText);
//            moreImg = ta.getResourceId(R.styleable.TemplateTitle_moreImg, 0);
//            moreText = ta.getString(R.styleable.TemplateTitle_moreText);
            setUpView();
        } finally {
            ta.recycle();
        }


    }

    private void setUpView() {
        donglan_back = inflate.findViewById(R.id.iv_back);
        donglan_more = inflate.findViewById(R.id.iv_more);
        if (cannotBack) {
            donglan_back.setVisibility(GONE);
        }

        donglan_title = inflate.findViewById(R.id.donglan_title);
        donglan_title.setText(titleText);
        donglan_right_tv = inflate.findViewById(R.id.donglan_right_tv);
        donglan_right_tv.setTextColor(getResources().getColor(R.color.color_dl_yellow));

        donglan_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) getContext();
                activity.finish();
            }
        });
    }

    public TextView getRightTv() {
        return donglan_right_tv;
    }

    public ImageView getRightIv() {
        return donglan_more;
    }

    public void setTitle(String s) {
        donglan_title.setText(s);
    }

    public void setRightText(String rightText){
        donglan_right_tv.setVisibility(VISIBLE);
        donglan_right_tv.setText(rightText);
    }
    public void setRightTextColor(@ColorRes int color){
        donglan_right_tv.setTextColor(getResources().getColor(color));
    }

}

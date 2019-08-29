package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by shy on 2017/12/18 16:41
 * Email:644563767@qq.com
 */


public class MyLinearLayout extends LinearLayout{
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //to do 逻辑...


                //不想要父视图拦截触摸事件
                getParent().requestDisallowInterceptTouchEvent(true);

                break;
            case MotionEvent.ACTION_UP:

                getParent().requestDisallowInterceptTouchEvent(false);

                break;

            default:
                break;
        }

        return true;
    }

}

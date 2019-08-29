package com.cn.danceland.myapplication.utils;

import android.content.Context;
import android.widget.ListView;

/**
 * Created by feng on 2018/1/12.
 */

public class MyListView extends ListView {
    public MyListView(android.content.Context context,
                       android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}

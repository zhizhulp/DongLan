package com.cn.danceland.myapplication.utils;

import android.content.Context;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * Created by yxx on 2018/1/12.
 */

public class MyPullToRefreshListView extends PullToRefreshListView {
    public MyPullToRefreshListView(Context context,
                                   android.util.AttributeSet attrs) {
        super(context, attrs);
    }


    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}

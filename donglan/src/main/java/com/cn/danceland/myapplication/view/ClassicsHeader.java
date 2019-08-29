package com.cn.danceland.myapplication.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cn.danceland.myapplication.R;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

/**
 * homeFragment 下拉刷新动画
 * Created by yxx on 2018-11-15.
 */

public class ClassicsHeader extends LinearLayout implements com.scwang.smartrefresh.layout.api.RefreshHeader {

    private TextView mHeaderText;//标题文本
    private ImageView mProgressView;//刷新动画视图

    public ClassicsHeader(Context context) {
        super(context);
        initView(context);
    }
    public ClassicsHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }
    public ClassicsHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }
    public void setHeaderTextColor(int color){
        this.mHeaderText.setTextColor(color);
    }
    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mHeaderText = new TextView(context);
        mProgressView = new ImageView(context);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
//        android.widget.RelativeLayout.LayoutParams lpProgress = new android.widget.RelativeLayout.LayoutParams(lpArrow);
//        lpProgress.addRule(15);
//        lpProgress.addRule(0, 16908312);

     //   mProgressView.setPadding(this.getPaddingLeft(), DensityUtil.dp2px(20f), this.getPaddingRight(),  DensityUtil.dp2px(20f));
        mProgressView.setLayoutParams(linearParams);
        mProgressView.setImageResource(R.drawable.listview_loading_anim);
        mHeaderText.setTextColor(getResources().getColor(R.color.color_dl_black));
        addView(mProgressView,DensityUtil.dp2px(20f),DensityUtil.dp2px(20f));
        addView(new View(context), DensityUtil.dp2px(16), DensityUtil.dp2px(16));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }
    @NonNull
    public View getView() {

        return this;//真实的视图就是自己，不能返回null
    }
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;//指定为平移，不能null
    }
    @Override
    public void onStartAnimator(RefreshLayout layout, int headHeight, int maxDragHeight) {
        ((AnimationDrawable) mProgressView.getDrawable()).start();//开始动画
    }
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        ((AnimationDrawable) mProgressView.getDrawable()).stop();//停止动画
//        if (success){
//            mHeaderText.setText("刷新完成");
//        } else {
//            mHeaderText.setText("刷新失败");
//        }
        return 500;//延迟500毫秒之后再弹回
    }
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case None:
            case PullDownToRefresh:
                mHeaderText.setText("开始刷新");
                mProgressView.setVisibility(GONE);//隐藏动画
                break;
            case Refreshing:
                mHeaderText.setText("正在刷新");
                mProgressView.setVisibility(VISIBLE);//显示加载动画
                break;
            case ReleaseToRefresh:
                mHeaderText.setText("释放刷新");
                mProgressView.setVisibility(VISIBLE);//显示加载动画
                break;
        }
    }
    public boolean isSupportHorizontalDrag() {
        return false;
    }
    @Override
    public void onInitialized(RefreshKernel kernel, int height, int maxDragHeight) {
    }

    @Override
    public void onMoving(boolean b, float v, int i, int i1, int i2) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i, int i1) {

    }

    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
    }
    public void onPulling(float percent, int offset, int headHeight, int maxDragHeight) {
    }
    public void onReleasing(float percent, int offset, int headHeight, int maxDragHeight) {
    }
    public void onRefreshReleased(RefreshLayout layout, int headerHeight, int maxDragHeight) {
    }
    @Override
    public void setPrimaryColors(@ColorInt int ... colors){
    }
}